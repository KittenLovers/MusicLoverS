package it.univr.musiclovers.model.beans;

import java.util.Date;

/**
 *
 * @author blasco991
 */
public class OrderBeans {

    private int ID;
    private float price;
    private Date soldDAte;
    private ProductBean product;
    private EmployerBean seller;
    private CustomerBean buyer;
    private ProfessionalBean owner;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getSoldDAte() {
        return (Date) soldDAte.clone();
    }

    public void setSoldDAte(Date soldDAte) {
        this.soldDAte = (Date) soldDAte.clone();
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

    public CustomerBean getBuyer() {
        return buyer;
    }

    public void setBuyer(CustomerBean buyer) {
        this.buyer = buyer;
    }

    public ProfessionalBean getOwner() {
        return owner;
    }

    public void setOwner(ProfessionalBean owner) {
        this.owner = owner;
    }

}
