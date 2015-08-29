package it.univr.musiclovers.model.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private BrandBean brand;
    private String description;
    private boolean enable;
    private boolean forChild;
    private int id;
    private boolean inexpensive;
    private int minAge;
    private String name;
    private boolean online;
    private float price;
    private List<String> productImage;
    private boolean professional;
    private boolean status;
    private boolean used;
    private float weight;

    public BrandBean getBrand() {
        return brand;
    }

    public void setBrand(BrandBean brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int min_age) {
        this.minAge = min_age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setProductImage(List<String> product_image) {
        this.productImage = new ArrayList<>(product_image);
    }

    public List<String> getProductImages() {
        return Collections.unmodifiableList(productImage);
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getThumb() {
        if (productImage.isEmpty()) {
            return "img/image-not-found.png";
        } else {
            return productImage.get(0);
        }
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean getForChild() {
        return forChild;
    }

    public void setForChild(boolean forChild) {
        this.forChild = forChild;
    }

    public boolean getInexpensive() {
        return inexpensive;
    }

    public void setInexpensive(boolean inexpensive) {
        this.inexpensive = inexpensive;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isProfessional() {
        return professional;
    }

    public void setProfessional(boolean professional) {
        this.professional = professional;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

}
