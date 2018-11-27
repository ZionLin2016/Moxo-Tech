package cn.lsd.app.ui.groupclass.model;

/**
 * Created by LSD on 2017/4/22.
 */

public enum EnumEventType {
    CLASS_NAME(0), COURSE_NAME(1), BK_TYPE(2), BOOK_NAME(3), CLASS_DETAIL(4), CLASS_COVER(5),
    URL_ADR(6), URL_AIM(7), URL_DEMAND(8), URL_GROUP(9), URL_TITLE(10), NOTIFY_CONTENT(11);

    private EnumEventType(int type) {
        this.type = type;
    }

    private int type;

    public int getType() {
        return type;
    }
}
