package com.pig4cloud.pigx.admin.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.pig4cloud.pigx.qrcode.QrCodeGenWrapper;
import com.pig4cloud.pigx.qrcode.conf.QrCodeConfig;
import junit.framework.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yihui on 2017/4/3.
 */
public class QrCodePaddingTest {


    private BufferedImage genQrCode(String content, Integer size) throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new HashMap<>(3);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);


        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }


    @Test
    public void testGenCode() {
        String content = "使用zxing生成二维码时， 某些场景下，即便指定 `padding` 参数为0，依然有很大的白边，本篇博文主要分析产生这个的原因，以及如何修复这个问题使用zxing生成二维码时， 某些场景下，即便指定 `padding` 参数为0，依然有很大的白边，本篇博文主要分析产生这个的原因，以及如何修复这个问题";

        int size = 300;
        try {
            BufferedImage bufferedImage = this.genQrCode(content, size);
            System.out.println("---");
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * 测试二维码
     */
    @Test
    public void testGenQrCode() {
        String content = "使用zxing生成二维码时， 某些场景下，即便指定 `padding` 参数为0，依然有很大的白边，本篇博文主要分析产生这个的原因，以及如何修复这个问题使用zxing生成二维码时， 某些场景下，即便指定 `padding` 参数为0，依然有很大的白边，本篇博文主要分析产生这个的原因，以及如何修复这个问题";

        // 简单的生成
        QrCodeConfig qrCodeConfig = QrCodeGenWrapper.createQrCodeConfig()
                .setMsg(content)
                .setH(300)
                .setW(300)
                .setPadding(0)
                .build();

        try {
            BufferedImage bufferedImage = QrCodeGenWrapper.asBufferedImage(qrCodeConfig);
            System.out.println("------------");
        } catch (Exception e) {
            System.out.println("create qrcode error! e: " + e);
            Assert.assertTrue(false);
        }
    }

}
