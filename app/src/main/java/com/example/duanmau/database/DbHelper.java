package com.example.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    static final String dbName = "PhuongNamLib";
    static final int dbVersion = 12;

    public DbHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng thủ thư
        String tb_ThuThu = "create table ThuThu(" +
                "MaTT text primary key," +
                "HoTen text not null," +
                "MatKhau text not null," +
                "Loaitaikhoan text not null)";
        db.execSQL(tb_ThuThu);

        // Tạo bảng thành viên
        String tb_ThanhVien = "create table ThanhVien(" +
                "MaTV integer primary key autoincrement," +
                "HoTen text not null," +
                "NamSinh text not null)";
        db.execSQL(tb_ThanhVien);

        // Tạo bảng loại sách
            String tb_LoaiSach = "create table LoaiSach(" +
                    "MaLoai integer primary key autoincrement," +
                    "HoTen text not null," +
                    "NhaCungCap text not null)";
            db.execSQL(tb_LoaiSach);

        // Tạo bảng sách
        String tb_Sach = "create table Sach(" +
                "MaSach integer primary key autoincrement," +
                "TenSach text not null," +
                "CotMau text not null,"+
                "TrongLuong text not null,"+
                "GiaThue integer not null," +
                "MaLoai integer references LoaiSach(MaLoai))";
        db.execSQL(tb_Sach);

        // Tạo bảng phiếu mượn
        String tb_PhieuMuon = "create table PhieuMuon(" +
                "MaPM integer primary key autoincrement," +
                "MaTT text references ThuThu(MaTT)," +
                "MaTV integer references ThanhVien(MaTV)," +
                "MaSach integer references Sach(MaSach)," +
                "TienThue integer not null," +
                "TraSach integer not null," +
                "Ngay text not null)";
        db.execSQL(tb_PhieuMuon);

        //data mẫu
        db.execSQL("INSERT INTO LoaiSach VALUES (1, 'Thiếu nhi','ds'),(2,'Tình cảm','1','vinhdet'),(3, 'Giáo khoa','ds')");
        db.execSQL("INSERT INTO Sach VALUES (1, 'Hãy đợi đấy','tim','100' ,2500), (2, 'Thằng cuội','tim','100', 1000), (3, 'Lập trình Android','tim','100', 2000)");
        db.execSQL("INSERT INTO ThuThu VALUES ('thuthu01','Nguyễn Văn Anh','1','Admin'),('thuthu02','Trần Văn Hùng','123abc','ThuThu')");
        db.execSQL("INSERT INTO ThanhVien VALUES (1,'Cao Thu Trang','2000'),(2,'Trần Mỹ Kim','2000')");
        //trả sách: 1: đã trả - 0: chưa trả
        db.execSQL("INSERT INTO PhieuMuon VALUES (1,'thuthu01',1, 1, 2500, 1, '19/03/2022')," +
                "(2,'thuthu01',1, 3, 2000, 0, '19/03/2022')," +
                "(3,'thuthu02',2, 1, 2000, 1, '19/03/2022')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i != i1){
            db.execSQL("drop table if exists ThuThu");
            db.execSQL("drop table if exists ThanhVien");
            db.execSQL("drop table if exists LoaiSach");
            db.execSQL("drop table if exists Sach");
            db.execSQL("drop table if exists PhieuMuon");
            onCreate(db);
        }
    }
}
