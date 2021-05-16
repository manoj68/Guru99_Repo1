package com.guru99.demo.testcases;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class Apache_ExcelToHashMap 
{
	@Test
	public void excelToHashMap() throws IOException
	{
		String file = "./" + "src\\main\\java\\com\\guru99\\demo\\testdata\\TestData_ApachePOI.xlsx";
		FileInputStream fis = new FileInputStream(file);	
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("Sheet2");
		
		int rowCount = sheet.getLastRowNum();
		System.out.println("total rows :" +rowCount);
		
		DataFormatter formatter = new DataFormatter();	//formatter is used to format the excel data to avoid any issues
		
		HashMap<String, String> data = new HashMap<String, String>();
		
		//Reading data from excle		
		
		for(int r=0; r<=rowCount;r++)
		{
			Cell cell1 = sheet.getRow(r).getCell(0);
			String key = formatter.formatCellValue(cell1);
			
			Cell cell2 = sheet.getRow(r).getCell(1);
			String value = formatter.formatCellValue(cell2);
			
		//	String key = sheet.getRow(r).getCell(0).getStringCellValue();
		//	String value = sheet.getRow(r).getCell(1).getStringCellValue();
			data.put(key, value);
		}
		
		//Read data from Hashmap /// this will also verify that it is added in hashmap
		for(Map.Entry entry : data.entrySet())
		{
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		
	}

}
