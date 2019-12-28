package com.yechaoa.materialdesign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.activity.BookCollectionActivity;
import com.yechaoa.materialdesign.activity.LoginActivity;
import com.yechaoa.materialdesign.activity.MainActivity;
import com.yechaoa.materialdesign.activity.SettingActivity;
import com.yechaoa.materialdesign.model.dao.Constant;
import com.yechaoa.materialdesign.utils.AnalysisUtils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FragmentMe extends MyFragment implements View.OnClickListener{

    private LinearLayout llHead;
    private RelativeLayout rlBookCollection, rlSetting;
    private ImageView ivHeadIcon, ivBookCollectionIcon, ivUserInfoIcon;
    private TextView tvUserName, tvUserDescription;

    String userName;
//    String description;

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
        userName = AnalysisUtils.readLoginUserName(getActivity());

        changeInfo();

        llHead.setOnClickListener(this);
        rlBookCollection.setOnClickListener(this);
        rlSetting.setOnClickListener(this);

    }

    public void changeInfo() {

        if (AnalysisUtils.readLoginStatus(getActivity())) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = Constant.LISTUSERBYNAME + "/" + userName;
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();


                try (Response response = okHttpClient.newCall(request).execute()) {
                    Looper.prepare();

                    String answer = response.body().string();

                    JsonParser parser = new JsonParser();
                    JsonElement json = parser.parse(answer);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String temp = gson.toJson(json);
                    JsonArray jsonArray = parser.parse(temp).getAsJsonArray();

                    JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                    final String description = jsonObject.get("discribe").getAsString();


//                        final String result = response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /*
                             * 这里写的东西都会在UI线程执行，我们可以将result传进来
                             * 也可以在这里获取主线程View的实例，更新UI
                             * 注意result要添加final修饰，否则会报错
                             */
//                            if (AnalysisUtils.readLoginStatus(getActivity())) {
                                tvUserName.setText(userName);
                                //调用后端返回用户的详细信息，得到用户的描述description
                                tvUserDescription.setText(description);
//                            } else {
//                                tvUserName.setText("Click to Login");
//                                tvUserDescription.setText("There is nothing, please login first.");
//                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

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
                    //Todo:跳转到个人资料界面or别的什么
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

    @Override public void refresh() {}

}
