package com.gmail.iikaliada.onlinemarket.servicemodule;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

public interface XMLtoItemService {

    void convertXmlToItem(File file) throws JAXBException, IOException, SAXException;
}
