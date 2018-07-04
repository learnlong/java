package com.worksyun.commons.util;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;


/**
 * Created by zhouhs on 2017/1/9.
 */
public class WordExportController {

   /* public static void main(String[] args) throws InvalidFormatException, IOException {
    	String path = "d:\\create_table.docx";
    	String ipath = "d:/bass.jpg";
    	String ipath2 = "d:/bass2.jpg";
    	createword(path,ipath,ipath2);
        System.out.println("create_table document written success.");
    }*/
    
    
    public static void createword(String savepath,String imgpath,String imgpath2) throws InvalidFormatException, IOException {
    	 //Blank Document
        XWPFDocument document= new XWPFDocument();

        //Write the Document in file system
        //FileOutputStream out = new FileOutputStream(new File("d:\\create_table.docx"));
        FileOutputStream out = new FileOutputStream(new File(savepath));

        //添加标题
        XWPFParagraph titleParagraph = document.createParagraph();
        //设置段落居中
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setText("身份证图片");
        titleParagraphRun.setColor("000000");
        titleParagraphRun.setFontSize(20);


        //换行
        XWPFParagraph paragraph1 = document.createParagraph();
        XWPFRun paragraphRun1 = paragraph1.createRun();
        paragraphRun1.setText("\r");
        
        
        XWPFParagraph p = document.createParagraph();


        //创建表格
        XWPFTable ComTable1 = document.createTable();
        
        //去表格边框  
        ComTable1.getCTTbl().getTblPr().unsetTblBorders();
        
        //外边框样式 
        setBorders(ComTable1);
        // 设置上下左右四个方向的距离
        ComTable1.setCellMargins(20, 20, 20, 20);

        //列宽自动分割  
        CTTblWidth comTableWidth = ComTable1.getCTTbl().addNewTblPr().addNewTblW();  
        comTableWidth.setType(STTblWidth.DXA);  
        comTableWidth.setW(BigInteger.valueOf(9072));  
        //去除单元格间的竖线
         ComTable1.setInsideVBorder(XWPFBorderType.NONE, 0, 0, "");
         //横线颜色
         ComTable1.setInsideHBorder(XWPFBorderType.SINGLE , 0, 0, "FFFFFF");
         //表格第一行  
         XWPFTableRow comTableRowOne = ComTable1.getRow(0); 
         setCell(comTableRowOne.getCell(0),"",p);
         comTableRowOne.getCell(0).setColor("ffffff");  //设置表格颜色
        
         XWPFTableRow comTableRow = ComTable1.createRow();
         //setPicCell(comTableRow.getCell(0),"",p,"d:/bass.jpg");
         setPicCell(comTableRow.getCell(0),"",p,imgpath);
         
         XWPFTableRow comTableRow2 = ComTable1.createRow();
         setCell(comTableRow2.getCell(0),"",p);
         comTableRow2.getCell(0).setColor("ffffff");
         
         @SuppressWarnings("unused")
		XWPFTableRow comTableRow3 = ComTable1.createRow();
         //setPicCell(comTableRow.getCell(0),"",p,"d:/bass.jpg");
         setPicCell(comTableRow.getCell(0),"",p,imgpath2);


        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

        //添加页眉
        CTP ctpHeader = CTP.Factory.newInstance();
        CTR ctrHeader = ctpHeader.addNewR();
        CTText ctHeader = ctrHeader.addNewT();
        String headerText = "Java POI create idcard file.";
        ctHeader.setStringValue(headerText);
        XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, document);
        //设置为右对齐
        headerParagraph.setAlignment(ParagraphAlignment.RIGHT);
        XWPFParagraph[] parsHeader = new XWPFParagraph[1];
        parsHeader[0] = headerParagraph;
        policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);


        //添加页脚
        CTP ctpFooter = CTP.Factory.newInstance();
        CTR ctrFooter = ctpFooter.addNewR();
        CTText ctFooter = ctrFooter.addNewT();
        String footerText = "上海复威网络科技有限公司";
        ctFooter.setStringValue(footerText);
        XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, document);
        headerParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFParagraph[] parsFooter = new XWPFParagraph[1];
        parsFooter[0] = footerParagraph;
        policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);


        document.write(out);
        out.close();
    }
  //设置图片题表格内容及对齐方式
    private static void setPicCell(XWPFTableCell cell,String text, XWPFParagraph p,String picpath) 
    		throws InvalidFormatException, IOException{

        if(cell.getParagraphs().size()>0){
              p=cell.getParagraphs().get(0);
     }else{
             p=cell.addParagraph();
     }
     XWPFRun pRun=p.createRun();
     pRun.setText(text);
     pRun.addPicture(new FileInputStream(picpath), XWPFDocument.PICTURE_TYPE_PNG, "", Units.toEMU(320), Units.toEMU(280));
     //pRun.setFontSize(20);

     //垂直居中
     cell.setVerticalAlignment(XWPFVertAlign.CENTER);
     
     //水平居中
     p.setAlignment(ParagraphAlignment.CENTER);


  }

 //设置表格内容及对齐方式
    private static void setCell(XWPFTableCell cell,String text,XWPFParagraph p){

        if(cell.getParagraphs().size()>0){
              p=cell.getParagraphs().get(0);
     }else{
             p=cell.addParagraph();
     }
     XWPFRun pRun=p.createRun();
     pRun.setText(text);
     //垂直居中
     cell.setVerticalAlignment(XWPFVertAlign.CENTER);
     //水平居中
     p.setAlignment(ParagraphAlignment.CENTER);


  }
    
    //设置外边框样式
    private static void setBorders( XWPFTable ComTable){
          CTTblBorders borders=ComTable.getCTTbl().getTblPr().addNewTblBorders();  
             CTBorder lBorder=borders.addNewLeft();  
             lBorder.setVal(STBorder.Enum.forString("single"));  
             lBorder.setSz(new BigInteger("10"));  
             lBorder.setColor("F4F4F4");  

             CTBorder rBorder=borders.addNewRight();  
             rBorder.setVal(STBorder.Enum.forString("single"));  
             rBorder.setSz(new BigInteger("10"));  
             rBorder.setColor("F4F4F4");  

             CTBorder tBorder=borders.addNewTop();  
             tBorder.setVal(STBorder.Enum.forString("single"));  
             tBorder.setSz(new BigInteger("10"));  
             tBorder.setColor("F4F4F4");  

             CTBorder bBorder=borders.addNewBottom();  
             bBorder.setVal(STBorder.Enum.forString("single"));  
             bBorder.setSz(new BigInteger("10"));  
             bBorder.setColor("F4F4F4");  


      }

}


