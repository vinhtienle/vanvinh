package com.example.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.duanmau.database.DbHelper;

public class ThuThuDao {
    DbHelper dbHelper;
    public ThuThuDao(Context context){
        dbHelper = new DbHelper(context);

    }

    // đăng nhập
    public boolean checkLogin(String MaTT,String MatKhau){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ThuThu where MaTT = ? and MatKhau = ?",new String[]{MaTT, MatKhau});
        if(cursor.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean updateMK(String username, String oldPass, String newPass){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ThuThu where MaTT = ? and MatKhau = ?", new String[]{username,oldPass});
        if (cursor.getCount() > 0){
            ContentValues values = new ContentValues();
            values.put("MatKhau", newPass);
            long check = db.update("ThuThu",values,"MaTT = ?",new String[]{username});
            if(check == -1){
                return false;
            }else {
                return true;
            }
        }
        return false;
    }


}
