package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Items")
public class ItemsDTO {

    private List<ItemDTO> items = new ArrayList<>();

    public List<ItemDTO> getItems() {
        return items;
    }
    @XmlElement(name = "Item")
    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
