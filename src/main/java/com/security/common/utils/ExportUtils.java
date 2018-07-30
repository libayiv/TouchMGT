package com.security.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: ExportUtils 
 * @说明: 报表导出功能
 * @author: lbx
 * @date: 2018年7月19日 下午2:38:40 
 */
public class ExportUtils {
	private static Logger log = LoggerFactory.getLogger(ExportUtils.class);


	public static JSONObject export(String title,List<Map<String,String>> list){
		JSONObject reJson=new JSONObject();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(title); 
		
		HSSFCellStyle style = wb.createCellStyle();  
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
		HSSFFont  fontStyle=wb.createFont();
		fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(fontStyle);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
		style.setWrapText(true);
        sheet.autoSizeColumn((short)1); //调整第一列宽度
		sheet.setDefaultColumnWidth(40);

        HSSFRow row = sheet.createRow((int) 0);  
        
		HSSFCell cell = row.createCell(0);  
		int c=0;
		for (String key :list.get(0).keySet()) {
			c++;
			cell.setCellValue(key);  
			cell.setCellStyle(style); 
			cell = row.createCell(c); 	
		}
		


		for (int i = 0; i < list.size(); i++)  
		{  
			row = sheet.createRow((int) i + 1);  
			Map<String,String> map = list.get(i);
			int j=0;
			 for (String key : map.keySet()) {
				 row.createCell(j).setCellValue(map.get(key));
				 j++;
			 }

			// 第四步，创建单元格，并设置值  

		}  
		String url=title+UUID.randomUUID().toString()+".xls";
		FileOutputStream fout;
		try {
			File file = new File(CommonUtils.savePath+"/excel");  
			if(!file.exists()){  
			    file.mkdirs();  
			} 
			fout = new FileOutputStream(CommonUtils.savePath+"/excel/"+url);
			wb.write(fout);
			fout.close();
			reJson.put("code", "100");
			reJson.put("result",url );
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			reJson.put("code", "120");
			reJson.put("result", e.getMessage());
			e.printStackTrace();
		}
		return reJson ;

	}
}
