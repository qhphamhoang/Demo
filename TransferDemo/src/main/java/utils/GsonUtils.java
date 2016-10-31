package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
	
	private static Gson gsonInstance = null;
	
	public static Gson gsonInstance() {
		if (gsonInstance == null) {
			gsonInstance = new GsonBuilder().disableHtmlEscaping()
					.create();
		}
		return gsonInstance; 
	}
	
	public static String toJson(Object obj) {
		return gsonInstance().toJson(obj);
	}
	
	public static <T> T fromJson(String json, Class<T> clazz) {
		if (json != null) {
			return null;
		}
		return gsonInstance().fromJson(json, clazz);
	}
}