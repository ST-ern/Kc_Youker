package com.yechaoa.materialdesign.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.utils.AnalysisUtils;

import butterknife.BindView;

public class ModifyDescriptionActivity extends ToolbarActivity implements View.OnClickListener {

    @BindView(R.id.til_description)
    TextInputLayout til_description;
    @BindView(R.id.btn_modify_description)
    Button btn_modify_description;

    private String userName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_description;
    }

    @Override
    protected void setToolbar() {
        mToolbar.setTitle("Modify Description");
    }

    @Override
    protected void initView(){
        userName = AnalysisUtils.readLoginUserName(this);

        btn_modify_description.setOnClickListener(this);
    }

    @Override public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_modify_description:
                submit();
                break;
            default:
                break;
        }
    }

    private void submit() {
        String description = til_description.getEditText().getText().toString().trim();

        //Todo:将userName, description传到后端
        Toast.makeText(this, "Description Ok", Toast.LENGTH_SHORT).show();

        ModifyDescriptionActivity.this.finish();
    }
}
