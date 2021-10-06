package com.example.addtocard1.Cart;

import com.example.addtocard1.Product;

import java.util.List;

public class CartUser {
    public String userID;
    public List<Product> listProduct;

    public CartUser(String userID, List<Product> listProduct) {
        this.userID = userID;
        this.listProduct = listProduct;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }
}
