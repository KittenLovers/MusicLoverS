package it.univr.musiclovers.model.beans;

import java.util.Date;

/**
 *
 * @author blasco991
 */
public class OrderBean {

    private int ID;
    private CustomerBean buyer;
    private ProfessionalBean owner;
    private String paymentType;
    private float price;
    private ProductBean product;
    private EmployerBean seller;
    private Date soldDate;

    public CustomerBean getBuyer() {
        return buyer;
    }

    public void setBuyer(CustomerBean buyer) {
        this.buyer = buyer;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ProfessionalBean getOwner() {
        return owner;
    }

    public void setOwner(ProfessionalBean owner) {
        this.owner = owner;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public EmployerBean getSeller() {
        return seller;
    }

    public void setSeller(EmployerBean seller) {
        this.seller = seller;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }

}
