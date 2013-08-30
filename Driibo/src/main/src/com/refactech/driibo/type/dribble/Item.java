package com.refactech.driibo.type.dribble;

import java.util.HashMap;

import android.database.Cursor;
import com.google.gson.Gson;
import com.refactech.driibo.dao.ShotsDataHelper;


public class Item extends BaseType {
	private static final HashMap<Long, Item> CACHE = new HashMap<Long, Item>();

	public String image;
	public long published_at;
	public String tag;
	public User user;
	public String id;
	public Votes votes;
	public long created_at;
	public String content;
	public String state;
	public long comments_count;
	public boolean allow_comment;

	private static void addToCache(Item shot) {
		CACHE.put(Long.parseLong(shot.id), shot);
	}

	private static Item getFromCache(long id) {
		return CACHE.get(id);
	}

	public static Item fromJson(String json) {
		return new Gson().fromJson(json, Item.class);
	}

	public static Item fromCursor(Cursor cursor) {
		long id = cursor.getLong(cursor
				.getColumnIndex(ShotsDataHelper.ShotsDBInfo._ID));
		Item shot = getFromCache(id);
		if (shot != null) {
			return shot;
		}
		shot = new Gson().fromJson(cursor.getString(cursor
				.getColumnIndex(ShotsDataHelper.ShotsDBInfo.JSON)), Item.class);
		addToCache(shot);
		return shot;
	}

}
