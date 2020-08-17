package com.pig4cloud.pigx.admin.qrcode;

import com.pig4cloud.pigx.qrcode.QrCodeReaderWrapper;
import org.junit.Test;

/**
 * Created by yihui on 2017/4/7.
 */
public class QrCodeDeTest {

    @Test
    public void testDecode() throws Exception {
        long start = System.nanoTime();
        String path = "qrcode/gen_300x300_logo.png";
        String ans = QrCodeReaderWrapper.decode(path);
        long end = System.nanoTime();
        System.out.println("response : " + ans + " cost: " + (end - start));


        start = System.nanoTime();
        path  = "http://mmbiz.qpic.cn/mmbiz_jpg/dYV9cAW65kZvIwQ4hOkDaicCvlnI5LfTY8LCIQt80P1V5cEL8hr6gsDjxSx9nC49nC2FbRXtZvJksCm9tuciawzA/0?wx_fmt=jpeg";
        ans = QrCodeReaderWrapper.decode(path);
        end = System.nanoTime();
        System.out.println("response : " + ans + " cost: " + (end - start));
    }
}
