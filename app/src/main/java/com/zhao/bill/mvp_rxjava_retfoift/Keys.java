package com.zhao.bill.mvp_rxjava_retfoift;

import android.os.Environment;

import java.io.File;

/**
 * APP常量
 * Created by David on 16/3/7.
 */
public final class Keys {

    // 科室日程   科室相关
    public static final String EDIT_AFFAIR = "edit_affair";
    public static final String AFFAIR_TIME = "affair_time";
    public static final String AFFAIR_NAME = "affair_name";
    public static final String REMIND_PATIENT = "remind_patient";
    public static final String REMIND_TIME = "remind_time_selected";
    public static final String REMIND_TEXT = "remind_text";
    public static final String REMIND_DOCTOR = "remind_doctor";
    public static final String COMMENT = "comment";
    public static final String ID = "id";
    public static final String LABELALL = "labelall";
    public static final String DEP_NOTICE_ID = "notice_id";//部门公告ID
    public static final String DEP_NOTICE = "dep_notice";//部门公告
    public static final String DEP_INTRO = "dep_intro";//科室简介
    public static final String NAME_LIST = "name_list";


    // 随访相关
    public static final String FOLLOWUP_PLAN_EVENT_TYPE = "followup_plan_event_type";//随访方案事件类型
    public static final String IS_FOLLOWUP_PLAN_SELECTED = "is_followup_plan_selected";//是否选择
    public static final String IS_SELECTED = "is_followup_selected";//是否选择状态
    public static final String EXTRA_FOLLOWUP_TABLE_ID = "extra_followup_table_id";
    public static final String DATA_BASE = "database";//当前病例库名称
    public static final String PATIENT_ID = "patient_id";//患者病例ID
    public static final String PATIENT_FORM_ID = "patients_form";//基本信息表单id
    public static final String PATIENT_WX_ID = "wx_pid";//患者微信ID
    public static final String IS_HASPID = "is_haspid";//是否关联到病历
    public static final String IS_CASE_LIST = "is_case_list";//是否打开病历列表
    public static final String PATIENT_DATABASE = "database";//患者ID
    public static final String DEPART_USER_ID = "userid";//获取同科室其他医生的随访方案用
    public static final String FOLLOWUP_PLAN_ID = "followup_plan_id";//随访方案ID
    public static final String IS_EMPTY_FOLLOWUP_PLAN = "is_empty_followup_plan"; //是否为空方案


    // 我的
    public static final String PARAM_TYPE = "param_type";//参数类型
    public static final String PARAM_NICKNAME = "param_nickname";//昵称
    public static final String PARAM_EMAIL = "param_email";//邮箱
    public static final String PARAM_PHONE = "param_email";//手机号
    public static final String PARAM_CONTENT = "param_content";//内容
    public static final String PREFERENCE_LOCK_PASSWORD = "preference_lock_password"; //本地锁密码
    public static final String PREFERENCE_IS_SAVE_PHOTO = "preference_is_save_photo"; //拍照同时保存到相册
    public static final String PREFERENCE_IS_NOTIFICATION = "preference_is_notification"; //聊天的时候是否打开推送
    public static final String PREFERENCE_OUTTIME = "preference_outtime_list";


    // 标签
    public static final String LABEL_TYPE = "label_type";//标签类型
    public static final String LABEL_ID = "label_id"; //标签ID
    public static final String LABLE_NAME = "label_name"; //标签Name
    public static final String IS_EDIT_LABEL = "is_edit_label";//修改标签
    public static final String CHOOSE_PATIENT = "choose_patient";
    public static final String CHOOSE_PATIENT_ID = "choose_patient_id";
    public static final String CHOOSE_CASE_ID = "choose_case_id";
    public static final String RULES_DESC = "rules_desc";//条件描述


    // 患者教育详情
    public static final String EDUCATION_URL = "education_url";
    public static final String EDUCATION_TITLE = "education_title";
    public static final String EDUCATION_ID = "education_id";
    public static final String EDUCATION_SHARECONTENT = "shareContent";


