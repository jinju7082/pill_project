package com.example.ui_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class FragHome extends Fragment  {//extends AppCompatActivity
    private View view;
    private TextView tv_id;//닉네임 text
    private ImageView iv_profile;//이미지 뷰

    //상태 저장(어댑터와 맞물려서 통신해주는 역할)
    public static FragHome newInstance(){
        FragHome fragHome = new FragHome();
        return fragHome;
    }
    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frag_home,container,false);

        Intent intent = getActivity().getIntent();
        String id = intent.getStringExtra("id"); //메인 액티비티로 부터 닉네임 전달받음
        String photourl = intent.getStringExtra("photourl"); //메인 액티비티로부터 이미지 전달

        tv_id = view.findViewById(R.id.tv_id);
        tv_id.setText(id);//닉네임 text를 텍스트 뷰에 세팅

        iv_profile = view.findViewById(R.id.iv_profile);
        Glide.with(this).load(photourl).into(iv_profile); // 프로필 url을 이미지 뷰에 세팅
        return view;
    }

}
