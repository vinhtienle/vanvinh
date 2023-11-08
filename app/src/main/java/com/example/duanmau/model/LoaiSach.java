package com.example.duanmau.model;

public class LoaiSach {
    private int MaLS;
    private String TenLS;
    private String NCC;

    public LoaiSach() {
    }

    public LoaiSach(int maLS, String tenLS, String NCC) {
        MaLS = maLS;
        TenLS = tenLS;
        this.NCC = NCC;
    }

    public int getMaLS() {
        return MaLS;
    }

    public void setMaLS(int maLS) {
        MaLS = maLS;
    }

    public String getTenLS() {
        return TenLS;
    }

    public void setTenLS(String tenLS) {
        TenLS = tenLS;
    }

    public String getNCC() {
        return NCC;
    }

    public void setNCC(String NCC) {
        this.NCC = NCC;
    }
}
