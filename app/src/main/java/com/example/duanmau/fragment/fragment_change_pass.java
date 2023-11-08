package com.example.duanmau.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duanmau.Login_Screen;
import com.example.duanmau.R;
import com.example.duanmau.dao.ThuThuDao;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class fragment_change_pass extends Fragment {

    TextInputLayout in_CurrentPassword, in_NewPass, in_Repass;
    TextInputEditText ed_txtCurrentPassword, ed_txtNewPass, ed_txtRepass;
    Button btnSaveMK, btnCancelSaveMK;
    private Context context ;
    public fragment_change_pass() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        in_CurrentPassword = view.findViewById(R.id.in_CurrentPassword);
        in_NewPass = view.findViewById(R.id.in_NewPass);
        in_Repass = view.findViewById(R.id.in_Repass);
        ed_txtCurrentPassword = view.findViewById(R.id.ed_txtCurrentPassword);
        ed_txtNewPass = view.findViewById(R.id.ed_txtNewPass);
        ed_txtRepass = view.findViewById(R.id.ed_txtRepass);
        btnSaveMK = view.findViewById(R.id.btn_SaveMK);




        btnSaveMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoiMK();
            }
        });
        return view;
    }

    public void DoiMK(){
        String oldPass = ed_txtCurrentPassword.getText().toString();
        String newPass = ed_txtNewPass.getText().toString();
        String repass = ed_txtRepass.getText().toString();
        if(newPass.equals(repass)){
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_File",getContext().MODE_PRIVATE);
            String matt = sharedPreferences.getString("Username","");
            String mk = sharedPreferences.getString("Password","");
            //cập nhật
            ThuThuDao thuThuDao =  new ThuThuDao(getContext());
            boolean check = thuThuDao.updateMK(matt,oldPass,newPass);
            if(oldPass.equals(mk)){
                if(check){
                    Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), Login_Screen.class);
                    Log.e("TAG", "DoiMK: 32" );
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }else{
                in_CurrentPassword.setError("Mật khẩu hiện tại không đúng");
            }
        }else{
            in_NewPass.setError("Mật Khẩu Không Khớp");
            in_Repass.setError("Mật Khẩu Không Khớp");
        }
    }
}