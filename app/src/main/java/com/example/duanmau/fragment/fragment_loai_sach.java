package com.example.duanmau.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.adapter_LoaiSach;
import com.example.duanmau.dao.LoaiSachDao;
import com.example.duanmau.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class fragment_loai_sach extends Fragment {

    FloatingActionButton fltAdd;
    RecyclerView rcvLS;
    LoaiSachDao dao;
    SearchView searchView;

    public fragment_loai_sach() {

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);

        rcvLS = view.findViewById(R.id.rcvLS);
        fltAdd = view.findViewById(R.id.add_LS);
//        searchView = view.findViewById(R.id.sv);
        dao = new LoaiSachDao(getContext());
        loadData();

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (newText.isEmpty()){
//                    list.clear();
//                    list.addAll(dao.getDSLoaiSach());
//                    adapter.notifyDataSetChanged();
//                }else {
//                    list.clear();
//                    list.addAll(dao.tim("%"+newText+"%"));
//                    adapter.notifyDataSetChanged();
//                }
//                return false;
//            }
//        });
        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaLogAddLS();
            }
        });
        return view;
    }

    public void DiaLogAddLS() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_loaisach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputLayout in_TenLS = view.findViewById(R.id.in_addTenLS);
        TextInputEditText ed_TenLS = view.findViewById(R.id.ed_addTenLS);
        TextInputLayout in_trangthai = view.findViewById(R.id.in_addTrangThai);
        TextInputEditText en_trangthai = view.findViewById(R.id.ed_addTrangThai);
        Button AddLS = view.findViewById(R.id.LS_add);


        ed_TenLS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    in_TenLS.setError("Vui lòng nhập tên loại sách");
                } else {
                    in_TenLS.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        AddLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenloai = ed_TenLS.getText().toString();
                if (tenloai.isEmpty()) {
                    if (tenloai.equals("")) {
                        in_TenLS.setError("Vui lòng nhập tên loại sách");
                    } else {
                        in_TenLS.setError(null);
                    }
                } else {
                    if (dao.insert(tenloai)) {
                        loadData();
                        Toast.makeText(getContext(), "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm loại sách không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    ArrayList<LoaiSach> list;
    adapter_LoaiSach adapter;

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvLS.setLayoutManager(layoutManager);
        list = dao.getDSLoaiSach();
        adapter = new adapter_LoaiSach(getContext(), list);
        rcvLS.setAdapter(adapter);
    }
}