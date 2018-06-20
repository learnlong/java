package com.worksyun.commons.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.worksyun.api.mapper.UseraddressMapper;
import com.worksyun.api.model.Userauthenticate;
import com.worksyun.api.model.Userbaseinfo;

public class PoiUtil {
	
	
	@SuppressWarnings("unused")
	@Autowired
	private  UseraddressMapper UseradderessMapper;
	
	
	/*public static void main(String[]args) throws IOException, DocumentException {
		PoiUtil po = new PoiUtil();
		po.execlout();
	}*/
	
	@SuppressWarnings("unused")
	public static void pdfout() throws DocumentException, IOException {
		  Document document = new Document();
		  // 第二步，设置要到出的路径
		  FileOutputStream out = new  FileOutputStream("d:/workbook222.pdf");
	      //如果是浏览器通过request请求需要在浏览器中输出则使用下面方式
	      //OutputStream out = response.getOutputStream();
		  // 第三步,设置字符
		  BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
		  Font fontZH = new Font(bfChinese, 12.0F, 0);
		  // 第四步，将pdf文件输出到磁盘
	      PdfWriter writer = PdfWriter.getInstance(document, out);
	      // 第五步，打开生成的pdf文件
	      document.open();
	      // 第六步,设置内容
	      String title = "标题";
	      document.add(new Paragraph(new Chunk(title, fontZH).setLocalDestination(title)));
	      document.add(new Paragraph("\n"));
	      // 创建table,注意这里的2是两列的意思,下面通过table.addCell添加的时候必须添加整行内容的所有列
	      PdfPTable table = new PdfPTable(2);
	      table.setWidthPercentage(100.0F);
	      table.setHeaderRows(1);
	      table.getDefaultCell().setHorizontalAlignment(1);
	      table.addCell(new Paragraph("序号", fontZH));
	      table.addCell(new Paragraph("结果", fontZH));
	      table.addCell(new Paragraph("1", fontZH));
	      table.addCell(new Paragraph("出来了", fontZH));
	      
	      document.add(table);
	      document.add(new Paragraph("\n"));
	      // 第七步，关闭document
	      document.close();
	      
	      System.out.println("导出pdf成功~");
	}
	
	
	@SuppressWarnings({ "resource", "deprecation" })
	public  void execlout() throws IOException {
		  //创建workbook   
        HSSFWorkbook workbook = new HSSFWorkbook();   
        //创建sheet页  
        HSSFSheet sheet = workbook.createSheet("学生表");  
        //创建列宽
        sheet.setColumnWidth(0, 256*20);
        sheet.setColumnWidth(1, 256*20);
        sheet.setColumnWidth(2, 256*20);
        sheet.setColumnWidth(3, 256*20);
        //创建单元格  
        HSSFRow row = sheet.createRow(0);   
        HSSFCell c0 = row.createCell(0);   
        c0.setCellValue(new HSSFRichTextString("开通日期：2017年12月3日"));  
        HSSFCellStyle cs = workbook.createCellStyle();
        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFRow row1 = sheet.createRow(1);   
        HSSFCell c6 = row1.createCell(0);   
        c6.setCellValue(new HSSFRichTextString("用户姓名"));   
        HSSFCell c7 = row1.createCell(1);   
        c7.setCellValue(new HSSFRichTextString("身份证号码")); 
        HSSFCell c8 = row1.createCell(2);   
        c8.setCellValue(new HSSFRichTextString("对应ICCID")); 
        HSSFCell c9 = row1.createCell(3);   
        c9.setCellValue(new HSSFRichTextString("终端设备号")); 
        
        HSSFRow row2 = sheet.createRow(2);
        HSSFCell cc = row2.createCell(0);
        cc.setCellValue(new HSSFRichTextString("张三"));
        HSSFCell cc2 = row2.createCell(1);
        cc2.setCellValue(new HSSFRichTextString("321202199203012313"));
        HSSFCell cc3 = row2.createCell(2);
        cc3.setCellValue(new HSSFRichTextString("sadkasdasdas324234"));
        HSSFCell cc4 = row2.createCell(3);
        cc4.setCellValue(new HSSFRichTextString("4234234kjlkjsffsdf"));
          
        CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 3);    
        sheet.addMergedRegion(region1);   

