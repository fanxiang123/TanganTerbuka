package com.swd.tanganterbuka.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.swd.tanganterbuka.activity.RegisterActivity;
import com.swd.tanganterbuka.modle.GuId;
import com.swd.tanganterbuka.modle.UserModle;

import org.json.JSONException;


import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.branch.referral.BuildConfig;


public class ResUtils {

    private static final  String SKEY  ="a2fee2b81aade715a1a82dfcb244ea00";
    private static final  String SIV  ="21128722af6a76f5";

    private static UserModle userModle;


    public static UserModle getUser(Context context){

        if (StorePreferences.getInstance(context).getUserInfo().isEmpty())
            return null;

        if (userModle == null){
           String userInfo = StorePreferences.getInstance(context).getUserInfo();
            Gson gs = new Gson();
            UserModle userModle1 = gs.fromJson(userInfo, UserModle.class);
            userModle =userModle1;
        }
        return userModle;
    }

    public static void logout(Context context){
        StorePreferences.getInstance(context).setUserInfo(null);
        userModle =null;
    }


    public static String createRequestData(Map<String, Object> map,Context context) {
        if (null != map) {
            StorePreferences storePreferences = StorePreferences.getInstance(context);
            Map<String, Object> driverInfo = getSimpleDriverInfo(storePreferences.getGaid(),context);

            String timestamp = getTimeMillis();
            String guid = storePreferences.getGuid();
            String ref = storePreferences.getRef();
            String userId = "";
            if (getUser(context)!=null)
                userId = getUser(context).getUser_id();

            if(!TextUtils.isEmpty(storePreferences.getUserInfo())){
                try{
                    org.json.JSONObject jsonObject = new org.json.JSONObject(storePreferences.getUserInfo());
                    userId=jsonObject.optString("user_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

//            map.put("appPackage", BuildConfig.APPLICATION_ID);
            map.put("appPackage","id.tangan.cashpro.terbuka.com");
            map.put("userId", TextUtils.isEmpty(userId) ? "" : userId);
            map.put("ref", TextUtils.isEmpty(ref) ? "" : ref); // 渠道添加
            map.put("appType", "android");
            map.put("position", "0,0");
            map.put("version", BuildConfig.VERSION_NAME);
            map.put("guid", TextUtils.isEmpty(guid) ? "" : guid);
            map.put("appVersion", getAppVersionCode(context));
            map.put("channel", "App");
            map.put("deviceInfo", null == driverInfo ? "" : driverInfo);
            String sign = getRequestSign(map, timestamp);
            map.put("sign", sign);
            map.put("timestamp", timestamp);
            return getMapToString(map);
        }
        return "";
    }

    private static String getMapToString(Map<String, Object> map) {
        try {
            String jsonStr = JSONObject.toJSONString(map);
            return jsonStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getRequestSign(Map<String, Object> paraMap, String time) {
        try {
            Map<String, Object> data = sortMapByKey(paraMap);
            String jsonData = JSONObject.toJSONString(data);
            String result = SKEY + "*|*" + jsonData + "@!@" + time;
            String sign = encrypt(encrypt(result));
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    private static Map<String, Object> getSimpleDriverInfo(String gaid,Context context) {
        Map<String, Object> mapClient = new HashMap<>();
         try {
            mapClient.put("andId", getAndroidID(context));
            mapClient.put("gaid", gaid);
            mapClient.put("imei", getDriverIMIE(context));
            mapClient.put("mac", "");
            mapClient.put("sn", getSerialNumber());
            mapClient.put("model", getModel());
            mapClient.put("brand", getBrand());
            mapClient.put("release", getDriverOsVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapClient;
    }



    private static String getAndroidID(Context context) {
        String androidID = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return androidID;
    }

    private static String getDriverIMIE(Context context) {
        String result = "";
//        try {
//            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if (null != tm) {
//                result = "";
//            }
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
        return result;
    }



    private static String getSerialNumber() {
        String result = null;
        try {
            result = android.os.Build.SERIAL;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getModel() {
        try {
            String model = Build.MODEL;
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getBrand() {
        try {
            String brand = Build.BRAND;
            return brand;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getDriverOsVersion() {
        try {
            String device = Build.VERSION.RELEASE;
            return device;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }



    private static int getAppVersionCode(Context context) {
        if (null == context) {
            return 0;
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static String getTimeMillis() {
        long time = System.currentTimeMillis();
        String str = String.valueOf(time);
        return str;
    }

    private static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Object> sortMap = new TreeMap<String, Object>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }



    private final static String encrypt(String plaintext) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = plaintext.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }


    public static String encryptedStr(String str) {
        try {
            String sKey = SKEY;
            String sIv = SIV;
            //java
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//aes-cbc-pkcs5(pkcs5与pkcs7通用)
            IvParameterSpec iv = new IvParameterSpec(sIv.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(str.getBytes("UTF-8"));
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptStr(String str) {
        try {
            String sKey = SKEY;
            String sIv = SIV;
            //java
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//aes-cbc-pkcs5(pkcs5与pkcs7通用)
            IvParameterSpec iv = new IvParameterSpec(sIv.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] decrypt = cipher.doFinal(Base64.decode(str, Base64.NO_WRAP));
            return new String(decrypt);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
