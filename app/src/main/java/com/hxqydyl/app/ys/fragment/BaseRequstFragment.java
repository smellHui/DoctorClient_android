package com.hxqydyl.app.ys.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.bean.request.ParamsBean;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.common.AppContext;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.CommonUtils;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.xus.http.httplib.https.HttpUtil;
import com.xus.http.httplib.interfaces.HttpUtilBack;
import com.xus.http.httplib.model.BaseParams;
import com.xus.http.httplib.model.GetParams;
import com.xus.http.httplib.model.PostPrams;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import galleryfinal.wq.photo.widget.PickConfig;
import galleryfinal.yalantis.ucrop.UCrop;

/**
 * Created by wangxu on 2016/4/20.
 */
public class BaseRequstFragment<T> extends BaseFragment implements HttpUtilBack {
    public HttpUtil httpUtil = new HttpUtil(this);
    public Gson gson = new Gson();
    private SweetAlertDialog pDialog;
    private int pickPic = 1;//选择图片模式
    private int pickNum = 1;//允许选择张数
    private static final int CODE_FOR_WRITE_PERMISSION = 1119;
    private boolean isTest = CommonUtils.isTest(AppContext.getInstance());

    public void showDialog(String text) {
        if (!(pDialog != null && pDialog.isShowing())) {
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setCancelable(true);
            pDialog.show();
        }
        pDialog.setTitleText(text);

    }

