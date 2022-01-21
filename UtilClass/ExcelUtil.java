package com.entropykorea.biztalkmng.util;

import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Component
public class ExcelUtil {

	private InputStream fis;

//	private final String phone_no = String.format("${%s}", "전화번호");
	private final String phone_no = "고객휴대폰번호";
	private final String name = "고객명";
	private final String birthday = "생년월일";

	public void setExcelFile(InputStream fis) {
		this.fis = fis;
	}

	private HashMap<String, Object> sendList = docSendMaker();

	private HashMap<String, Object> docSendMaker() {
		HashMap<String, Object> colList = new HashMap<>();
		colList.put("expires_in","86400");
		colList.put("call_center_no","02-717-5196");
		colList.put("title","메시지");
		colList.put("message","메시지 내용 테스트");
		colList.put("markdown_use","N");
		colList.put("allow_simple_registration","N");
		colList.put("verify_auth_name","N");
		colList.put("publish_certified_electronic_doc","N");
		colList.put("payload","");
		colList.put("sub_org_id","");
		colList.put("call_center_label","");
		colList.put("create_dt","");
		colList.put("create_user","admin");

		return colList;
	}

	@SuppressWarnings("resource")
	public List<XSSFSheet> getContainExcelSheet() throws Exception{

		XSSFWorkbook workBook = new XSSFWorkbook(this.fis);
		FormulaEvaluator evaluator = workBook.getCreationHelper().createFormulaEvaluator();
		List<XSSFSheet> sheetList = new ArrayList<XSSFSheet>();
		int sheetSize = workBook.getNumberOfSheets();

		if(sheetSize > 0) {
			for(int index = 0; index < sheetSize; index++) {
				sheetList.add(workBook.getSheetAt(index));
			}
		}

		return sheetList;
	}

	public String[] getHeaderNames(List<XSSFSheet> sheet){
		XSSFRow headers = sheet.get(0).getRow(0);
		int lastIndex = headers.getLastCellNum();
		List<String> headerStr = new ArrayList<String>();
		for(int index=0; index < lastIndex; index++){
			headerStr.add(headers.getCell(index).getStringCellValue());
		}
		return headerStr.toArray(new String[headerStr.size()]);
	}

	public List<HashMap<String, String>> getBodyValues(List<XSSFSheet> sheet){
		String[] headerStr = getHeaderNames(sheet);
		int lastRowIndex = sheet.get(0).getLastRowNum();
		int lastCellIndex = 0;
		HashMap<String,String> rowValues = new HashMap<>();
		List<HashMap<String, String>> rowsList = new ArrayList<>();
		for(int rowIndex=1; rowIndex < lastRowIndex; rowIndex++){
			XSSFRow row = sheet.get(0).getRow(rowIndex);
			lastCellIndex = row.getLastCellNum();
			for(int cellIndex=0; cellIndex < lastCellIndex; cellIndex++){
				rowValues.put(headerStr[cellIndex],row.getCell(cellIndex).toString());
			}
			rowsList.add(new HashMap<String,String>(rowValues));
		}
		return rowsList;
	}

	public String[] replaceContent(String content, List<HashMap<String, String>> targetString){

		List<String> contentList = new ArrayList<String>();
		String targetContent = null;

		for(int index=0; index < targetString.size(); index++){
			targetContent = content;
			contentList.add(targetContent);
		}

		return contentList.toArray(new String[contentList.size()]);

	}

	public String stringReplacer(String content, String[] headers, XSSFRow row){
		for(int cellCnt=0; cellCnt<headers.length; cellCnt++){
			if(row.getCell(cellCnt) != null)
				content = content.replaceAll(String.format(Pattern.quote("#{%s}"), headers[cellCnt]), row.getCell(cellCnt).toString());

		}
		return content;
	}

