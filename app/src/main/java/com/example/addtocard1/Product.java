package com.example.addtocard1;

import java.io.Serializable;

public class Product implements Serializable {
    public String idProduct;
    public String nameProduct;
    public String descriptionProduct;
    public int priceProduct;
    public int quantity;
    public  String imgResource;
    public String categories;
    private boolean isAddToCard;
    public Product(){

    }
    public Product(String idProduct, String nameProduct, String descriptionProduct, int priceProduct, int quantity, String imgResource, String categories) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.descriptionProduct = descriptionProduct;
        this.priceProduct = priceProduct;
        this.quantity = quantity;
        this.imgResource = imgResource;
        this.categories = categories;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public int getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(int priceProduct) {
        this.priceProduct = priceProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImgResource() {
        return imgResource;
    }

    public void setImgResource(String imgResource) {
        this.imgResource = imgResource;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public boolean isAddToCard() {
        return isAddToCard;
    }

    public void setAddToCard(boolean addToCard) {
        isAddToCard = addToCard;
    }
}
