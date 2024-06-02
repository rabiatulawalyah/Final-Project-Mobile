package com.example.finalproject.model;

import java.io.Serializable;

public class Planet implements Serializable {
    private int planetOrder;
    private String name;
    public int getPlanetOrder() {
        return planetOrder;
    }

    public void setPlanetOrder(int planetOrder) {
        this.planetOrder = planetOrder;
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

    public BasicDetails getBasicDetails() {
        return basicDetails;
    }

    public void setBasicDetails(BasicDetails basicDetails) {
        this.basicDetails = basicDetails;
    }

    public ImgSrc getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(ImgSrc imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String description;
    private BasicDetails basicDetails;
    private ImgSrc imgSrc;
    private int id;

    // Getters and Setters...

    public static class BasicDetails implements Serializable {
        private String volume;
        private String mass;
        private String source;

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getMass() {
            return mass;
        }

        public void setMass(String mass) {
            this.mass = mass;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getWikiLink() {
            return wikiLink;
        }

        public void setWikiLink(String wikiLink) {
            this.wikiLink = wikiLink;
        }

        private String wikiLink;

        // Getters and Setters...
    }

    public static class ImgSrc implements Serializable {
        private String img;
        private String imgDescription;

        // Getters and Setters...

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImgDescription() {
            return imgDescription;
        }

        public void setImgDescription(String imgDescription) {
            this.imgDescription = imgDescription;
        }
    }
}
