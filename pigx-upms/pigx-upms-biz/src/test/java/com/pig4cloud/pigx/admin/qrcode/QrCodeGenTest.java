package com.pig4cloud.pigx.admin.qrcode;


import com.pig4cloud.pigx.qrcode.QrCodeGenWrapper;
import com.pig4cloud.pigx.qrcode.conf.QrCodeConfig;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by yihui on 2017/4/2.
 */
public class QrCodeGenTest {

    /**
     * 测试二维码
     */
    @Test
    public void testGenQrCode() {
        String msg = "https://my.oschina.net/u/566591/blog/872728";

        // 简单的生成
        QrCodeConfig qrCodeConfig = QrCodeGenWrapper.createQrCodeConfig()
                .setMsg(msg)
                .build();

        try {
            boolean ans = QrCodeGenWrapper.asFile(qrCodeConfig, "D:\\gen.png");
            System.out.println(ans);
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("create qrcode error! e: " + e);
            Assert.assertTrue(false);
        }


        //生成红色的二维码 300x300, 无边框
        try {
            boolean ans = QrCodeGenWrapper.createQrCodeConfig()
                    .setMsg(msg)
                    .setW(300)
                    .setOnColor(0xffff0000)
                    .setOffColor(0xffffffff)
                    .setPadding(0)
                    .asFile("D:\\gen_300x300.png");
            System.out.println(ans);
        } catch (Exception e) {
            System.out.println("create qrcode error! e: " + e);
            Assert.assertTrue(false);
        }


        // 生成带logo的二维码
        try {
            String logo = "https://static.oschina.net/uploads/user/283/566591_100.jpeg";
            boolean ans = QrCodeGenWrapper.createQrCodeConfig()
                    .setMsg(msg)
                    .setW(300)
                    .setOnColor(0xffff0000)
                    .setOffColor(0xffffffff)
                    .setPadding(0)
                    .setLogo(logo)
                    .asFile("D:\\gen_300x300_logo.png");
            System.out.println(ans);
        } catch (Exception e) {
            System.out.println("create qrcode error! e: " + e);
            Assert.assertTrue(false);
        }


        // 根据本地文件生成待logo的二维码
        try {
            String logo = "d:/logo.jpg";
            boolean ans = QrCodeGenWrapper.createQrCodeConfig()
                    .setMsg(msg)
                    .setW(300)
                    .setOnColor(0xffff0000)
                    .setOffColor(0xffffffff)
                    .setPadding(0)
                    .setLogo(logo)
                    .asFile("d:/gen_300x300_logo_v2.png");
            System.out.println(ans);
        } catch (Exception e) {
            System.out.println("create qrcode error! e: " + e);
            Assert.assertTrue(false);
        }
    }

}
