package com.gmail.iikaliada.onlinemarket.servicemodule.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "Item")
public class ItemDTO {

    private Long id;
    @NotNull
    @Size(min = 2, max = 50, message = "{validation.size.message}")
    @Pattern(regexp = "^[a-zA-Z0-9/.,;:\"' ]+$", message = "{letter.not.correct}")
    private String name;
    private BigDecimal price;
    private String uniqueNumber;
    @NotNull
    @Size(min = 10, max = 50, message = "{validation.size.message}")
    @Pattern(regexp = "^[a-zA-Z0-9/.,;:\"' ]+$", message = "{letter.not.correct}")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
