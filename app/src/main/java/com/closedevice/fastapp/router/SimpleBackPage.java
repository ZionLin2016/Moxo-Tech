package com.closedevice.fastapp.router;



import com.closedevice.fastapp.ui.BkInside.fragment.AddNotifyFragment;
import com.closedevice.fastapp.ui.BkInside.fragment.SetUrlAdrFragment;
import com.closedevice.fastapp.ui.BkInside.fragment.SetUrlAimFragment;
import com.closedevice.fastapp.ui.BkInside.fragment.SetUrlDemandFragment;
import com.closedevice.fastapp.ui.BkInside.fragment.SetUrlGroupFragment;
import com.closedevice.fastapp.ui.BkInside.fragment.SetUrlTitleFragment;
import com.closedevice.fastapp.ui.BkInside.fragment.UploadDocFragment;
import com.closedevice.fastapp.ui.BkInside.fragment.UploadFromPcFragment;
import com.closedevice.fastapp.ui.BkInside.fragment.UploadUrlFragment;
import com.closedevice.fastapp.ui.BkInsideActivity;
import com.closedevice.fastapp.ui.common.DetailFragment;
import com.closedevice.fastapp.ui.groupclass.fragment.ClassDetailFragment;
import com.closedevice.fastapp.ui.groupclass.fragment.ClassNameFragment;
import com.closedevice.fastapp.ui.groupclass.fragment.FillClassFragment;
import com.closedevice.fastapp.ui.groupclass.fragment.InputCodeFragment;
import com.closedevice.fastapp.ui.groupclass.fragment.SetBookFragment;
import com.closedevice.fastapp.ui.setting.fragment.AboutFragment;
import com.closedevice.fastapp.ui.setting.fragment.LikeReadFragment;
import com.closedevice.fastapp.ui.setting.fragment.RecentReadFragment;
import com.closedevice.fastapp.ui.setting.fragment.SettingFragment;


public enum SimpleBackPage {
    ABOUT(0, "详情", AboutFragment.class),
    SETTING(1, "设置", SettingFragment.class),
    DETAIL(2, "详情", DetailFragment.class),
    RECENT_READ(3, "最近阅读", RecentReadFragment.class),
    LIKE_READ(4, "我的收藏", LikeReadFragment.class),
    ADD_CLASS(5, "创建班课",FillClassFragment.class),
    INPUT_CODE(6, "加入班课",InputCodeFragment.class),
    CLASS_NAME(7, "课程",ClassNameFragment.class),
    TEXTBOOK(8, "数字教材", SetBookFragment.class),
    SET_CLASS_DETAIL(9, "班课详情", ClassDetailFragment.class),
    UPLOAD_FROM_PC(10,"从PC上传", UploadFromPcFragment.class),
    UPLOAD_URL(11, "添加网页链接", UploadUrlFragment.class),
    UPLOAD_DOC(12, "资源库", UploadDocFragment.class),
    ADD_NOTIFY(13, "通知", AddNotifyFragment.class),
    SET_URL_ADDRESS(14,"设置链接", SetUrlAdrFragment.class),
    SET_URL_TITLE(15,"设置标题", SetUrlTitleFragment.class),
    SET_URL_GROUP(16,"选择分组", SetUrlGroupFragment.class),
    SET_URL_AIM(17,"知识点", SetUrlAimFragment.class),
    SET_URL_DEMAND(18, "学习要求", SetUrlDemandFragment.class);
    //SHOW_CLASS_DETAIL(10, "待设置", BkInsideActivity.class);

    private int id;
    private String title;
    private Class<?> clazz;

    SimpleBackPage(int id, String title, Class<?> clazz) {
        this.id = id;
        this.title = title;
        this.clazz = clazz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static SimpleBackPage getPage(int id) {
        for (SimpleBackPage page : values()) {
            if (page.getId() == id) {
                return page;
            }

        }
        return null;
    }
}
