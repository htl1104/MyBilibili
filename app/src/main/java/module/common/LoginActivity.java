package module.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.rxjava.myblibi.R;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;
import utils.CommonUtil;
import utils.ConstantUtil;
import utils.PreferenceUtil;
import utils.ToastUtil;

/**
 * @author 小陈
 * @time 2018/1/13  10:43
 * @desc 登录页面
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.iv_icon_left)
    ImageView mLeftLogo;

    @BindView(R.id.iv_icon_right)
    ImageView mRightLogo;

    @BindView(R.id.delete_username)
    ImageView mDeleteUserName;

    @BindView(R.id.et_username)
    EditText et_username;

    @BindView(R.id.et_password)
    EditText et_password;
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        et_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (hasFocus && et_username.getText().length() > 0) {
                    mDeleteUserName.setVisibility(View.VISIBLE);
                } else {
                    mDeleteUserName.setVisibility(View.GONE);
                }

                mLeftLogo.setImageResource(R.drawable.ic_22);
                mRightLogo.setImageResource(R.drawable.ic_33);
                
            }
        });

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                mLeftLogo.setImageResource(R.drawable.ic_22_hide);
                mRightLogo.setImageResource(R.drawable.ic_33_hide);
                
            }
        });


        et_username.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 如果用户名清空了 清空密码 清空记住密码选项
                et_password.setText("");
                if (s.length() > 0) {
                    // 如果用户名有内容时候 显示删除按钮
                    mDeleteUserName.setVisibility(View.VISIBLE);
                } else {
                    // 如果用户名有内容时候 隐藏删除按钮
                    mDeleteUserName.setVisibility(View.GONE);
                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}


            @Override
            public void afterTextChanged(Editable s) {}
        });
        
    }

    @Override
    public void initToolBar() {
        mToolbar.setNavigationIcon(R.drawable.ic_cancle);
        mToolbar.setTitle("登录");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_login)
    void startLogin(){
        boolean isNetConnected = CommonUtil.isNetworkAvailable(this);
        if (!isNetConnected) {
            ToastUtil.ShortToast("当前网络不可用,请检查网络设置");
            return;
        }        
        login();        
    }

    @OnClick(R.id.delete_username)
    void delete() {
        // 清空用户名以及密码
        et_username.setText("");
        et_password.setText("");
        mDeleteUserName.setVisibility(View.GONE);
        et_username.setFocusable(true);
        et_username.setFocusableInTouchMode(true);
        et_username.requestFocus();
    }

    private void login() {
        String name = et_username.getText().toString();
        String password = et_password.getText().toString();
        
        if(TextUtils.isEmpty(name)) {
            ToastUtil.ShortToast("用户名不能为空");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.ShortToast("密码不能为空");
            return;
        }

        PreferenceUtil.putBoolean(ConstantUtil.KEY, true);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}
