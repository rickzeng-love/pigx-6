package com.pig4cloud.pigx.admin.qrcode;

import com.pig4cloud.pigx.qrcode.util.ImageUtil;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by yihui on 2017/4/7.
 */
public class RoundImgCreateTest {


    @Test
    public void testGen() throws IOException {
        String logo = "D:\\gen.png";
        BufferedImage bufferedImage = ImageIO.read(new File(logo));


        BufferedImage result = ImageUtil.makeRoundBorder(bufferedImage, 60, 5, null);
        System.out.println("---");
    }

}
