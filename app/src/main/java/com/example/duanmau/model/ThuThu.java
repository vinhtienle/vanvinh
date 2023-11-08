package com.example.duanmau.model;

public class ThuThu {
    private String MaTT,MatKhau,HoTen;
    private int chucVu;

    public ThuThu() {
    }

    public ThuThu(String maTT, String matKhau, String hoTen, int chucVu) {
        MaTT = maTT;
        MatKhau = matKhau;
        HoTen = hoTen;
        this.chucVu = chucVu;
    }

    public String getMaTT() {
        return MaTT;
    }

    public void setMaTT(String maTT) {
        MaTT = maTT;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public int getChucVu() {
        return chucVu;
    }

    public void setChucVu(int chucVu) {
        this.chucVu = chucVu;
    }
}
