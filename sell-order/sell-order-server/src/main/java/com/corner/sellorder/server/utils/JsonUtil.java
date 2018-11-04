package com.corner.sellorder.server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 转换为json字符串
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T toObject(String json, TypeReference<T> typeReference){
		try {
			return (T)objectMapper.readValue(json,typeReference);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
