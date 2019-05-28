package com.gmail.iikaliada.onlinemarket.repositorymodule.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long uid;
    @EmbeddedId
    private OrderId id;
    @Column
    private String orderStatus;
    @Column
    private Long quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("item_id")
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user_id")
    private User user;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public OrderId getId() {
        return id;
    }

    public void setId(OrderId id) {
        this.id = id;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(uid, order.uid) &&
                Objects.equals(id, order.id) &&
                Objects.equals(orderStatus, order.orderStatus) &&
                Objects.equals(quantity, order.quantity) &&
                Objects.equals(item, order.item) &&
                Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, id, orderStatus, quantity, item, user);
    }
}
