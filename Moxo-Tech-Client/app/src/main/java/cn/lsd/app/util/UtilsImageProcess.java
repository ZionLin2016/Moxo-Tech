package cn.lsd.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilsImageProcess {

    /**
     * 将得到的一个Bitmap保存到SD卡上，得到一个URI地址
     */
    public static Uri saveBitmap(Bitmap bm) {
        //在SD卡上创建目录
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/org.chenlijian.test");
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }

        File img = new File(tmpDir.getAbsolutePath() + "test.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将得到的一个Bitmap保存到SD卡上，得到一个绝对路径
     */
    public static String getPath(Bitmap bm) {
        //在SD卡上创建目录
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/org.lsd.test");
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }

        File img = new File(tmpDir.getAbsolutePath() + "/test.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
            return img.getCanonicalPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将图库中选取的图片的URI转化为URI
     */
    public static Uri convertUri(Uri uri, Context context) {
        InputStream is;
        try {
            is = context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在拍摄照片之前生成一个文件路径Uri，保证拍出来的照片没有被压缩太小,用日期作为文件名，确保唯一性
     */
    public static String getSavePath(){
        String saveDir = Environment.getExternalStorageDirectory() + "/org.chenlijian.test";
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String fileName = saveDir + "/" + formatter.format(date) + ".png";

        return fileName;
    }

    /**
     *  计算图片的缩放值
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     *  根据路径获得图片并压缩，返回bitmap用于显示
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        // 设置为true，画质更好一点，加载时间略长
        options.inPreferQualityOverSpeed = true;

        return BitmapFactory.decodeFile(filePath, options);
    }
}