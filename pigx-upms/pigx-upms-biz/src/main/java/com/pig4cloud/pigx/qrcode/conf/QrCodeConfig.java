package com.pig4cloud.pigx.qrcode.conf;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.pig4cloud.pigx.qrcode.QrCodeGenWrapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yihui on 2017/4/2.
 */
@Getter
@Setter
@ToString
public class QrCodeConfig {
    /**
     * 塞入二维码的信息
     */
    private String msg;


    /**
     * 二维码中间的logo
     */
    private String logo;


    /**
     * 生成二维码的宽
     */
    private Integer w;


    /**
     * 生成二维码的高
     */
    private Integer h;


    /**
     * 生成二维码的颜色
     */
    private MatrixToImageConfig matrixToImageConfig;


    private Map<EncodeHintType, Object> hints;


    /**
     * 生成二维码图片的格式 png, jpg
     */
    private String picType;


    @ToString
    public static class QrCodeConfigBuilder {
        private static final MatrixToImageConfig DEFAULT_CONFIG = new MatrixToImageConfig();

        /**
         * The message to put into QrCode
         */
        private String msg;


        /**
         * qrcode center logo
         */
        private String logo;


        /**
         * qrcode image width
         */
        private Integer w;


        /**
         * qrcode image height
         */
        private Integer h;


        /**
         * qrcode bgcolor, default white
         */
        private Integer offColor;


        /**
         * qrcode msg color, default black
         */
        private Integer onColor;


        /**
         * qrcode message's code, default UTF-8
         */
        private String code;


        /**
         * 0 - 4
         */
        private Integer padding;


        /**
         * error level, default H
         */
        private ErrorCorrectionLevel errorCorrection;


        /**
         * output qrcode image type, default png
         */
        private String picType;


        public String getMsg() {
            return msg;
        }

        public QrCodeConfigBuilder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public String getLogo() {
            return logo;
        }

        public QrCodeConfigBuilder setLogo(String logo) {
            this.logo = logo;
            return this;
        }

        public Integer getW() {
            return w == null ? (h == null ? 200 : h) : w;
        }

        public QrCodeConfigBuilder setW(Integer w) {
            if (w != null && w < 0) {
                throw new IllegalArgumentException("???????????0");
            }
            this.w = w;
            return this;
        }

        public Integer getH() {
            if (w != null && w < 0) {
                throw new IllegalArgumentException("???????????0");
            }
            return h == null ? (w == null ? 200 : w) : h;
        }

        public QrCodeConfigBuilder setH(Integer h) {
            this.h = h;
            return this;
        }

        public Integer getOffColor() {
            return offColor == null ? MatrixToImageConfig.WHITE : offColor;
        }

        public QrCodeConfigBuilder setOffColor(Integer offColor) {
            this.offColor = offColor;
            return this;
        }

        public Integer getOnColor() {
            return onColor == null ? MatrixToImageConfig.BLACK : onColor;
        }

        public QrCodeConfigBuilder setOnColor(Integer onColor) {
            this.onColor = onColor;
            return this;
        }

        public String getCode() {
            return code == null ? "UTF-8" : code;
        }

        public QrCodeConfigBuilder setCode(String code) {
            this.code = code;
            return this;
        }

        public Integer getPadding() {
            if (padding == null) {
                return 1;
            }

            if (padding < 0) {
                return 0;
            }

            if (padding > 4) {
                return 4;
            }

            return padding;
        }

        public QrCodeConfigBuilder setPadding(Integer padding) {
            this.padding = padding;
            return this;
        }


        public String getPicType() {
            return picType == null ? "png" : picType;
        }

        public QrCodeConfigBuilder setPicType(String picType) {
            this.picType = picType;
            return this;
        }

        public ErrorCorrectionLevel getErrorCorrection() {
            return errorCorrection == null ? ErrorCorrectionLevel.H : errorCorrection;
        }

        public void setErrorCorrection(ErrorCorrectionLevel errorCorrection) {
            this.errorCorrection = errorCorrection;
        }

        private void validate() {
            if (msg == null || msg.length() == 0) {
                throw new IllegalArgumentException("????????????!");
            }
        }


        private QrCodeConfig create() {
            this.validate();

            QrCodeConfig qrCodeConfig = new QrCodeConfig();
            qrCodeConfig.setMsg(getMsg());
            qrCodeConfig.setH(getH());
            qrCodeConfig.setW(getW());
            qrCodeConfig.setLogo(getLogo());
            qrCodeConfig.setPicType(getPicType());

            Map<EncodeHintType, Object> hints = new HashMap<>(3);
            hints.put(EncodeHintType.ERROR_CORRECTION, this.getErrorCorrection());
            hints.put(EncodeHintType.CHARACTER_SET, this.getCode());
            hints.put(EncodeHintType.MARGIN, this.getPadding());
            qrCodeConfig.setHints(hints);


            MatrixToImageConfig config;
            if (getOnColor() == MatrixToImageConfig.BLACK
                    && getOffColor() == MatrixToImageConfig.WHITE) {
                config = DEFAULT_CONFIG;
            } else {
                config = new MatrixToImageConfig(getOnColor(), getOffColor());
            }
            qrCodeConfig.setMatrixToImageConfig(config);


            return qrCodeConfig;
        }

        /**
         * create qrcodeConfig
         *
         * @return
         */
        public QrCodeConfig build() {
            return create();
        }


        public BufferedImage asBufferedImage() throws IOException, WriterException {
            return QrCodeGenWrapper.asBufferedImage(create());
        }


        public boolean asFile(String absFileName) throws IOException, WriterException {
            return QrCodeGenWrapper.asFile(create(), absFileName);
        }
    }
}
