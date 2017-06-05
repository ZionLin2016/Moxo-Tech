package px.com.photoselectorlibrary;

/**
 * Created by admin on 2016/9/12.
 */

    public class ImageBean{
        /**
         * 文件夹的第一张图片路径
         */
        private String topImagePath;
        /**
         * 文件夹名
         */
        private String folderName;
        /**
         * 文件夹中的图片数
         */
        private int imageCounts;

        public String getTopImagePath() {
            return topImagePath;
        }
        public void setTopImagePath(String topImagePath) {
            this.topImagePath = topImagePath;
        }
        public String getFolderName() {
            return folderName;
        }
        public void setFolderName(String folderName) {
            this.folderName = folderName;
        }
        public int getImageCounts() {
            return imageCounts;
        }
        public void setImageCounts(int imageCounts) {
            this.imageCounts = imageCounts;
        }

    @Override
    public String toString() {
        return "ImageBean{" +
                "topImagePath='" + topImagePath + '\'' +
                ", folderName='" + folderName + '\'' +
                ", imageCounts=" + imageCounts +
                '}';
    }
}

