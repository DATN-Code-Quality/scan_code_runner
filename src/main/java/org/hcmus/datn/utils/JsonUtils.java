package org.hcmus.datn.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
public class JsonUtils {
	private static final Gson gson = new Gson();

	public static JsonObject toObject(String json) {
		return gson.fromJson(json, JsonObject.class);
	}

	public static List<JsonObject> toList(String json) {
		try {
			Type listType = new TypeToken<ArrayList<JsonObject>>() {
			}.getType();
			List<JsonObject> items = gson.fromJson(json, listType);
			return items;
		} catch (Exception ex) {
			new Error("[Error parse json] - json: " + json, ex);
			return null;
		}
	}

	public static List<JsonObject> toList(JsonArray json){
		Type listType = new TypeToken<ArrayList<JsonObject>>() {
		}.getType();
		return gson.fromJson(json, listType);
	}
}
