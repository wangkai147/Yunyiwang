package com.wangk.mymusic.Home.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wangk.mymusic.Login.Bean.Login;
import com.wangk.mymusic.R;


public class MyFragment extends Fragment {

    private String result = null;
    private Login login = null;

    private View rootView;
    private Context mContext;

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();

        Bundle bundle = getArguments();
        if(bundle!=null){
            result = bundle.getString("result");

            //解析json
            Gson gson = new Gson();
            //login存储登录数据
            login = gson.fromJson(result,Login.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my, container, false);
        if(login!=null) Toast.makeText(mContext,"id"+login.getAccount().getId(),Toast.LENGTH_SHORT).show();
        return rootView;
    }
}
