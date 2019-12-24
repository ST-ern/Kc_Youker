package com.yechaoa.materialdesign.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.model.dao.Constant;
import com.yechaoa.materialdesign.utils.AnalysisUtils;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ModifyPswActivity extends ToolbarActivity implements View.OnClickListener {

    @BindView(R.id.til_old_psw)
    TextInputLayout til_old_psw;
    @BindView(R.id.til_new_psw)
    TextInputLayout til_new_psw;
    @BindView(R.id.btn_modify_psw)
    Button btn_modify_psw;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String userName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_psw;
    }

    @Override
    protected void setToolbar() {
        mToolbar.setTitle("Modify Password");
    }

    @Override
    protected void initView(){
        userName = AnalysisUtils.readLoginUserName(this);

        btn_modify_psw.setOnClickListener(this);
    }

    @Override public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_modify_psw:
                submit();
                break;
            default:
                break;
        }
    }


    private void submit() {
        String psw = til_old_psw.getEditText().getText().toString().trim();
        String newPsw = til_new_psw.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(psw)){
            Toast.makeText(this,"Please enter original password.",Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(psw)){
            Toast.makeText(this,"Please enter new password.",Toast.LENGTH_SHORT).show();
            return;
        }else if (!psw.equals(readPsw())){
            Toast.makeText(this,"Original password incorrect."+readPsw(),Toast.LENGTH_SHORT).show();
            return;
        }else {
            Toast.makeText(this,"Setting password succeed, please log in again.", Toast.LENGTH_SHORT).show();
            modifyPsw(newPsw);
            Intent intent=new Intent(ModifyPswActivity.this,LoginActivity.class);
            startActivity(intent);
            //关闭设置页面
            // 在submit方法中，密码修改成功之后除了把当前页面关了，还要把设置界面也关了，所以用到instance
//            SettingActivity.instance.finish();
            ModifyPswActivity.this.finish();
            //关闭修改密码页面 ModifyPswActivity.this.finish();
        }
    }

    //Todo:将修改后的新密码传到后端
    private void modifyPsw(String newPsw) {

        final String psw = newPsw;

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put("password", psw);
                paramsMap.put("name", userName);

                Gson gson = new Gson();
                String data = gson.toJson(paramsMap);


                RequestBody formBody;
                formBody = RequestBody.create(JSON, data);
                Request request = new Request.Builder().url(Constant.UPDATEPSW).post(formBody).build();

                try (Response response = okHttpClient.newCall(request).execute()) {
                    Looper.prepare();
                    final String answer = response.body().string();
                    Boolean t;
                    if (answer.equals("1")) {
                        t = true;
                    } else {
                        t = false;
                    }
                    final Boolean add_success = t;
                    //同名卡包添加失败的显示

                    ModifyPswActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (add_success) {
                                Toast.makeText(ModifyPswActivity.this, "Psw Ok", Toast.LENGTH_SHORT).show();
                                ModifyPswActivity.this.finish();
                            } else {
                                Toast.makeText(ModifyPswActivity.this, "Modify False.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userName,newPsw);
        editor.commit();
    }

    private String readPsw() {
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPsw=sharedPreferences.getString(userName,"");
        Log.i("username", userName);
        Log.i("spPsw", spPsw);
        return spPsw;
    }
}
