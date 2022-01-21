package com.ai.v2.common;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class FileUtil {
	public static void mkDir(String fileSavePath) {
		File folder = new File(fileSavePath);
		
		if(!folder.exists()) {
			try {
				folder.mkdir();
				System.out.println("폴더 생성 완료");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("이미 폴더가 존재합니다.");
		}
		
	}
	
	public static void ImageResize(String imgFilePath) throws Exception {
		
		int newWidth = 600;
		int newHeight = 300;
		String mainPosition = "W";
		
		Image image;
		int orImgWidth;
		int orImgHeight;
		double ratio;
		int w;
		int h;
		
		System.out.println("img " + imgFilePath);
		
		try {
			
			image = ImageIO.read(new File(imgFilePath));
			
			orImgWidth = image.getWidth(null);
			orImgHeight = image.getHeight(null);
			
			if (mainPosition.equals("W")) { //비율 넓이기준
				ratio = (double)newWidth/(double)orImgWidth;
				w = (int)(orImgWidth * ratio);
				h = (int)(orImgHeight * ratio);
			} else if (mainPosition.equals("H")) { //비율 높이기준
				ratio = (double)newHeight/(double)orImgHeight;
				w = (int)(orImgWidth * ratio);
				h = (int)(orImgHeight * ratio);
			} else { 
				w = newWidth;
				h = newHeight;
			}
			
			// 이미지 리사이즈
            // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
            // Image.SCALE_FAST    : 이미지 부드러움보다 속도 우선
            // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
            // Image.SCALE_SMOOTH  : 속도보다 이미지 부드러움을 우선
            // Image.SCALE_AREA_AVERAGING  : 평균 알고리즘 사용
			
			Image resizeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
			
			//새 이미지 저장하기
			BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics g = newImage.getGraphics();
			g.drawImage(resizeImage, 0, 0, null);
			ImageIO.write(newImage, "jpg", new File(imgFilePath));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	//중복된 파일명 넘버링하기
	public static String getFileName(String path, String filename) {
		
		//path = "/resources/files/"
		//filename = "dog.png"
		int n = 1;
		int index = filename.lastIndexOf(".");
		String tempName = filename.substring(0, index); //"dog"
		String tempExt = filename.substring(index); //".png"
		
		
		//중복 검사
		while (true) {
			File file = new File(path + "/" + filename); // files/dog.png
			if (file.exists()) {
				filename = tempName + "_" + n + tempExt; // "dog_1.png"
				n++;
			} else {
				return file.getName();//중복 안된다고 판단된 유일한 이름
			}
			
		}	
		
	}
}
