package com.hxqydyl.app.ys.utils;

/**
 * 常量
 * Created by hxq on 2016/3/1.
 */
public class Constants {

    public static String CALLBACK = "xch";

    //请求网络成功
    public static int REQUEST_SUCCESS = 1100;
    //请求网络失败
    public static int REQUEST_FAIL = 1101;

    private static String url = /*"http://192.168.0.150:8080";*//*"http://172.168.1.30";*/"http://172.168.1.9";/*"http://172.168.1.63";*//*"http://172.168.1.10";*/
    public static String LOGIN_URL = url+"/app/pub/doctor/gotoLogin";


    //获取轮播图
    public static String GET_PLATFORMPIC = url+"/app/pub/doctor/getPlatformPic";

    //手机获取验证码
    public static String GET_VERIFICATION_CODE=url+"/app/pub/doctor/getVerificationCode";
   //手机新用户第一步
    public static String REGISTER_ONE = url+"/app/pub/doctor/registerOne";
   //第二步
    public static String REGISTER_TWO = url+"/app/pub/doctor/registerTwo";
   //第三步
    public static String REGISTER_THREE = url+"/app/pub/doctor/registerThree";
    //修改密码
    public static String UPDATE_PASSWORD = url+"/app/pub/doctor/updatePassword";
    //退出登陆
    public static String STAFF_OUT_OF = url+"/app/pub/doctor/staffOutOf";

    //获取医生基本信息（新添加）
    public static  String GET_DOCTOR_INFO = url+"/app/service/doctor/getDoctorInfo";

    //获取省
    public static String GET_PROVINCE = url+"/app/service/doctor/getProvince";
    //获取市
    public static String GET_CITY = url+"/app/service/doctor/getCity";
    //获取区县
    public static String GET_REGION = url+"/app/service/doctor/getRegion";
    //获取医院
    public static String GET_HOSPITAL = url+"/app/service/doctor/getHospital";
    //获取科室
    public static String GET_DEPARTMENT = url+"/app/service/doctor/getDepartment";
    //获取标签
    public static String GET_TAGS = url+"/app/service/doctor/getTags";

    //上传图片
    public static String UPLOAD_IMAGE = url+"/app/pub/doctor/uploadIcon";
    //上传完善用户图片信息
    public static  String SAVE_USER_ICON_LIST = url+"/app/service/doctor/saveUserIconList";
    //上传图片集
    public static String UPLOAD_IMGS = url +"/app/support/common/uploadimg";

    //获取阅读列表
    public static String GET_READING = "http://172.168.1.41/html/thedoctorinformation/index.shtml";/*"http://admin.hxqydyl.com/html/thedoctorinformation/index.shtml";*/
    //获取讲堂列表信息
    public static String GET_VIDEOS = "http://172.168.1.41/html/lecture/all_course.shtml";
    //获取诊所
    public static String GET_CLINIC = "http://172.168.1.41/html/clinic/index.shtml";
    //我的患者
    public static String MY_PATIENT = "http://172.168.1.41/html/mypatient/index.shtml";
    //我的任务
    public static String MY_TASK = "http://172.168.1.41/html/task/my_task.shtml";
    //个人中心
    public static String USER_INFO = "http://172.168.1.41/html/user/index.shtml";
   //患教库
    public static String PATIENT_EDUCATION = "http://172.168.1.41/html/follow_up/toolbox/patient_education.shtml";


    //患者分组
    public static String GET_PATIENT_GROUP = url + "/app/service/customer/1.0/getGroupAndCustomer";
    //医生群发消息给患者
    public static String ADD_INNER_MSG = url+"/app/service/customer/1.0/addInnerMessage";



}
