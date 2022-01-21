//CallBack Server Http Status Check (200, 500...) 
@RequestMapping(value = "/statusCheck", method = RequestMethod.GET)
public ResponseEntity<String> statusCheck() throws Exception {
  return new ResponseEntity<String>(HttpStatus.OK);
}

//Call Status Check Ex
@RequestMapping(value = "/middleServerStatusCheck", method = RequestMethod.GET)
public @ResponseBody
String middleServerStatusCheck(HttpServletRequest req) throws Exception {
  //Url check Call that statusCheck API port/RequestMapping/statusCheck ....
  String url = "localhost:[port]/[RequestMapping]/statusCheck"; 
  String status = "";
  try {
    HttpResponse<JsonNode> response = Unirest
        .get(url)
        .header("Content-Type", "application/json")
        .asJson();
    status = String.valueOf(response.getStatus());
  } catch (Exception e) {
    status = "500";
  }

  return status;
}

