package com.pig4cloud.pigx.qrcode.util;

import com.google.zxing.common.BitMatrix;
import com.pig4cloud.pigx.qrcode.conf.QrCodeConfig;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by yihui on 2017/4/2.
 */
public class MatrixToImageUtil {

    /**
     * 根据二维码配置 & 二维码矩阵生成二维码图片
     *
     * @param qrCodeConfig
     * @param bitMatrix
     * @return
     * @throws IOException
     */
    public static BufferedImage toBufferedImage(QrCodeConfig qrCodeConfig, BitMatrix bitMatrix) throws IOException {
        int qrCodeWidth = bitMatrix.getWidth();
        int qrCodeHeight = bitMatrix.getHeight();
        BufferedImage qrCode = new BufferedImage(qrCodeWidth, qrCodeHeight, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < qrCodeWidth; x++) {
            for (int y = 0; y < qrCodeHeight; y++) {
                qrCode.setRGB(x, y,
                        bitMatrix.get(x, y) ?
                                qrCodeConfig.getMatrixToImageConfig().getPixelOnColor() :
                                qrCodeConfig.getMatrixToImageConfig().getPixelOffColor());
            }
        }

        // 插入logo
        if (!(qrCodeConfig.getLogo() == null || "".equals(qrCodeConfig.getLogo()))) {
            ImageUtil.insertLogo(qrCode, qrCodeConfig.getLogo());
        }

        // 若二维码的实际宽高和预期的宽高不一致, 则缩放
        int realQrCodeWidth = qrCodeConfig.getW();
        int realQrCodeHeight = qrCodeConfig.getH();
        if (qrCodeWidth != realQrCodeWidth || qrCodeHeight != realQrCodeHeight) {
            BufferedImage tmp = new BufferedImage(realQrCodeWidth, realQrCodeHeight, BufferedImage.TYPE_INT_RGB);
            tmp.getGraphics().drawImage(
                    qrCode.getScaledInstance(realQrCodeWidth, realQrCodeHeight,
                            Image.SCALE_SMOOTH), 0, 0, null);
            qrCode = tmp;
        }

        return qrCode;
    }



}
