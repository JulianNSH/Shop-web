package github.JulianNSH.SHOP.Pages;
/*
    Class with getters and setters to handle mappings
 */
public class BuyersClass {
    int id;
    String name;
    String surname;
    double acquisitions;
    int discount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Double getAcquisitions() {
        return acquisitions;
    }

    public void setAcquisitions(Double acquisitions) {
        this.acquisitions = acquisitions;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
