package com.yechaoa.materialdesign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.activity.BookCollectionActivity;
import com.yechaoa.materialdesign.activity.SearchActivity;

public class FragmentHome extends Fragment implements View.OnClickListener{

    Button btn_search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_search = (Button) getView().findViewById(R.id.btn_home_search);

        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_search:
                //跳转到Search页面
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivityForResult(intent,1);
                Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
        }
    }

}
