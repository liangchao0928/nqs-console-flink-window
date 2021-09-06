package com.eystar.common.util;

import com.xxl.conf.core.XxlConfClient;
import com.xxl.conf.core.factory.XxlConfBaseFactory;
import org.springframework.stereotype.Component;

@Component
public class XxlConfBean {

    private  String address;
    private  String env;
    private  String token;
    private  String mirrorfile;

    public  String getAddress() {
        return address;
    }
    public  void setAddress(String address) {
        this.address = address;
    }
    public  String getEnv() {
        return env;
    }
    public  void setEnv(String env) {
        this.env = env;
    }
    public  String getToken() {
        return token;
    }
    public  void setToken(String token) {
        this.token = token;
    }
    public  String getMirrorfile() {
        return mirrorfile;
    }
    public  void setMirrorfile(String mirrorfile) {
        this.mirrorfile = mirrorfile;
    }

    /**
     * 初始化XxlConfBaseFactory类,允许调用XxlConfClient
     */
    public  void  init(){
        XxlConfBaseFactory.init(address,env,token,mirrorfile);
    }


    public static String getXxlValueByString(String key){
        return XxlConfClient.get(key);
    }

    public static long getXxlValueByLong(String key){
        return XxlConfClient.getLong(key);
    }

    public static double getXxlValueByDouble(String key){
        return XxlConfClient.getDouble(key);
    }
}
