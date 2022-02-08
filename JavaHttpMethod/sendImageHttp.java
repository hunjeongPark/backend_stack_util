@RequestMapping(value = "/sendImage")
	@ResponseBody
	public JSONObject sendImage(Model model,HttpServletRequest req) throws Exception {
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest)req;
		
		MultipartFile imgFile = multi.getFile("imgFile"); //첨부 파일 참조 객체
		HttpMethod http = new HttpMethod(ConfigConstants.URL); //이미지업로드 할 URL
		
		System.out.println(imgFile.getName());
		System.out.println(imgFile.getOriginalFilename());//사용자 올린 이름 + 하드 저장되는 이름
		
		String path = req.getParameter("path");
		String imageFilename = req.getParameter("imageFilename");
		
		File transFile = new File(req.getRealPath(imgFile.getOriginalFilename()));
		
		System.out.println(req.getRealPath(imgFile.getOriginalFilename()));
		
		//파일업로드 시 필요한 파라미터 add
	  String httpReturn = http.addParam("uploadedfile", transFile)
	             .addParam("gubun", "1")
	             .submit();
	    
	  // 결과값 화면에 뿌려주기 위한 JSON 파싱
	  JSONParser parser = new JSONParser();
	  Object result = parser.parse(httpReturn);
	  JSONObject jsonResult = (JSONObject) result;
	    
		return jsonResult;
	}