    // 页面跳转标记
    public static final String JUMP_FROM_AFFAIR_DETAIL = "jump_from_affair_detail";
    public static final String JUMP_FROM_DEPART_AFFAIR = "jump_from_depart_affair";
    public static final String JUMP_FROM_CHAT = "jump_from_chat";
    public static final String JUMP_FROM_CHAT_GROUP = "jump_from_chat_group";
    public static final String JUMP_FROM_CHAT_SINGLE = "jump_from_chat_single";
    public static final String JUMP_FROM_CHAT_GROUP_AFFAIR = "jump_from_chat_group_affair";
    public static final String JUMP_FROM_CHATADAPTER = "jump_from_chatadapter";
    public static final String JUMP_FROM_PATIENT_UPLOAD = "jump_from_patient_upload";
    public static final String PATIENT_INFO = "patient_info";
    public static final String JUMP_TO_ALL_PATIENT = "jump_to_all_patient";
    public static final String JUMP_TO_VIEWCASE = "jump_to_viewcase";
    public static final String JUMP_TO_ASSCASE = "jump_to_asscase";
    public static final String JUMP_TO_UPLOADCASE = "jump_to_uploadcase";
    public static final String JUMP_FROM_EDUCATION_DETAIL = "jump_from_education_detail";
    public static final String JUMP_FROM_TABLE_DETAIL = "jump_from_table_detail";
    public static final String JUMP_FROM_SPECIAL_ATTENTION = "jump_from_special_attention";
    public static final String JUMP_FROM_ADVANCESEARCH = "jump_from_advancesearch";
    public static final String JUMP_FROM_NORMALSEARCH = "jump_from_normalsearch";
    public static final String JUMP_FROM_CASELIST = "jump_from_caselist";
    public static final String JUMP_FROM_VIEWCASE = "jump_from_viewcase";
    public static final String JUMP_FROM_PATIENTINFO = "jump_from_patientinfo";
    public static final String JUMP_FROM_LINKEDCASE = "jump_from_linkedcase";
    public static final String JUMP_FROM_FOLLOWDEPART = "jump_from_followdepart";
    public static final String JUMP_FROM_PROPSE = "jumpFromPropose";
    public static final String JUMP_FROM_PHOTOLIST = "jumpFromPhotoList";
    public static final String JUMP_FROM_TOOL_SLIDE = "jumpFromToolSlide";


    // 其他
    public static final String STRUCTURE_WINDOW = "structure_window";
    public static final String DEPART_AFFAIR_REMIND = "depart_affair_remind";
    public static final String DESC_LENGTH = "desc_length";
    public static final String RESULT = "result";
    public static final String AGE = "age";
    public static final String SPLIT = "split";


    // 公共参数
    public static final String DEVICE_TOKEN = "device_token";
    public static final String SIGN_TIME = "appSignTime";
    public static final String APP_SIGN = "appSign";
    public static final String DEVICE_UUID = "device_uuid";
    public static final String LOGIN_TOKEN = "token";
    public static final String DB_NAME = "dbname";
    public static final String APP_ID = "appid";
    public static final String APP_VERSION = "version";

    public static final String HTTP_CODE = "code";
    public static final String HTTP_MESSAGE = "message";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";
    public static final String IS_AUTO_LOGIN = "is_auto_login";
    public static final String IS_FIRST_INTO_VIEW_CASE = "is_firsi_into_view_case";//记录程序是否是第一次进入病例详情页面改版之后的新的页面

    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_NUMBER = "page";

    public static final String TITLE_ID = "title_id";//页面标题

    public static final String TABLE_NAME = "table_name";//表单名字
    public static final String TABLE_URL = "table_url";//表单url
    public static final String FORM_ID = "form_id";//页面ID

    public static final String CHAT_FORM = "chat_form"; // 图标Name
    public static final String CHAT_FIELD = "chat_field"; //图标属性

    public static final String REPLAY_ID = "replay_id";//快捷回复ID
    public static final String IS_GROUP = "is_group";
    public static final String IS_CHOICE = "is_choice";

    public static final String MEDBANKS_PREFERENCE = "medbanks_preference";
    public static final String PREFERENCE_USERINFO = "preference_userinfo";
    public static final String PREFERENCE_DBNAME = "preference_db_name";
    public static final String PREFERENCE_IS_NEW_DETAILS = "preference_is_new_details";
    public static final String PREFERENCE_DBNAME_WORD = "preference_db_name_word";


    // 图片上传   上传病例
    public static final String ACTION_UPLOAD_IMG = "intentservice.action.UPLOAD_IMAGE"; // 上传图片
    public static final String UPLOAD_STATE = "intentservice.extra.UPLOAD_STATE"; // 上传成功或者失败状态
    public static final String UPLOAD_RESULT = "intentservice.UPLOAD_RESULT"; //上传结果
    public static final String PID = "pid"; //病历id图片上传时需要携带
    public static final String SIGN = "sign"; //图片上传唯一标示 图片上传时需要携带
    public static final String TIMEMARK = "timemark"; //图片上传时间
    public static final String PHOTOUPLOADBENA = "photouploadbena"; //图片上传绑定的数据
    public static final String PERMISSION = "permission"; // 权限
    public static final String CURRENTPOSITION = "currentPosition"; // 当前点击图片的位置


