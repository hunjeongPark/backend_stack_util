//Check this DB Data for in list
private HashSet<String> getAcceptedSenderNoList() {
  HashSet<String> res = new HashSet<String>();

  List<Map<String, String>> list = dao.selectSenderNo(); //DB Data
  for (Map<String, String> row : list) {
    String curSenderNo = row.getOrDefault("SENDER_NO", ""); //list in Data ? Default "" >> 데이터가 없으면 빈값을 
    if (curSenderNo.isEmpty()) 
      continue;

    res.add(curSenderNo);
  }

  return res;
}


public String postMultiSmsWithChkSenderNo(Map<String, Object> params) {
		HashSet<String> acceptedSenderNo = getAcceptedSenderNoList();
		
		List<Map<String, Object>> inputList = (List<Map<String, Object>>) params.get("send_messages");

		for (Map<String, Object> row : inputList) {
			String smsType = String.valueOf(row.get("sms_type"));
			String senderNo = String.valueOf(row.get("sender_no"));
			if(!smsType.equals("")) {
				if (!acceptedSenderNo.contains(senderNo)) {
					row.put("sender_no", "");
				}
			}
		}
		Gson gson = new Gson();
		String req = gson.toJson(params);
