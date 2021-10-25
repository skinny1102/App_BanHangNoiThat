package com.example.addtocard1;

import java.util.List;

public class DonHang {
    public String maDonHang;
    public String nameNguoiDat;
    public String ngayDatHang;
    public List<Product> listProductDat;
    public boolean trangThai;

    public DonHang(String maDonHang, String nameNguoiDat, String ngayDatHang, List<Product> listProductDat, boolean trangThai) {
        this.maDonHang = maDonHang;
        this.nameNguoiDat = nameNguoiDat;
        this.ngayDatHang = ngayDatHang;
        this.listProductDat = listProductDat;
        this.trangThai = trangThai;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getNameNguoiDat() {
        return nameNguoiDat;
    }

    public void setNameNguoiDat(String nameNguoiDat) {
        this.nameNguoiDat = nameNguoiDat;
    }

    public String getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(String ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
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
}
