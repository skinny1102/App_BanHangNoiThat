package com.example.addtocard1.Doituong;

import com.example.addtocard1.Doituong.Product;

import java.util.List;

public class DonHang {
    private String maDonhang;
    private String userId;
    private String fullName;
    private String sdt;
    private String address;
    private String dateDatHang;
    private List<Product> listProductDat;
    private boolean trangThai;
    private int tongTien;

    public DonHang(String maDonhang,String userId, String fullName, String sdt, String address, String dateDatHang, List<Product> listProductDat, boolean trangThai, int tongTien) {
        this.maDonhang = maDonhang;
        this.userId = userId;
        this.fullName = fullName;
        this.sdt = sdt;
        this.address = address;
        this.dateDatHang = dateDatHang;
        this.listProductDat = listProductDat;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
    }
    public DonHang(){

    }

    public String getMaDonhang() {
        return maDonhang;
    }

    public void setMaDonhang(String maDonhang) {
        this.maDonhang = maDonhang;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateDatHang() {
        return dateDatHang;
    }

    public void setDateDatHang(String dateDatHang) {
        this.dateDatHang = dateDatHang;
    }

    public List<Product> getListProductDat() {
        return listProductDat;
    }

    public void setListProductDat(List<Product> listProductDat) {
        this.listProductDat = listProductDat;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
