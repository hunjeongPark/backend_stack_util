/*
 * Copyright (c) 2021, Mobile Entropy. All rights reserved.
 */
package com.entropykorea.biztalkmng.util;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * <pre>
 * 형변환 시 추가적인 처리가 필요한 부분들에 대한 유틸.
 * </pre>
 * 
 * @author seyoungHwang
 * @since 1.0-SNAPSHOT
 */
@Component
public class CastUtils {
	public String getSHA512encryptedPassword(String rawPassword) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-512");

		md.update(rawPassword.getBytes());
		byte[] msgb = md.digest();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < msgb.length; i++) {
			String tmp = Integer.toHexString(msgb[i] & 0xFF);
			while (tmp.length() < 2)
				tmp = "0" + tmp;
			sb.append(tmp.substring(tmp.length() - 2));
		}
		return sb.toString();
	}

	public String getNotNullFormString(Map<String, String> params) throws Exception {
		String res = toUrlFormStringFromMap(params);
		if (res == null || res.length() == 0)
			return "";
		return "?"+res;
	}
	
	
	/**
	 * Map을 x-www-form-urlencoded 형식에 맞춘 URL String으로 변경
	 * 
	 * @author seyoungHwang
	 * @param params Map
	 * @return a=123&b=23&... 형식의 URL String
	 */
	public String toUrlFormStringFromMap(Map<String, String> params) {
		return params.keySet().stream().map(key -> {
			String value = params.getOrDefault(key, "");
			if (value.length() == 0)
				return null;
			String res = String.format("%s=%s", key, value);
			return res;
		}).filter(res -> res != null).collect(Collectors.joining("&"));
	}
	
	/**
	 * Map을 MultiValueMap으로 변경.
	 * 이 때 null 또는 empty인 항목은 제외.
	 * @author seyoungHwang
	 * @param params MultiValueMap으로 변경할 Map
	 * @return null 또는 empty값이 없는 MultiValueMap
	 */
	public MultiValueMap<String, String> toNotNullMultiValueMap(Map<String, String> params) {
		MultiValueMap<String, String> res = new LinkedMultiValueMap<>();
		for (String key : params.keySet()) {
			String tmp = params.getOrDefault(key, null);
			if (tmp == null)
				continue;
			res.add(key, tmp);
		}
		return res;
	}
	
	/**
	 * MultiValueMap을 null이나 empty인 항목이 없는 MultiValueMap으로 변경.
	 * @author seyoungHwang
	 * @param params MultiValueMap
	 * @return null 또는 empty값이 없는 MultiValueMap
	 */
	public MultiValueMap<String, String> toNotNullMultiValueMap(MultiValueMap<String, String> params) {
		MultiValueMap<String, String> res = new LinkedMultiValueMap<>();
		for (String key : params.keySet()) {
			List<String> tmp = params.get(key);
			if (tmp == null)
				continue;
			res.addAll(key, tmp);
		}
		return res;
	}
}
