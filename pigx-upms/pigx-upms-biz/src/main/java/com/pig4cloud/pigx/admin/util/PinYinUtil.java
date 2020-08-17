package com.pig4cloud.pigx.admin.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.text.Collator;
import java.util.*;

public class PinYinUtil {
	/**
	 * 获取汉字串拼音，英文字符不变
	 * @param chinese 汉字串
	 * @return 汉语拼音
	 */
	public static String getFullSpell(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString();
	}
	/*
	*按照字母分类排序
	 */
	public static Map<String,Object> px(List<Map> list){
		Comparator com = Collator.getInstance(Locale.CHINA);
		//按字母排序
		//Collections.sort(list,com);
		Collections.sort(list, (p1, p2) -> {
			return com.compare((String)p1.get("name"), (String)p2.get("name"));
		});
		//输出26个字母
		Map<String,Object> map = new TreeMap<String,Object>();
		for(int i=1;i<=26;i++){
			boolean b = false;
			String word = String.valueOf((char)(96+i)).toUpperCase();
			//循环找出 首字母一样的数据
			List<Map> letter = new ArrayList<Map>();
			for(Map etmap:list){
				String name = (String)etmap.get("name");
				String zm = getFullSpell(name).substring(0,1);
				zm = zm.toUpperCase();
				if(word.equals(zm)){
					b = true;
					letter.add(etmap);
				}
			}
			if(b){
				map.put(word,letter);
			}

		}
		return map;
	}
}
