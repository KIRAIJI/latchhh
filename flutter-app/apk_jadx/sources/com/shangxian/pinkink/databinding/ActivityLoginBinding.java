package com.shangxian.pinkink.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.shangxian.pinkink.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityLoginBinding implements ViewBinding {
    public final Button btnLoginOrRegister;
    public final EditText etAccount;
    public final EditText etVerifyCode;
    public final ImageView ivAccountClear;
    public final ImageView ivAgreementChecked;
    public final ImageView ivVerifyCodeClear;
    public final ImageView ivWechatLogin;
    public final LinearLayout llAgreement;
    private final LinearLayout rootView;
    public final TextView tvGetVerifyCode;
    public final TextView tvPrivacyAgreement;
    public final TextView tvUserProtocol;

    private ActivityLoginBinding(LinearLayout rootView, Button btnLoginOrRegister, EditText etAccount, EditText etVerifyCode, ImageView ivAccountClear, ImageView ivAgreementChecked, ImageView ivVerifyCodeClear, ImageView ivWechatLogin, LinearLayout llAgreement, TextView tvGetVerifyCode, TextView tvPrivacyAgreement, TextView tvUserProtocol) {
        this.rootView = rootView;
        this.btnLoginOrRegister = btnLoginOrRegister;
        this.etAccount = etAccount;
        this.etVerifyCode = etVerifyCode;
        this.ivAccountClear = ivAccountClear;
        this.ivAgreementChecked = ivAgreementChecked;
        this.ivVerifyCodeClear = ivVerifyCodeClear;
        this.ivWechatLogin = ivWechatLogin;
        this.llAgreement = llAgreement;
        this.tvGetVerifyCode = tvGetVerifyCode;
        this.tvPrivacyAgreement = tvPrivacyAgreement;
        this.tvUserProtocol = tvUserProtocol;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityLoginBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityLoginBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_login, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityLoginBinding bind(View rootView) {
        int i = R.id.btnLoginOrRegister;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.btnLoginOrRegister);
        if (button != null) {
            i = R.id.etAccount;
            EditText editText = (EditText) ViewBindings.findChildViewById(rootView, R.id.etAccount);
            if (editText != null) {
                i = R.id.etVerifyCode;
                EditText editText2 = (EditText) ViewBindings.findChildViewById(rootView, R.id.etVerifyCode);
                if (editText2 != null) {
                    i = R.id.ivAccountClear;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivAccountClear);
                    if (imageView != null) {
                        i = R.id.ivAgreementChecked;
                        ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivAgreementChecked);
                        if (imageView2 != null) {
                            i = R.id.ivVerifyCodeClear;
                            ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivVerifyCodeClear);
                            if (imageView3 != null) {
                                i = R.id.ivWechatLogin;
                                ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.ivWechatLogin);
                                if (imageView4 != null) {
                                    i = R.id.llAgreement;
                                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.llAgreement);
                                    if (linearLayout != null) {
                                        i = R.id.tvGetVerifyCode;
                                        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvGetVerifyCode);
                                        if (textView != null) {
                                            i = R.id.tvPrivacyAgreement;
                                            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvPrivacyAgreement);
                                            if (textView2 != null) {
                                                i = R.id.tvUserProtocol;
                                                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvUserProtocol);
                                                if (textView3 != null) {
                                                    return new ActivityLoginBinding((LinearLayout) rootView, button, editText, editText2, imageView, imageView2, imageView3, imageView4, linearLayout, textView, textView2, textView3);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