    public void dismissDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismissWithAnimation();
        }
    }

    @Override
    public <T> void onSuccess(int i, String s, Class<T> aClass, Map<String, String> map) {
        try {
            Log.e("wangxu", "cookie" + map.get("Cookie"));
            Log.e("wangxu", "params" + map.get("params"));
            Log.e("wangxu", "json=" + s);
            SharedPreferences.getInstance().putString("Http_Cookie", map.get("Cookie"));
            dismissDialog();
            if (map.get("IsString").equals("false")) {
                BaseResponse t = (BaseResponse) gson.fromJson(s, aClass);
                if (t.code == 200 || (t.query != null && !TextUtils.isEmpty(t.query.success) && t.query.success.equals("1")) || (t.value != null && t.value.equals("true"))) {
                    onSuccessToBean(t, i);
                } else if (t.code != 200 && !TextUtils.isEmpty(t.message)) {
                    if (t.code == 500) {
                        UIHelper.ToastMessage(getActivity(), "服务器正在升级，请稍后再试");
                    } else {
                        UIHelper.ToastMessage(getActivity(), t.message);
                        if (isTest && t.code != 406) {
                            DialogUtils.showNormalDialog(getActivity(), "此弹框仅在测试弹出", "服务器错误:请测试人员区分是否为bug后记录-\nurl:" + map.get("url") + "\n请求数据:" + map.get("params") + "\n请求方式:" + map.get("httpType") + "\n" + "返回数据:" + s);
                        }
                    }
                } else if (t.query != null && !TextUtils.isEmpty(t.query.message)) {
                    UIHelper.ToastMessage(getActivity(), t.query.message);
                } else {
                    UIHelper.ToastMessage(getActivity(), "请求异常！请稍后再试");
                    if (isTest) {
                        DialogUtils.showNormalDialog(getActivity(), "此弹框仅在测试弹出", "服务端请求头有误，请确认json\n" + s);
                    }
                }
            } else {
                onSuccessToString(s, i);
            }
        } catch (Exception e) {
            Log.e("wangxu", e.toString());
            UIHelper.ToastMessage(getActivity(), "加载失败，请稍后再试");
            if (isTest) {
                DialogUtils.showNormalDialog(getActivity(), "此弹框仅在测试弹出", "android程序内部错误" + e.toString());
            }
            onfail(i, 9999, map);
        }
    }

    @Override
    public void onfail(int i, int i1, Map<String, String> map) {
        dismissDialog();
        UIHelper.ToastMessage(getActivity(), "加载失败，请稍后再试");
    }

    public <T> void onSuccessToBean(T bean, int flag) {

    }

    public void onSuccessToString(String json, int flag) {

    }

    /**
     * @param params 使用toPostParams或者toGetParams方法
     * @param flag   表示该次请求的flag
     * @param url    请求地址
     *               <p/>
     *               onSuccessString中回调
     */
    public void toNomalNetStringBack(BaseParams params, int flag, String url, String showdialog) {

        Map<String, String> map = new HashMap<>();
        map.put("IsString", "true");
        map.put("url", url);
        map.put("params", params.toString());
        doNet(params, flag, url, map,null,showdialog);

    }

    /**
     * 普通请求，默认转换为bean类
     *
     * @param params 使用toPostParams或者toGetParams方法
     * @param aClass 转换成bean类的class
     * @param flag   表示该次请求的flag
     * @param url    请求地址
     *               <p/>
     *               在onSuccessToBean中回调
     */
    public void toNomalNet(BaseParams params, Class<T> aClass, int flag, String url, String showdialog) {
        Map<String, String> map = new HashMap<>();
        map.put("IsString", "false");
        map.put("url", url);
        map.put("params", params.toString());
        doNet(params, flag, url, map,aClass,showdialog);

    }

    public void toNomalNet(BaseParams params, int flag, String url, String showdialog) {
        Map<String, String> map = new HashMap<>();
        map.put("IsString", "false");
        doNet(params, flag, url, map,null,showdialog);

    }

    private void doNet(BaseParams params, int flag, String url, Map<String, String> map,Class<T> tClass, String showdialog){
        if (!TextUtils.isEmpty(showdialog)) {
            showDialog(showdialog);
        }
        if (!TextUtils.isEmpty(SharedPreferences.getInstance().getString("Http_Cookie", ""))) {
            params.addHeader("cookie", SharedPreferences.getInstance().getString("Http_Cookie", ""));
        }
        if (params instanceof GetParams) {
            GetParams get = (GetParams) params;
            httpUtil.doGet(flag, url, get,tClass!=null?tClass:BaseResponse.class, map);
        } else if (params instanceof PostPrams) {
            PostPrams post = (PostPrams) params;
            httpUtil.doPost(flag, url, post, tClass!=null?tClass:BaseResponse.class, map);
        }
    }

    public GetParams toGetParams(ParamsBean... keys) {
        return toGetParams(null, keys);
    }

    public PostPrams toPostParams(ParamsBean... keys) {
        return toPostParams(null, keys);
    }

    /**
     * @param keys 通过toParamsBaen获取键值对
     * @return
     */
    public PostPrams toPostParams(Map<String, String> header, ParamsBean... keys) {
        PostPrams params = new PostPrams();
        for (ParamsBean s : keys) {
            params.put(s.getKey(), s.getValue());
        }
        if (header != null) {
            params.setHeader(header);
        }
        return params;
    }

    public PostPrams toPostFileParams(Map<String, String> header, ParamsBean... keys) {
        PostPrams params = new PostPrams();
        for (ParamsBean s : keys) {
            params.addFilePrams(s.getKey(), s.getValue());
        }
        if (header != null) {
            params.setHeader(header);
        }
        return params;
    }

    public PostPrams toPostFileParams(ParamsBean... keys) {
        PostPrams params = new PostPrams();
        for (ParamsBean s : keys) {
            params.addFilePrams(s.getKey(), s.getValue());
        }
        return params;
    }

    public GetParams toGetParams(Map<String, String> header, ParamsBean... keys) {
        GetParams params = new GetParams();
        for (ParamsBean s : keys) {
            params.put(s.getKey(), s.getValue());
        }
        if (header != null) {
            params.setHeader(header);
        }
        return params;
    }

    /**
     * 填入表单值
     *
     * @param key
     * @param value
     * @return
     */
    public ParamsBean toParamsBaen(String key, String value) {
        return new ParamsBean(key, value);
    }


    public void access(int pickPic, int picknum) {
        this.pickNum = picknum;
        this.pickPic = pickPic;
        int hasWriteContactsPermission = 0;
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            hasWriteContactsPermission = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        CODE_FOR_WRITE_PERMISSION);

                return;
            }
        }
        showEditPhotoLayout();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_FOR_WRITE_PERMISSION) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意使用write
                showEditPhotoLayout();
            } else {
                //用户不同意，自行处理即可
                // finish();
            }
        }
    }

    public void showEditPhotoLayout() {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        new PickConfig.Builder(getActivity())
                .isneedcrop(false)
                .actionBarcolor(Color.parseColor("#1F80B8"))
                .statusBarcolor(Color.parseColor("#FFFFFF"))
                .isneedcamera(true)
                .isSqureCrop(false)
                .setUropOptions(options)
                .maxPickSize(pickNum)
                .spanCount(Integer.parseInt("3"))
                .pickMode(pickPic).build();
    }

}