	    FileOutputStream out = new  FileOutputStream("D:/workbook.xls");
	    //如果是浏览器通过request请求需要在浏览器中输出则使用下面方式
	    //OutputStream out = response.getOutputStream();
	    workbook.write(out);
	    out.close();
	    System.out.println("导出excel成功~");
	}
	
	
	public static HSSFWorkbook expExcel(List<Userbaseinfo> Userbaseinfo) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        createSheetStyle(workbook, sheet);

        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        Userbaseinfo category = new Userbaseinfo();
        Field[] fields = category.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            cell = row.createCell(j);
            String value = "";
            if(fields[j].getName().equals("userid")) {
            	value = "用户id";
            }else if(fields[j].getName().equals("certificationid")) {
            	value = "认证id";
            }else if(fields[j].getName().equals("mobile")) {
            	value = "手机号";
            }else if(fields[j].getName().equals("password")) {
            	value = "密码";
            }else if(fields[j].getName().equals("nickname")) {
            	value = "昵称";
            }else if(fields[j].getName().equals("name")) {
            	value = "姓名";
            }else if(fields[j].getName().equals("idcard")) {
            	value = "身份证号码";
            }else if(fields[j].getName().equals("cardpicture1")) {
            	value = "身份证图1";
            }else if(fields[j].getName().equals("cardpicture2")) {
            	value = "身份证图2";
            }else if(fields[j].getName().equals("usertype")) {
            	value = "用户类型";
            }else if(fields[j].getName().equals("creationtime")) {
            	value = "创建时间";
            }else if(fields[j].getName().equals("status")) {
            	value = "用户状态";
            }else if(fields[j].getName().equals("avatar")) {
            	value = "图像";
            }else if(fields[j].getName().equals("integral")) {
            	value = "积分";
            }else if(fields[j].getName().equals("area")) {
            	value = "地区";
            }else if(fields[j].getName().equals("modifyuserId")) {
            	value = "修改人id";
            }else if(fields[j].getName().equals("modifyusername")) {
            	value = "修改人姓名";
            }else if(fields[j].getName().equals("modifytime")) {
            	value = "修改时间";
            }else if(fields[j].getName().equals("creationusername")) {
            	value = "创建人姓名";
            }else if(fields[j].getName().equals("creationuserid")) {
            	value = "创建人id";
            }
            cell.setCellValue(value);
            //cell.setCellStyle(this.textAlignCenter);
        }

        for (int i = 0; i < Userbaseinfo.size(); i++) {
            category = Userbaseinfo.get(i);
            row = sheet.createRow(i + 1);
            for (int k = 0; k < fields.length; k++) {
                Field field = fields[k];
                Object o = invokeGet(category, field.getName());
                HSSFCellStyle cellStyle = workbook.createCellStyle();

                HSSFDataFormat format= workbook.createDataFormat();

                cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));

                cell = row.createCell(k);
                if(field.getName().equals("creationtime")||field.getName().equals("modifytime")) {
                	if(o==null) {
                		cell.setCellValue(o != null ? o.toString() : "");
                	}else {
                		SimpleDateFormat format2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US); 
                        Date o2 = null;
                        try {
        					 o2=format2.parse(o.toString());
        				} catch (ParseException e) {
        					e.printStackTrace();
        				}
                        String o3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(o2);
                    	cell.setCellValue(o3 != null ? o3.toString() : "");
                	}
                	//cell.setCellStyle(cellStyle);
                }else {
                	cell.setCellValue(o != null ? o.toString() : "");
                }
                //cell.setCellStyle((k == 0 || k == 1) ? this.textAlignCenter : this.textAlignLeft);
            }
        }
        return workbook;
    }
	
	
	
	public static HSSFWorkbook expExcel2(List<Userauthenticate> Userauthenticate) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		createSheetStyle(workbook, sheet);

		HSSFRow row = sheet.createRow(0);
		HSSFCell cell;
		Userauthenticate category = new Userauthenticate();
		Field[] fields = category.getClass().getDeclaredFields();
		for (int j = 0; j < fields.length; j++) {
			cell = row.createCell(j);
			cell.setCellValue(fields[j].getName());
			// cell.setCellStyle(this.textAlignCenter);
		}

		for (int i = 0; i < Userauthenticate.size(); i++) {
			category = Userauthenticate.get(i);
			row = sheet.createRow(i + 1);
			for (int k = 0; k < fields.length; k++) {
				Field field = fields[k];
				Object o = invokeGet(category, field.getName());
				HSSFCellStyle cellStyle = workbook.createCellStyle();

				HSSFDataFormat format = workbook.createDataFormat();

				cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));

				cell = row.createCell(k);
				if (field.getName().equals("creationtime") || field.getName().equals("modifytime")) {
					if (o == null) {
						cell.setCellValue(o != null ? o.toString() : "");
					} else {
						SimpleDateFormat format2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
						Date o2 = null;
						try {
							o2 = format2.parse(o.toString());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						String o3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(o2);
						cell.setCellValue(o3 != null ? o3.toString() : "");
					}
					// cell.setCellStyle(cellStyle);
				} else {
					cell.setCellValue(o != null ? o.toString() : "");
				}
				// cell.setCellStyle((k == 0 || k == 1) ? this.textAlignCenter :
				// this.textAlignLeft);
			}
		}
		return workbook;
	}
	
	private static void createSheetStyle(HSSFWorkbook _workbook, HSSFSheet _sheet) {
        // 设置表字体
        HSSFFont font10 = _workbook.createFont();
        font10.setFontHeightInPoints((short) 12);
        font10.setFontName("黑体");
        // 设置表样
        //this.textAlignCenter = getCellStyle(_workbook, font10, HSSFCellStyle.ALIGN_CENTER);
       // this.textAlignLeft = getCellStyle(_workbook, font10, HSSFCellStyle.ALIGN_LEFT);
        // 设置列宽
        _sheet.setColumnWidth(0, 4000);
        _sheet.setColumnWidth(1, 4000);
        _sheet.setColumnWidth(2, 10000);
        _sheet.setColumnWidth(3, 10000);
    }
	
	
	private static Object invokeGet(Object o, String fieldName) {
        Method method = getGetMethod(o.getClass(), fieldName);
        try {
            return method.invoke(o, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static Method getGetMethod(Class objectClass, String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            return objectClass.getMethod(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	
}