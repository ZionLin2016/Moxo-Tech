package com.closedevice.fastapp.model.uploadFile;

import java.util.List;

/**
 * Created by admin on 2017/4/25.
 */

public class UploadList {

    public UploadList(List<UploadMsg> content) {
        this.content = content;
    }

    private List<UploadMsg> content;

        public List<UploadMsg> getContent() {
            return content;
        }

        public void setContent(List<UploadMsg> content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "UploadList [content=" + content + "]";
        }
}
