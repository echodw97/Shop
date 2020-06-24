package com.qzh.shop.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/6/17 16:51
 * @Version 1.0
 */
public class Numbering {


    public String getNumbering() {
        String numbering = (System.currentTimeMillis() + new Random().nextInt(99999)) + "";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] =  md.digest(numbering.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
