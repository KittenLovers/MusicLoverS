package it.univr.musiclovers.model.beans;

import java.io.Serializable;
import java.util.List;

public class ProductBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;
    private boolean enable;
    private boolean online;
    private boolean inexpensive;
    private float weight;
    private float price;
    private String name;
    private String description;
    private boolean for_child;
    private int min_age;
    private boolean professional;
    private boolean used;
    private BrandBean brand;
    private List<String> product_image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInexpensive() {
        return inexpensive;
    }

    public void setInexpensive(boolean inexpensive) {
        this.inexpensive = inexpensive;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFor_child() {
        return for_child;
    }

    public void setFor_child(boolean for_child) {
        this.for_child = for_child;
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
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

    public BrandBean getBrand() {
        return brand;
    }

    public void setBrand(BrandBean brand) {
        this.brand = brand;
    }

    public List<String> getProduct_image() {
        return product_image;
    }

    public void setProduct_image(List<String> product_image) {
        this.product_image = product_image;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

}
