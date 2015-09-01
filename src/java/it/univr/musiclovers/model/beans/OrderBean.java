package it.univr.musiclovers.model.beans;

/**
 *
 * @author blasco991
 */
public class OrderBean {

    private int ID;
    private CustomerBean buyer = new CustomerBean();
    private ProfessionalBean owner = new ProfessionalBean();
    private String paymentType;
    private float price;
    private ProductBean product = new ProductBean();
    private EmployerBean seller = new EmployerBean();
    private String soldDate;

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

    public String getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

}
