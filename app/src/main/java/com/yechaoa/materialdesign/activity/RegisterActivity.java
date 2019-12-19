package com.yechaoa.materialdesign.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.model.dao.Constant;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends ToolbarActivity implements View.OnClickListener{

    boolean add_success = true;
    String userName;
    String psw;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    @BindView(R.id.til_name_register)
    TextInputLayout mTilName_register;
    @BindView(R.id.til_password_register)
    TextInputLayout mTilPassword_register;
    @BindView(R.id.til_password_register_again)
    TextInputLayout mTilPassword_register_again;
    @BindView(R.id.et_name_register)
    TextInputEditText mEtName_register;

    @BindView(R.id.btn_register_over)
    Button btnRegisterOver;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void setToolbar() {
        mToolbar.setTitle(R.string.register);
    }

    @Override
    protected void initView() {
        mEtName_register.addTextChangedListener(mTextWatcher);

        btnRegisterOver.setOnClickListener(this);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (mTilName_register.getEditText().getText().length() > mTilName_register.getCounterMaxLength())
                mTilName_register.setError("输入内容超过上限");
            else
                mTilName_register.setError(null);
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_over:
                //注册成功的处理，跳回登陆界面
                Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show();

                //获得用户名和两次密码
                userName = mTilName_register.getEditText().getText().toString().trim();
                psw = mTilPassword_register.getEditText().getText().toString().trim();
                String pswAgain = mTilPassword_register_again.getEditText().getText().toString().trim();


                //判断输入框内容
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "Please enter your username.", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "Please enter password again.", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!psw.equals(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "Different passwords.", Toast.LENGTH_SHORT).show();
                    return;
                    /** *从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名 */
                    //SharedPreferences换成从数据库（后端）读取
                }else{
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            addUserInfo();
                            if (!add_success) {
                                Toast.makeText(RegisterActivity.this, "This username is used.", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(RegisterActivity.this, "Succeed!", Toast.LENGTH_SHORT).show();
                                //把账号、密码和账号标识保存到sp里面
                                /** * 保存账号和密码到SharedPreferences中 */
                                //SharedPreferences换成存入数据库，向后端传数据

                                saveRegisterInfo(userName, psw);
                                //注册成功后把账号传递到LoginActivity.java中
                                // 返回值到loginActivity显示
                                Intent data = new Intent();
                                data.putExtra("userName", userName);
                                setResult(RESULT_OK, data);
                                //RESULT_OK为Activity系统常量，状态码为-1，
                                // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                                add_success = false;
                                RegisterActivity.this.finish();
                            }
                        }
                    }).start();
                }

                break;
        }

    }


    //改成：SharedPreferences换成从数据库（后端）读取

    /** * 从SharedPreferences中读取输入的用户名，将用户信息插入，如果插入成功返回true，插入失败返回false重新输入 */
    private void addUserInfo(){
//        boolean has_userName= false;

//        mode_private SharedPreferences sp = getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE

//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                OkHttpClient okHttpClient = new OkHttpClient();
//                HashMap<String,String> paramsMap=new HashMap<>();
//                paramsMap.put("password", psw);
//                paramsMap.put("name", userName);
//
//
//                paramsMap.put("id","1");
//
//                Gson gson=new Gson();
//                String data=gson.toJson(paramsMap);
//
//
//                RequestBody formBody;
//                formBody=RequestBody.create(JSON,data);
//                Request request=new Request.Builder().url(Constant.ADD).post(formBody).build();
//
//                try (Response response = okHttpClient.newCall(request).execute()) {
//                    Looper.prepare();
//                    String answer = response.body().string();
//                    Boolean t = Boolean.parseBoolean(answer);
//                    add_success = t;
//                    Looper.loop();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


//        new Thread(new Runnable(){
//            @Override
//            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("password", psw);
                paramsMap.put("name", userName);


                paramsMap.put("id","1");

                Gson gson=new Gson();
                String data=gson.toJson(paramsMap);


                RequestBody formBody;
                formBody=RequestBody.create(JSON, data);
                Request request=new Request.Builder().url(Constant.ADD).post(formBody).build();

                try (Response response = okHttpClient.newCall(request).execute()) {
                    Looper.prepare();
                    String answer = response.body().string();
                    Boolean t;
                    if(answer.equals("1")) {
                        t = true;
                    } else {
                        t = false;
                    }
                    add_success = t;



//                    Looper.loop();


                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }
//        }).start();


//        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
//        //获取密码
//        String spPsw=sp.getString(userName, "");
        // 传入用户名获取密码
        // 如果密码不为空则确实保存过这个用户名，不可注册

//        if(!TextUtils.isEmpty(spPsw)) {
//            add_success = false;
//        } else {
//            add_success = true;
//        }
    }

    private void saveRegisterInfo(String userName,String psw){
        // loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences();
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        // key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(userName, psw);
        //提交修改 editor.commit();
        editor.commit();
    }
}
