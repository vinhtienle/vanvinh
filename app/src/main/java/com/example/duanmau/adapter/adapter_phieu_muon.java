package com.example.duanmau.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.dao.PhieuMuonDao;
import com.example.duanmau.dao.SachDao;
import com.example.duanmau.dao.ThanhVienDao;
import com.example.duanmau.model.PhieuMuon;
import com.example.duanmau.model.Sach;
import com.example.duanmau.model.ThanhVien;

import java.util.ArrayList;
import java.util.HashMap;

public class adapter_phieu_muon extends RecyclerView.Adapter<adapter_phieu_muon.ViewHolder> {

    private ArrayList<PhieuMuon> list;
    private Context context;
    PhieuMuonDao dao;

    public adapter_phieu_muon(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new PhieuMuonDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieu_muon,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaPM.setText(String.valueOf(list.get(position).getMaPM()));
        holder.txtTenTV.setText(list.get(position).getHoTenTV());
        holder.txtTenSach.setText(list.get(position).getTenSach());
        holder.txtTienThue.setText(String.valueOf(list.get(position).getTienThue()));
        String trangthai = "";
        if(list.get(position).getTrangThai() == 1){
            trangthai = "Đã trả sách";
            holder.txtTrangThai.setTextColor(ContextCompat.getColor(context, R.color.TrangThai));
        }else{
            trangthai = "chưa trả sách";
        }
        holder.txtTrangThai.setText(trangthai);
        holder.txtNgay.setText(list.get(position).getNgayThue());

        holder.PM_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Bạn thật sự muốn xóa phiếu mượn này chứ");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PhieuMuonDao dao = new PhieuMuonDao(context);
                        boolean check = dao.delete(list.get(holder.getAdapterPosition()).getMaPM());
                        if (check){
                            list.clear();
                            list = dao.getDSPhieuMuon();
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Xóa phiếu mượn không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy",null);
                builder.create().show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialogUpdatePM(list.get(holder.getAdapterPosition()));
                return true;
            }
        });
    }

    @SuppressLint("MissingInflatedId")
    private void dialogUpdatePM(PhieuMuon pm) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_phieumuon,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        Spinner spnThanhVien = view.findViewById(R.id.spnThanhVien);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        Button PM_add = view.findViewById(R.id.PM_update);

        CheckBox chk = view.findViewById(R.id.chkcheck);
        getDataThanhVien(spnThanhVien);
        getDataSach(spnSach);

        PM_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status;
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                int matv = (int) hsTV.get("MaTV");
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                int masach = (int) hsSach.get("MaSach");
                int id = pm.getMaPM();
                int tt = pm.getTrangThai();

                if(chk.isChecked()){
                    status = 1;
                    pm.setTrangThai(status);
                }else{
                    status = 0;
                    pm.setTrangThai(status);
                }


            }
        });
    }

    private void getDataThanhVien(Spinner spnThanhVien){
        ThanhVienDao thanhVienDao = new ThanhVienDao(context);
        ArrayList<ThanhVien> list = thanhVienDao.getDSThanhVien();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(ThanhVien tv : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MaTV",tv.getMaTV());
            hs.put("HoTen",tv.getHoTen());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"HoTen"},
                new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach){
        SachDao sachDao = new SachDao(context);
        ArrayList<Sach> list = sachDao.getDSSach();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(Sach sc : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MaSach",sc.getMaSach());
            hs.put("TenSach",sc.getTenSach());
            hs.put("GiaThue",sc.getGiaThue());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"TenSach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }

    private void loadData(){
        list.clear();
        list = dao.getDSPhieuMuon();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaPM, txtMaTV, txtTenTV, txtMaTT, txtTenTT, txtMaSach, txtTenSach, txtNgay, txtTrangThai, txtTienThue;
        ImageView PM_Delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaPM =  itemView.findViewById(R.id.MaPM);
            txtTenTV =  itemView.findViewById(R.id.TenTV);
            txtTenSach =  itemView.findViewById(R.id.TenSach);
            txtNgay =  itemView.findViewById(R.id.PM_NT);
            txtTrangThai =  itemView.findViewById(R.id.PM_TrangThai);
            txtTienThue =  itemView.findViewById(R.id.PM_TT);
            PM_Delete = itemView.findViewById(R.id.PM_Delete);
        }
    }

}
