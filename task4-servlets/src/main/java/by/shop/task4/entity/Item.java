package by.shop.task4.entity;

public class Item {
    private Long id;
    private String title;
    private Long price;
    private String description;
    private String manufactureTeam;
    private Long ownerId;

    public Item() {
    }

    public Item(String title, Long price, String description, String manufactureTeam, Long ownerId) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.manufactureTeam = manufactureTeam;
        this.ownerId = ownerId;
    }

    public Item(Long id, String title, Long price, String description, String manufactureTeam, Long ownerId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.manufactureTeam = manufactureTeam;
        this.ownerId = ownerId;
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

    public String getManufactureTeam() {
        return manufactureTeam;
    }

    public void setManufactureTeam(String manufactureTeam) {
        this.manufactureTeam = manufactureTeam;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
