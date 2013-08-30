package com.refactech.driibo.vendor;

import android.text.TextUtils;

import com.refactech.driibo.type.dribble.User;

/**
 * Created by Issac on 7/18/13.
 */
public class DribbbleApi {
	private static final String BASE_URL = "http://m2.qiushibaike.com/article/list/suggest?";

	public static final String SHOTS_LIST = BASE_URL + "page=%1$d";

	public static final String USER_IMAG_URL = "http://pic.moumentei.com/system/avtnew/%1$s/%2$s/thumb/";

	public static final String CONTENT_IMAG_URL = "http://pic.moumentei.com/system/pictures/%1$s/%2$s/small/";

	public static String getUserImgURL(User user) {
		if (TextUtils.isEmpty(user.icon))
			return null;
		return String.format(USER_IMAG_URL, new Object[] {
				user.id.trim().substring(0, 4), user.id })
				+ user.icon;
	}

	public static String getContentImgURL(String img) {
		if (TextUtils.isEmpty(img))
			return null;
		int index = img.lastIndexOf(".");
		String number = img;
		if (index != -1) {
			number = img.substring(index - 8, index);
		}

		return String.format(CONTENT_IMAG_URL,
				new Object[] { number.substring(0, 4), number })
				+ img;
	}
}
