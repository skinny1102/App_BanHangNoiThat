package com.example.addtocard1.Doituong;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    public String idProduct;
    public String nameProduct;
    public String descriptionProduct;
    public int priceProduct;
    public int quantity;
    public  String imgResource;
    public String categories;
    public List<String> listImgResource;
    private boolean isAddToCard;
    public Product(){
    }

    public Product(String idProduct, String nameProduct, String descriptionProduct, int priceProduct, int quantity, String imgResource, String categories, List<String> listImgResource) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.descriptionProduct = descriptionProduct;
        this.priceProduct = priceProduct;
        this.quantity = quantity;
        this.imgResource = imgResource;
        this.categories = categories;
        this.listImgResource = listImgResource;

    }

    public List<String> getListImgResource() {
        return listImgResource;
    }

    public void setListImgResource(List<String> listImgResource) {
        this.listImgResource = listImgResource;
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
//        limitToFirst()	Đặt số lượng mục tối đa để trả về từ đầu danh sách kết quả có thứ tự.
//        limitToLast()	Đặt số lượng mục tối đa để trả về từ cuối danh sách kết quả theo thứ tự.
//        startAt()	Trả lại các mặt hàng lớn hơn hoặc bằng khóa hoặc giá trị đã chỉ định tùy thuộc vào phương thức theo thứ tự đã chọn.
//        startAfter()	Trả lại các mặt hàng lớn hơn giá trị hoặc khóa được chỉ định tùy thuộc vào phương thức đặt hàng đã chọn.
//        endAt()	Trả lại các mặt hàng nhỏ hơn hoặc bằng khóa hoặc giá trị đã chỉ định tùy thuộc vào phương thức theo thứ tự đã chọn.
//        endBefore()	Trả lại các mặt hàng ít hơn giá trị hoặc khóa được chỉ định tùy thuộc vào phương thức đặt hàng đã chọn.
//        equalTo()	Trả lại các mặt hàng bằng với khóa hoặc giá trị đã chỉ định tùy thuộc vào phương thức theo thứ tự đã chọn.
//Query query2 = FirebaseDatabase.getInstance().getReference("Groups").orderByChild("search")
//        .startAt(s)
//        .endAt(s+"\uf8ff")
//Tạo đối tượng
//