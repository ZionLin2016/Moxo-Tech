package cn.lsd.app.util;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {
    private final static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};


    public static boolean isImgUrl(String url) {
        if (url == null || url.trim().length() == 0)
            return false;
        return Pattern
                .compile(".*?(gif|jpeg|png|jpg|bmp)").matcher(url).matches();
    }


    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return Pattern
                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(email).matches();
    }


    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }


    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }


    public static String toString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line);
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    public static boolean isURL(String str) {
        if (str == null || str.trim().length() == 0)
            return false;
        return Pattern
                .compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)").matcher(str).matches();
    }

    public static boolean isSameChar(String pwd) {
        char[] charArray = pwd.toCharArray();
        for (int i = 0; i < charArray.length - 1; i++) {
            if (charArray[i] != charArray[i + 1]) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    public static boolean checkUsername(String username) {
        Pattern p = Pattern.compile("[A-Z,a-z,0-9,-]*");
        Matcher m = p.matcher(username);
        return m.matches();
    }

    public static boolean checkPassword(String pwd) {
        Pattern p = Pattern.compile("[da-zA-Z]*d+[a-zA-Z]+[da-zA-Z]*");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    public static boolean isSafePassword(String str) {
        String regex = "^(?=.*?[a-zA-Z])(?=.*?[0-6])[!\"#$%&'()*+,\\-./:;<=>?@\\[\\\\\\]^_`{|}~A-Za-z0-9]{6,12}$";
        return str.matches(regex);
    }


    public static boolean isPhoneNumber(String phone) {
        Pattern p = Pattern.compile("1[3|5|7|8|][0-9]{9}");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static boolean isQQNumber(String value) {
        return value.matches("[1-9][0-9]{4,13}");
    }


    public static String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    public static String hexToString(byte[] value) {
        char str[] = new char[value.length * 2];
        int k = 0;
        for (int i = 0; i < value.length; i++) {
            byte byte0 = value[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str).toLowerCase();
    }

    private static String byteTohex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }


    private static byte[] hexTobyte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException();
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }


    private static String currentString = "";
    /**获取刚传入处理后的string
     * @must 上个影响currentString的方法 和 这个方法都应该在同一线程中，否则返回值可能不对
     * @return
     */
    public static String getCurrentString() {
        return currentString == null ? "" : currentString;
    }


    //获取string,为null则返回""
    public static String getString(TextView tv) {
        if (tv == null || tv.getText() == null) {
            return "";
        }
        return getString(tv.getText().toString());
    }
    //获取string,为null则返回""
    public static String getString(Object object) {
        return object == null ? "" : getString(String.valueOf(object));
    }
    //获取string,为null则返回""
    public static String getString(CharSequence cs) {
        return cs == null ? "" : getString(cs.toString());
    }
    //获取string,为null则返回""
    public static String getString(String s) {
        return s == null ? "" : s;
    }



    //获取string,为null则返回""
    public static String getTrimedString(TextView tv) {
        return getTrimedString(getString(tv));
    }
    //获取string,为null则返回""
    public static String getTrimedString(Object object) {
        return getTrimedString(getString(object));
    }

    public static String getTrimedString(CharSequence cs) {
        return getTrimedString(getString(cs));
    }

    public static String getTrimedString(String s) {
        return getString(s).trim();
    }


    public static String getNoBlankString(TextView tv) {
        return getNoBlankString(getString(tv));
    }

    public static String getNoBlankString(Object object) {
        return getNoBlankString(getString(object));
    }

    public static String getNoBlankString(CharSequence cs) {
        return getNoBlankString(getString(cs));
    }

    public static String getNoBlankString(String s) {
        return getString(s).replaceAll(" ", "");
    }

    public static boolean isNotEmpty(TextView tv, boolean trim) {
        return isNotEmpty(getString(tv), trim);
    }
    /**判断字符是否非空
     * @param object
     * @param trim
     * @return
     */
    public static boolean isNotEmpty(Object object, boolean trim) {
        return isNotEmpty(getString(object), trim);
    }
    /**判断字符是否非空
     * @param cs
     * @param trim
     * @return
     */
    public static boolean isNotEmpty(CharSequence cs, boolean trim) {
        return isNotEmpty(getString(cs), trim);
    }
    /**判断字符是否非空
     * @param s
     * @param trim
     * @return
     */
    public static boolean isNotEmpty(String s, boolean trim) {
        //		Log.i(TAG, "getTrimedString   s = " + s);
        if (s == null) {
            return false;
        }
        if (trim) {
            s = s.trim();
        }
        if (s.length() <= 0) {
            return false;
        }

        currentString = s;

        return true;
    }

    public static String getCorrectPhone(TextView tv) {
        return getCorrectPhone(getString(tv));
    }
    /**获取去掉所有 空格 、"-" 、"+86" 后的phone
     * @param phone
     * @return
     */
    public static String getCorrectPhone(String phone) {
        if (isNotEmpty(phone, true) == false) {
            return "";
        }

        phone = getNoBlankString(phone);
        phone = phone.replaceAll("-", "");
        if (phone.startsWith("+86")) {
            phone = phone.substring(3);
        }
        return phone;
    }
}
