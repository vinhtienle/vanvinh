package com.example.duanmau.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.adapter_Top10;
import com.example.duanmau.dao.ThongKeDao;
import com.example.duanmau.model.Sach;

import java.util.ArrayList;


public class fragment_top extends Fragment {

    public fragment_top() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        RecyclerView rcv = view.findViewById(R.id.rcv_Top);

        ThongKeDao thongKeDao = new ThongKeDao(getContext());
        ArrayList<Sach> list = thongKeDao.getTop10();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapter_Top10 adapter = new adapter_Top10(getContext(),list);
        rcv.setAdapter(adapter);

        return view;
    }
}