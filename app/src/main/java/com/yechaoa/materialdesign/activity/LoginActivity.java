package com.yechaoa.materialdesign.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.model.dao.Constant;
import com.yechaoa.materialdesign.utils.AnalysisUtils;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.*;

public class LoginActivity extends ToolbarActivity implements View.OnClickListener{

    @BindView(R.id.til_name)
    TextInputLayout mTilName;
    @BindView(R.id.til_password)
    TextInputLayout mTilPassword;
    @BindView(R.id.et_name)
    TextInputEditText mEtName;

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;

    String userName;
    String psw;
    String  password;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setToolbar() {
        mToolbar.setTitle("LOGIN");
    }

    @Override
    protected void initView() {
        mEtName.addTextChangedListener(mTextWatcher);

        //设置返回键不显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
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
            if (mTilName.getEditText().getText().length() > mTilName.getCounterMaxLength())
                mTilName.setError("输入内容超过上限");
            else
                mTilName.setError(null);
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                //登陆的处理
//                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();

                //获得用户名和密码
                userName = mTilName.getEditText().getText().toString().trim();
                psw = mTilPassword.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this, "Please enter your username.", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            //通过userName查询后端有无账号和密码，得到密码password

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
                                String temp=gson.toJson(json);
                                JsonArray jsonArray=parser.parse(temp).getAsJsonArray();

                                JsonObject jsonObject=jsonArray.get(0).getAsJsonObject();
                                password = jsonObject.get("password").getAsString();

//                                answer = " ";
                                //将answer（json）中的password提取出来
//                            String password = readPsw(userName);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                            if (TextUtils.isEmpty(password)) {
                                password = " ";
                            }

                            //在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                            saveLoginStatus(true, userName);

                            if (psw.equals(password)) {
                                //密码相同，登陆成功
                                //宣布成功
                                Toast.makeText(LoginActivity.this, "Succeed log in!", Toast.LENGTH_SHORT).show();
                                // 保存用户名和密码
                                saveUserInfo(userName, password);
                                //登录成功后关闭此页面进入主页
                                Intent data = new Intent();
                                //data.putExtra( ); name , value ;
                                data.putExtra("isLogin", true);
                                data.putExtra("userName", userName);
                                // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                                setResult(RESULT_OK, data);
                                //销毁登录界面
                                LoginActivity.this.finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect Info", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).start();
                }
                break;
            case R.id.btn_register:
                //跳转到注册界面
//                Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();

                //跳转等待结果
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent, 1);

                break;
        }

    }


    /** *从SharedPreferences中根据用户名读取密码 */
    //改成后端操作
    private String readPsw(String userName){
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(userName , "");
    }

    /** *保存登录状态和登录用户名到SharedPreferences中 */
    //改成后端操作
    private void saveLoginStatus(boolean status,String userName){
        //saveLoginStatus(true, userName);
        // loginInfo表示文件名 SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.commit();

    }


    /** * 注册成功的数据返回至此 * @param requestCode 请求码 * @param resultCode 结果码 * @param data 数据 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                //设置用户名到 et_user_name 控件
                mTilName.getEditText().setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                mTilName.getEditText().setSelection(userName.length());
            }
            data.putExtra("userName",userName);
        }
    }


    private void saveUserInfo(String userName,String psw){
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
