package com.yechaoa.materialdesign.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.utils.AnalysisUtils;

import butterknife.BindView;

public class ModifyBookDescriptionActivity extends ToolbarActivity implements View.OnClickListener {

    @BindView(R.id.til_book_description)
    TextInputLayout til_description;
    @BindView(R.id.btn_modify_book_description)
    Button btn_modify_description;

    private String userName;
    private String title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_book_description;
    }

    @Override
    protected void setToolbar() {
        mToolbar.setTitle("Modify Description");
    }

    @Override
    protected void initView(){
        userName = AnalysisUtils.readLoginUserName(this);

        // 从Intent获得book_title
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("bookTitle");
        if(title==null){ title = "No found."; }

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

        //Todo:将userName, title, description传到后端
        Toast.makeText(this, "Description Ok", Toast.LENGTH_SHORT).show();

        ModifyBookDescriptionActivity.this.finish();
    }
}
