package com.closedevice.fastapp.router;

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
    CLASS_DETAIL(9, "班课详情", ClassDetailFragment.class);

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