    // 患者日程  随访消息
    public static final String SCHE_ID = "schedule_id"; // 患者日程提醒id
    public static final String UPDATE_APK_PROGRESS = "progress";//患者微信ID
    public static final String URL_WEBVIEW = "url_webview";
    public static final String RED_POT = "red_pot";// 红点
    public static final String RED_POT_REPOR = "red_pot";// 红点--科室月报告
    public static final String JUMP_STATUS = "jump_status";//消息中心  是否可跳转
    public static final String SHARE_CONTENT = "share_content";//消息中心  分享内容
    public static final String READ_MONTH_REPORT = "read_month_report";// 月报推送
    public static final String IS_DOUBLE_SELECTED_MODE = "is_double_selected_mode"; //是否为多选模式
    public static final String REGISTER = "register";
    public static final String RELATE = "relate";
    public static final String TABLE_ID = "table_id";
    public static final String PLAN = "plan"; //随访方案


    // 搜索条件字段
    public static final String CASE_FORMS = "case_forms_code";//高级搜索关键字
    public static final String CASE_SEARCH = "case_search_code";//普通搜索关键字
    public static final String CASE_DATETIME = "case_datetime";//病历列表每月
    public static final String CASE_COLLETION = "case_is_colletion";//收藏
    public static final String CASE_FOLLOWUP = "case_is_followup";//收藏
    public static final String GROUP_ID = "group_id";//分组
    public static final String CASE_ORDER = "order_field";// 排序条件
    public static final String SEARCH_HISTORY = "search_history";//搜索历史
    public static final String PREFERENCE_SEARCH_FIELDS = "preference_search_fields"; //高级搜索条件
    public static final String PREFERENCE_PERMISSION_DATA = "preference_permission_data"; //首页接口返回的权限的数据的sp里面的key
    public static final String PREFERENCE_LABEL_CHOOSE = "preference_label_choose"; //标签分类筛选
    public static final String PREFERENCE_LABEL_All = "preference_label_all"; //所有库下  所有标签


    // apk下载
    public final static String CommonAction = "medbanks.common.action";// 程序的广播的通用过滤字段
    public static final String responseBody = "DataBody";// 广播携带数据的key
    public static final String BroadCast_Type_Key = "BroadCast_Type_Key";// 广播类型的key
    public static final String TYPE_APK_UPDATE = "APK_UPDATE";// 广播类型为apk需要更新
    public static final String TYPE_APK_EXPERIENCE = "APK_EXPERIENCE";// 广播类型为apk需要对体验账户进行拦截
    public static final String TYPE_APK_HAS_DOENLOAD = "APK_HAS_DOENLOAD";// 广播类型为apk下载完成

    public static final String HTTP_LOG_KEY = "http";// 网络数据日志的打印的tag
    public static final String PIC = "pic";
    public static final String PALN_TYPE = "paln_type";
    public static final String WX_NAME = "wx_name";
    public static final String PLAN_LIST = "plan_list";
    public static boolean DEBUG = BuildConfig.DEBUG;// 日志打印开关

    public final static String IS_DOWNLOAD_SUCCESS = "IS_DOWNLOAD_SUCCESS";// apk是否下载完成的标志的key，用于SharedPreferences
    public static final String APK_UPDATE_TIME = "apk_update_time";//apk的检查更新的时间
    public final static String APK_FILE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator
            + "MedBanks" + File.separator;// apk文件的保存基路径

    public final static String PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";// apk安装完成
    public final static String PACKAGE_REPLACE = "android.intent.action.PACKAGE_REPLACED";// apk覆盖安装完成
    public static final String INSTALL_SUCCESS = "install_success";
    public static final String URL = "url";


    // 病例中图片上传状态
    /**
     * 正在录入中---上传成功包含在其中
     */
    public static final int RECORDING = 0; // 录入中
    public static final int UPLOADSUCCESS = 4; // 成功
    /**
     * 录入完成
     */
    public static final int INPUTCOMPLETION = 1;
    /**
     * 上传失败
     */
    public static final int UPLOADFAIL = 2;
    /**
     * 正在上传中
     */
    public static final int UPLOADING = 3;

    /**
     * 显示更新时间   不显示状态
     */
    public static final int SHOWTIME = 5;

    /**
     * 默认选择集
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";
    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";
    /**
     * 图片选择模式，默认多选
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /**
     * 最大图片选择次数，int类型，默认12
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * 是否显示相机，默认显示
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";

    /**
     * 图片选择事件类型,默认为选择图片
     */
    public static final String EXTRA_EVENT_MODE = "select_event_mode";
    /**
     * 是否保存照片到相册
     */
    public static final String EXTRA_SAVE_PHOTO = "save_photo";

    public static final String FIRST_IN = "first_in";  // 用户第一次登陆

}