	public HashMap<String, Object> dataMaker (HashMap<String, Object> rowValues, String[] headers, XSSFRow row){
		String tmp = rowValues.get("template_data").toString();
		String srvCode = rowValues.get("service_code").toString();

		DecimalFormat decimalFormat = new DecimalFormat("###,###");
		String moneyData;

		row = setCellTypeToString(row);

		for(int cellCnt=0; cellCnt<headers.length; cellCnt++){
			if(row.getCell(cellCnt) != null)
				switch(headers[cellCnt]) {
					case phone_no :
						rowValues.put("phone_no", row.getCell(cellCnt).toString());
						break;
					case name :
						rowValues.put("name", ""+row.getCell(cellCnt));
						//고객명이 두번중복 사용되기 떄문에 한번더 replace 시행한다.
						tmp = tmp.replaceAll(String.format(Pattern.quote("#{%s}"), headers[cellCnt]), row.getCell(cellCnt).toString());
						break;
					case birthday :
						// rowValues.put("birthday", row.getCell(cellCnt).toString().substring(0,6));
						rowValues.put("birthday", row.getCell(cellCnt).toString());
						// 납부자실명번호가 두번 중복 사용되기 때문에 한번 더 replace 실행
						// tmp = tmp.replaceAll(String.format(Pattern.quote("#{%s}"), headers[cellCnt]), row.getCell(cellCnt).toString());
						break;
					default :
						if (headers[cellCnt].equals("고지금액합계") || headers[cellCnt].equals("수납금액합계") || headers[cellCnt].equals("미납금액합계")
							|| headers[cellCnt].equals("1개월청구금액") || headers[cellCnt].equals("1개월수납금액") || headers[cellCnt].equals("1개월미납금액")
								|| headers[cellCnt].equals("2개월청구금액") || headers[cellCnt].equals("2개월수납금액") || headers[cellCnt].equals("2개월미납금액")
								|| headers[cellCnt].equals("3개월청구금액") || headers[cellCnt].equals("3개월수납금액") || headers[cellCnt].equals("3개월미납금액")
								|| headers[cellCnt].equals("4개월청구금액") || headers[cellCnt].equals("4개월수납금액") || headers[cellCnt].equals("4개월미납금액")
								|| headers[cellCnt].equals("5개월청구금액") || headers[cellCnt].equals("5개월수납금액") || headers[cellCnt].equals("5개월미납금액")
								|| headers[cellCnt].equals("미납총금액") || headers[cellCnt].equals("미납금액(등록예정금액)")) {
							tmp = tmp.replaceAll(String.format(Pattern.quote("#{%s}"), headers[cellCnt]), decimalFormat.format(Integer.parseInt(row.getCell(cellCnt).toString())));

						}

						else {
							tmp = tmp.replaceAll(String.format(Pattern.quote("#{%s}"), headers[cellCnt]), row.getCell(cellCnt).toString());
							break;
						}
				}
				rowValues.put("data",tmp);
				rowValues.put("service_code", srvCode);
		}
		return rowValues;
	}

	public XSSFRow setCellTypeToString(XSSFRow row) {
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if(row.getCell(i) == null) {

			} else
				if (row.getCell(i).getCellType() == CellType.NUMERIC) {
					if (DateUtil.isCellDateFormatted(row.getCell(i))) {
	//					row.getCell(i).getCellStyle().getDataFormatString()
						row.getCell(i).setCellValue(setCellDateTypeToString(row.getCell(i), "yyyymmdd"));
					} else {
						row.getCell(i).setCellType(CellType.STRING);
					}
				}
		}
		return row;
	}

	public String setCellDateTypeToString(XSSFCell cell, String dateFmt) {
//		Date date = cell.getDateCellValue();
//		String dateFmt = cell.getCellStyle().getDataFormatString();
		return new CellDateFormatter(dateFmt).format(cell.getDateCellValue());
	}


	public List<HashMap<String, Object>> getDocumentItems(MultipartFile file, HashMap<String, Object> templateMap) throws Exception {
		InputStream fileStream = null;
		try{
			fileStream = file.getInputStream();
			setExcelFile(fileStream);
			List<XSSFSheet> targetSheets = getContainExcelSheet();

			String[] headerList = getHeaderNames(targetSheets);

//			HashMap<String, Object> rowValues = docSendMaker();
			HashMap<String, Object> rowValues = templateMap;
			List<HashMap<String, Object>> rowsList = new ArrayList<>();

			int lastRowIndex = targetSheets.get(0).getLastRowNum();

			for(int rowIndex=1; rowIndex <= lastRowIndex; rowIndex++){
				XSSFRow row = targetSheets.get(0).getRow(rowIndex);

				if ( row.getCell(0) == null)
					continue;

				rowValues = dataMaker(rowValues, headerList, row);

				rowsList.add(new HashMap<String, Object> (rowValues));
			}

			return rowsList;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
