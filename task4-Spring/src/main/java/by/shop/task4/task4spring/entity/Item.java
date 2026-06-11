package by.shop.task4.task4spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "items")
public class Item {
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Long price;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private int created_by;

    @Column(name = "created_at")
    private Date created_at;

    public Item() {
    }

    public Item(String title, Long price, String description, int created_by, Date created_at) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.created_by = created_by;
        this.created_at = created_at;
    }

    public Item(Long id, String title, Long price, String description, int created_by, Date ownerId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.created_by = created_by;
        this.created_at = ownerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatedBy() {
        return created_by;
    }

    public void setCreatedBy(int manufactureTeam) {
        this.created_by = created_by;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Long ownerId) {
        this.created_at = created_at;
    }
}
