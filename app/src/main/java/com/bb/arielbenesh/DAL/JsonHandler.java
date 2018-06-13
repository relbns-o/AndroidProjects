package com.bb.arielbenesh.DAL;

import com.bb.arielbenesh.DL.Item;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * creating List<Item> of specific elements from json string
 * In cases where requirements object is missing return -1
 */
public class JsonHandler {
	public static List<Item> getCustomListFromJson(String json) throws JSONException {
		List<Item> items = new ArrayList<>();
		JSONObject jObj = new JSONObject(json);
		JSONArray jArr = jObj.getJSONArray("hits");
		for (int i = 0; i < jArr.length(); i++) {
			JSONObject jb = jArr.getJSONObject(i);
			JSONObject jObj2 = jb.getJSONObject("_source");
			String fullName = (jObj2.getJSONObject("info")).getString("fullName");
			int Level;
			try {
				Level = (jObj2.getJSONObject("requirements")).getInt("Level");
			} catch (JSONException e) {
				Level = -1;
			}
			boolean identified = (jObj2.getJSONObject("attributes")).getBoolean("identified");
			items.add( new Item(fullName,Level,identified));
		}
		return items;
	}
}
