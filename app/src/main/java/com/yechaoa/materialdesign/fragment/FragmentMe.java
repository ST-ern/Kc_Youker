package com.yechaoa.materialdesign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.activity.BookCollectionActivity;
import com.yechaoa.materialdesign.activity.LoginActivity;
import com.yechaoa.materialdesign.activity.SettingActivity;
import com.yechaoa.materialdesign.utils.AnalysisUtils;


public class FragmentMe extends Fragment implements View.OnClickListener{

    private LinearLayout llHead;
    private RelativeLayout rlBookCollection, rlSetting;
    private ImageView ivHeadIcon, ivBookCollectionIcon, ivUserInfoIcon;
    private TextView tvUserName, tvUserDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        llHead = (LinearLayout) view.findViewById(R.id.ll_head);
        ivHeadIcon = (ImageView) view.findViewById(R.id.iv_head_icon);
        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        tvUserDescription = (TextView) view.findViewById(R.id.tv_user_description);
        rlBookCollection = (RelativeLayout) view.findViewById(R.id.rl_book_collection);
        ivBookCollectionIcon = (ImageView) view.findViewById(R.id.iv_book_collection_icon);
        rlSetting = (RelativeLayout) view.findViewById(R.id.rl_setting);
        ivUserInfoIcon = (ImageView) view.findViewById(R.id.iv_userInfo_icon);

        changeInfo();

        llHead.setOnClickListener(this);
        rlBookCollection.setOnClickListener(this);
        rlSetting.setOnClickListener(this);

    }

    public void changeInfo() {
        if (AnalysisUtils.readLoginStatus(getActivity())){
            String userName = AnalysisUtils.readLoginUserName(getActivity());
            tvUserName.setText(userName);
            //Todo: 调用后端返回用户的详细信息，得到用户的描述description
            tvUserDescription.setText("Now you log in.");
        }else {
            tvUserName.setText("Click to Login");
            tvUserDescription.setText("There is nothing, please login first.");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_head:
                if (AnalysisUtils.readLoginStatus(getActivity())){
                    //跳转到个人资料界面
                }else {
                    //跳转到登录界面
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivityForResult(intent,1); }
                break;
            case R.id.rl_book_collection:
                if (AnalysisUtils.readLoginStatus(getActivity())){
                    //跳转到BookCollection页面
                    Intent intent=new Intent(getActivity(), BookCollectionActivity.class);
                    getActivity().startActivityForResult(intent,1);
                    Toast.makeText(getActivity(), "Book Collection", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Please log in first.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_setting:
                if (AnalysisUtils.readLoginStatus(getActivity())){
                    Intent intent=new Intent(getActivity(), SettingActivity.class);
                    getActivity().startActivityForResult(intent,1);
                }else {
                    Toast.makeText(getActivity(), "Please log in first.", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

}
