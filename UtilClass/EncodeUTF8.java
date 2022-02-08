/*
	 * 파일 컨트롤러 UTF-8 한글 깨짐현상 방지
	 * EUC-KR => UTF-8 로 변경
	 * 
	 * */
	private String encodeUTF8(String path, String sendFileName, String charset) {
		
		String s;
		String rename = path.replace(".txt", "_backup.txt");
		String readFileName 	= "";
		String backupFileName 	= "";
		File renameFile = new File(path);
		
		if(renameFile.exists()) {
			if(renameFile.renameTo(new File(rename))) {
				readFileName = rename;
				backupFileName = sendFileName.replace(".txt", "_backup.txt");
			} else {
//				System.out.println("file renameTo Error");
				util.writeLog("[noticeUpload 백업 Error] ", "file renameTo Error");
			}
		}
		
		try {
			BufferedReader in = null;
			
			if (charset.equals("UTF-8")) in = new BufferedReader(new InputStreamReader(new FileInputStream(readFileName)));
			else if(charset.equals("EUC-KR")) in = new BufferedReader(new InputStreamReader(new FileInputStream(readFileName), "EUC-KR"));
			
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path),"UTF-8"));
            
            while ((s = in.readLine()) != null) {
            	
            	String utf8Convert = new String(s.getBytes(),"UTF-8");
            	
                out.write(utf8Convert);
                out.newLine();
            }
            
            out.flush();
            in.close();
            out.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return backupFileName;
	}
