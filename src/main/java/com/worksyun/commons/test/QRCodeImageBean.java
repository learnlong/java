package com.worksyun.commons.test;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;

public class QRCodeImageBean implements QRCodeImage {

	 BufferedImage image;  
	  
	    public QRCodeImageBean(BufferedImage image) {  
	        this.image = image;  
	    }  
	  
	    public int getWidth() {  
	        return image.getWidth();  
	    }  
	  
	    public int getHeight() {  
	        return image.getHeight();  
	    }  
	  
	    public int getPixel(int x, int y) {  
	        return image.getRGB(x, y);  
	    } 

}
