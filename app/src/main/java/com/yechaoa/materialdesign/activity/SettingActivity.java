package com.yechaoa.materialdesign.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.utils.AnalysisUtils;

import butterknife.BindView;

public class SettingActivity extends ToolbarActivity implements View.OnClickListener{

    @BindView (R.id.rl_modify_psw)
    RelativeLayout rl_modify_psw;
    @BindView (R.id.rl_modify_description)
    RelativeLayout rl_modify_description;
    @BindView (R.id.rl_exit_login)
    RelativeLayout rl_exit_login;

    public static SettingActivity instance=null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void setToolbar() {
        mToolbar.setTitle("Setting");
    }

//    @Override protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_setting);
//        instance=this;
//        initView();
//    }
    @Override
    protected void initView(){
        rl_modify_psw.setOnClickListener(this);
        rl_modify_description.setOnClickListener(this);
        rl_exit_login.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_modify_psw: //修改密码界面
                Intent intent=new Intent(SettingActivity.this,ModifyPswActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_modify_description:
                //Modify description
                Toast.makeText(this,"Modify Description",Toast.LENGTH_SHORT).show();
                Intent anotherIntent=new Intent(SettingActivity.this,ModifyDescriptionActivity.class);
                startActivity(anotherIntent);
                break;
            case R.id.rl_exit_login:  //退出登录，即清除登录状态
                Toast.makeText(this,"Exit succeed!",Toast.LENGTH_SHORT).show();
                AnalysisUtils.cleanLoginStatus(this);
                Intent data=new Intent();
                data.putExtra("isLogin",false);
                setResult(RESULT_OK,data);
                finish();
                break;
            default:
                break;
        }
    }
}
