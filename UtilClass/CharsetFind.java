import java.io.FileInputStream;
import java.io.IOException;

import org.mozilla.universalchardet.UniversalDetector;

/*
* 파일의 Charterset 찾기(UTF8, EUC-KR, ANSI 등)
* 
*/
public class CharsetFind {
	
	public static String returnCharset(String filePath) throws IOException {
		FileInputStream fis = new FileInputStream(filePath);
		
		UniversalDetector detector = new UniversalDetector(null);
		
		byte[] BOM = new byte[4];
		
		int nread;
		while((nread = fis.read(BOM)) > 0 && !detector.isDone()) {
			detector.handleData(BOM, 0, nread);
		}
		detector.dataEnd();
		
		String charset = detector.getDetectedCharset();
		
		System.out.println("charset : " + charset);
		
		return charset;
	}
	
}
