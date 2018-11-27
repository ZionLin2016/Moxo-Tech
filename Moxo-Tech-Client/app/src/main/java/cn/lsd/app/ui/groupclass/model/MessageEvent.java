package cn.lsd.app.ui.groupclass.model;

public class MessageEvent {
    private int type;
    private String data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "type=" + type +
                ", data='" + data + '\'' +
                '}';
    }
}