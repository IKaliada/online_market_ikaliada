package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ItemRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Item;
import com.gmail.iikaliada.onlinemarket.servicemodule.XMLtoItemService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.ItemConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ItemDTO;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.ItemsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.xml.sax.SAXException;

import javax.transaction.Transactional;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class XMLtoItemServiceImpl implements XMLtoItemService {

    private Logger logger = LoggerFactory.getLogger(XMLtoItemServiceImpl.class);

    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    public XMLtoItemServiceImpl(ItemRepository itemRepository, ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    @Transactional
    public void convertXmlToItem(File file) throws JAXBException, SAXException, IOException {
        logger.info(file.toString());
        validateXml(file);
        JAXBContext jaxbContext = JAXBContext.newInstance(ItemsDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ItemsDTO items = (ItemsDTO) unmarshaller.unmarshal(file);
        for (ItemDTO itemDTO : items.getItems()) {
            logger.info(itemDTO.toString());
            itemDTO.setUniqueNumber(UUID.randomUUID().toString());
            Item item = itemConverter.fromItemDTO(itemDTO);
            itemRepository.persist(item);
        }
    }

    private void validateXml(File file) throws SAXException, IOException {
        File fileName = ResourceUtils.getFile("classpath:uploadingDir/items_schema.xsd");
        SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(fileName)
                .newValidator()
                .validate(new StreamSource(file));
    }
}
