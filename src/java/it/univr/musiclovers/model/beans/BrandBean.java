/**
 * Bean per la tabella Product
 */
package it.univr.musiclovers.model.beans;

import java.io.Serializable;

public class BrandBean implements Serializable {
    private static final long serialVersionUID = 1L;

    // === Properties ============================================================
    private int id;
    private String name;
    private String description;
    private String link;
    private String logo;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
