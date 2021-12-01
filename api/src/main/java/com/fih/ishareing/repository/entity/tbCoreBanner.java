package com.fih.ishareing.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The persistent class for the tbCoreBanner database table.
 */

@Entity
@Table(name = "core_banner")
public class tbCoreBanner implements Serializable {
    private static final long serialVersionUID = 1646236048313290516L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    private Long id;
    @Column(name = "Url")
    private String url;
    @Column(name = "Image")
    private String image;
    @Column(name = "displaySort")
    private Integer displaySort;
    @Column(name = "smallImage")
    private String smallImage;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getDisplaySort() {
        return displaySort;
    }

    public void setDisplaySort(Integer displaySort) {
        this.displaySort = displaySort;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }
}