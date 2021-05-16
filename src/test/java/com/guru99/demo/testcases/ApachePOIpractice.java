package com.guru99.demo.testcases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

@Test
public class ApachePOIpractice 
{
	// create excel >> add in project >> code to read dat from excel

	public void getExcelData() throws IOException		//READ excel data
	{
		String excelPath = "./" + "src\\main\\java\\com\\guru99\\demo\\testdata\\TestData_ApachePOI.xlsx";
		
		FileInputStream inputstream = new FileInputStream(excelPath);
		XSSFWorkbook workbook = new XSSFWorkbook(inputstream);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		
		int rowCount = sheet.getLastRowNum() + 1;	//added 1 as it starts from 0
		System.out.println("row count is : " + rowCount);
		
		int colCount = sheet.getRow(1).getLastCellNum();
		System.out.println("colCount is :" +colCount);
		
	//to get data from excle using for loop ///////////////////
		
		for(int r=0;r<rowCount;r++)
		{
			XSSFRow row = sheet.getRow(r);
			
			for(int c=0;c<colCount;c++)
			{
				XSSFCell cell = row.getCell(c);
				
				switch(cell.getCellType())
				{
				case STRING: System.out.print(cell.getStringCellValue()); 	break;
				case NUMERIC: System.out.print(cell.getNumericCellValue()); 	break;
				case BOOLEAN: System.out.print(cell.getBooleanCellValue()); 	break; 
				}
				System.out.print(" | ");
			}
			System.out.println();
		}
		
	// to get data using ITERATOR	////////////////////
		
		Iterator itrtr = sheet.iterator();
		while(itrtr.hasNext())	//checking iterator has another record am if the condition is true then it will execute while loop
		{
			XSSFRow row = (XSSFRow) itrtr.next(); 	//it will retuen the row details and we stored in variable row and its type is XSSFRow
			//also we have done typecasting to convert the row objects in row
			
			Iterator cellIterator= row.cellIterator();
			while(cellIterator.hasNext())
			{
				XSSFCell cell =(XSSFCell) cellIterator.next();
				
				switch(cell.getCellType())
				{
				case STRING: System.out.print(cell.getStringCellValue()); 	break;
				case NUMERIC: System.out.print(cell.getNumericCellValue()); 	break;
				case BOOLEAN: System.out.print(cell.getBooleanCellValue()); 	break; 
				}
				System.out.print(" | ");
			}
			System.out.println();
		}
	}
}
