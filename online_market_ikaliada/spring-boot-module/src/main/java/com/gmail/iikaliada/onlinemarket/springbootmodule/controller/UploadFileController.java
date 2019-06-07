package com.gmail.iikaliada.onlinemarket.springbootmodule.controller;

import com.gmail.iikaliada.onlinemarket.servicemodule.XMLtoItemService;
import com.gmail.iikaliada.onlinemarket.springbootmodule.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class UploadFileController {

    private Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    @Value("${file.not.found.message}")
    private String notFoundMessage;

    private final XMLtoItemService xmLtoItemService;

    public UploadFileController(XMLtoItemService xmLtoItemService) {
        this.xmLtoItemService = xmLtoItemService;
    }

    @PostMapping(value = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile[] uploadingFile, RedirectAttributes redirectAttributes) throws JAXBException,
            IOException, SAXException {
        for (MultipartFile uploadedFile : uploadingFile) {
            if (uploadedFile.getOriginalFilename().equals("")) {
                redirectAttributes.addFlashAttribute("notFoundMessage", notFoundMessage);
                return "redirect:/private/items";
            }
            File convFile = new File(uploadedFile.getOriginalFilename());
            logger.info(convFile.toString());
            try (FileOutputStream fos = new FileOutputStream(convFile)) {
                fos.write(uploadedFile.getBytes());
            }
            try {
                xmLtoItemService.convertXmlToItem(convFile);
            } catch (JAXBException | IOException | SAXException e) {
                throw new CustomException(HttpStatus.BAD_REQUEST.toString(), "Not correct type of file");
            }
        }
        return "redirect:/private/items";
    }
}
