package com.entropykorea.biztalkmng.util;

import java.util.Map;
import org.springframework.stereotype.Component;
import com.entropykorea.biztalkmng.config.BizTalkMngBaseController;
import com.entropykorea.biztalkmng.service.vo.ApiLogVo;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

/*
 * Overloading Method GET(getUnirest, getDataArray), POST(postUnirest, postDataArray)
 * GET, POST Unirest 호출(Log, JSONObject, JSONArray 구별)
 * */
@Component
public class UrlUnirestConnection extends BizTalkMngBaseController {
	
	public JSONObject getUnirest(String url) {
		
		HttpResponse<JsonNode> response = Unirest
				.get(url)
				.header("Authorization", "Bearer " + tokenManager.getToken())
	            .header("Content-Type", "application/json")
	            .asJson();
		JSONObject res = response.getBody().getObject();
		
		return res;
	}
	
	public JSONObject getUnirest(String url, String apiName, String userName) throws Exception {
		
		ApiLogVo log = new ApiLogVo();
		log.setId(userName);
		log.setIn_out("IN");
		log.setFunc_nm(apiName);
		log.setRmk1(url.toString());
		mapper.addApiLog(log);
		
		HttpResponse<JsonNode> response = Unirest
				.get(url)
				.header("Authorization", "Bearer " + tokenManager.getToken())
	            .header("Content-Type", "application/json")
	            .asJson();
		JSONObject res = response.getBody().getObject();
		
		log = new ApiLogVo();
		log.setId(userName);
		log.setIn_out("OUT");
		log.setFunc_nm(apiName);
		log.setRmk1(res.toString());
		mapper.addApiLog(log);
		
		return res;
	}
	
	public JSONObject postUnirest(String url) {
		HttpResponse<JsonNode> response = Unirest
				.post(url)
				.header("Authorization", "Bearer " + tokenManager.getToken())
	            .header("Content-Type", "application/json")
	            .asJson();
		JSONObject res = response.getBody().getObject();
		return res;
	}
	
	public JSONObject postUnirest(String url, Map<String, Object> vo) {
		HttpResponse<JsonNode> response = Unirest
				.post(url)
				.header("Authorization", "Bearer " + tokenManager.getToken())
	            .header("Content-Type", "application/json")
	            .body(vo)
	            .asJson();
		JSONObject res = response.getBody().getObject();
		return res;
	}
	
	public JSONObject postUnirest(String url, String apiName, String userName) throws Exception {
		
		ApiLogVo log = new ApiLogVo();
		log.setId(userName);
		log.setIn_out("IN");
		log.setFunc_nm(apiName);
		log.setRmk1(url.toString());
		mapper.addApiLog(log);
		
		HttpResponse<JsonNode> response = Unirest
				.post(url)
				.header("Authorization", "Bearer " + tokenManager.getToken())
	            .header("Content-Type", "application/json")
	            .asJson();
		JSONObject res = response.getBody().getObject();
		
		log = new ApiLogVo();
		log.setId(userName);
		log.setIn_out("OUT");
		log.setFunc_nm(apiName);
		log.setRmk1(res.toString());
		mapper.addApiLog(log);
		
		return res;
	}
	
	public JSONArray getDataArray(JSONObject res) {
		JSONArray jsonArray = res.getJSONArray("data");
		return jsonArray; 
	}
	
	public JSONArray getDataArray(String url, String apiName, String userName) throws Exception {
		JSONObject res = urlUnirestConnection.getUnirest(url);
		JSONArray jsonArray = new JSONArray();
		
		ApiLogVo log = new ApiLogVo();
		log.setId(userName);
		log.setIn_out("IN");
		log.setFunc_nm(apiName);
		log.setRmk1(url.toString());
		mapper.addApiLog(log);
		
		if (res.get("code").equals("API_200")) {
			jsonArray = res.getJSONArray("data");
			
			log = new ApiLogVo();
			log.setId(userName);
			log.setIn_out("OUT");
			log.setFunc_nm(apiName);
			log.setRmk1(jsonArray.toString());
			mapper.addApiLog(log);
		} else {
			log = new ApiLogVo();
			log.setId(userName);
			log.setIn_out("OUT");
			log.setFunc_nm(apiName);
			log.setRmk1("code : " + res.get("code").toString() + ", message : " + res.get("message").toString());
			mapper.addApiLog(log);
		}
		
		return jsonArray;
	}
	
	public JSONArray postDataArray(String url, String apiName, String userName) throws Exception {
		JSONObject res = urlUnirestConnection.postUnirest(url);
		JSONArray jsonArray = new JSONArray();
		
		ApiLogVo log = new ApiLogVo();
		log.setId(userName);
		log.setIn_out("IN");
		log.setFunc_nm(apiName);
		log.setRmk1(url.toString());
		mapper.addApiLog(log);
		
		if (res.get("code").equals("API_200")) {
			jsonArray = res.getJSONArray("data");
			
			log = new ApiLogVo();
			log.setId(userName);
			log.setIn_out("OUT");
			log.setFunc_nm(apiName);
			log.setRmk1(jsonArray.toString());
			mapper.addApiLog(log);
		} else {
			log = new ApiLogVo();
			log.setId(userName);
			log.setIn_out("OUT");
			log.setFunc_nm(apiName);
			log.setRmk1("code : " + res.get("code").toString() + ", message : " + res.get("message").toString());
			mapper.addApiLog(log);
		}
		
		return jsonArray; 
	}
	
	public JSONObject getNHNUnirest(String url) {
		
		HttpResponse<JsonNode> response = Unirest
				.get(url)
				.header("X-Auth-Token", nhnTokenManager.getToken())
	            .asJson();
		JSONObject res = response.getBody().getObject();
		
		return res;
	}
	
	public JSONObject postNHNUnirest(String url) {
		HttpResponse<JsonNode> response = Unirest
				.post(url)
				.header("X-Auth-Token", nhnTokenManager.getToken())
	            .asJson();
		JSONObject res = response.getBody().getObject();
		
		return res;
	}

	public Object deleteUnirest(String url, String apiName, String userName) throws Exception {
		ApiLogVo log = new ApiLogVo();
		log.setId(userName);
		log.setIn_out("IN");
		log.setFunc_nm(apiName);
		log.setRmk1(url.toString());
		mapper.addApiLog(log);
		
		HttpResponse<JsonNode> response = Unirest
				.delete(url)
				.header("Authorization", "Bearer " + tokenManager.getToken())
	            .header("Content-Type", "application/json")
	            .asJson();
		JSONObject res = response.getBody().getObject();
		
		log = new ApiLogVo();
		log.setId(userName);
		log.setIn_out("OUT");
		log.setFunc_nm(apiName);
		log.setRmk1(res.toString());
		mapper.addApiLog(log);
		
		return res;
	}
	
}








