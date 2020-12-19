package com.ekr.smartlaundry.data;

public class ProductModel {
    private String nama_produk;
    private int harga_produk;
    private int qty;

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public int getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(int harga_produk) {
        this.harga_produk = harga_produk;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
