package com.pig4cloud.pigx.admin.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	public static String getNewStringByRemoveHC(String oldString){
			Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
			Matcher m = CRLF.matcher(oldString);
			String newString = "";
			if (m.find()) {
				return newString = m.replaceAll("<br>");
			}else{
				return oldString;
			}
		}
	/**
	 * 将BufferedImage转换为InputStream
	 * @param image
	 * @return
	 */
	public static InputStream bufferedImageToInputStream(BufferedImage image){
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", os);
			InputStream input = new ByteArrayInputStream(os.toByteArray());
			return input;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
