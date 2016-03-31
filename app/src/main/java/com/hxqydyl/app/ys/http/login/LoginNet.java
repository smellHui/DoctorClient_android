package com.hxqydyl.app.ys.http.login;

import com.hxqydyl.app.ys.bean.register.DoctorInfo;
import com.hxqydyl.app.ys.bean.register.DoctorResult;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.utils.Constants;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * 登陆请求网络
 * Created by hxq on 2016/3/3.
 */
public class LoginNet {

    private OnLoginNetListener mListener;

    public void setmListener(OnLoginNetListener mListener){
        this.mListener = mListener;
    }

    public interface  OnLoginNetListener{
        void  requestLoginNetSuccess(DoctorResult doctorResult);
        void  requestLoginNetFail(int statusCode);
    }

    public void loginData(String mobile,String password){
        OkHttpUtils
                .post()
                .url("http://172.168.1.57/app/service/customer/1.0/addCaseGroup")
                .addParams("doctorUuid", "ctorAdvice0000000061002")
                .addParams("groupName", "中国")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        mListener.requestLoginNetFail(Constants.REQUEST_FAIL);
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("response---->"+response);
//                        try {
//                            mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(StringUtils.cutoutBracketToString(response)));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                });


     /*   OkHttpUtils
                .post()
                .url(Constants.LOGIN_URL)
                .addParams("mobile", mobile)
                .addParams("password", password)
                .addParams("callback",Constants.CALLBACK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        mListener.requestLoginNetFail(Constants.REQUEST_FAIL);
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("response---->"+response);
                        try {
                            mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(StringUtils.cutoutBracketToString(response)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });*/
    }

}
