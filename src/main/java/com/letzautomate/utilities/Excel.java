package com.letzautomate.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
	
	Row row;
	
	public void createWorkbook(String path) throws EncryptedDocumentException, InvalidFormatException, IOException{
		Workbook wb =null;
		if(path.contains(".xls")) {
			wb = new HSSFWorkbook();
		} 	
		FileOutputStream fileOut = new FileOutputStream(new File(path));
        wb.write(fileOut);
        fileOut.close();
        wb.close();	
	}
	
	public String[][] getTestSuite(String filePath) throws EncryptedDocumentException, InvalidFormatException, IOException {
			Workbook wb = WorkbookFactory.create(new File(filePath));
			Sheet sheet = wb.getSheet("Testcases");
			int totalStepRows  = sheet.getLastRowNum();
			int colsCount = sheet.getRow(0).getLastCellNum();
			String[][] testSuite = new String[totalStepRows][colsCount];
			String cellValue = null;
			for(int testStepIndex = 1 ; testStepIndex <= totalStepRows ; testStepIndex++ ) {		
				for(int colIndex = 0; colIndex < colsCount ; colIndex++) {
					Cell cell = sheet.getRow(testStepIndex).getCell(colIndex);
					if(cell == null || cell.getCellType() == cell.CELL_TYPE_BLANK) {
						testSuite[testStepIndex -1][colIndex] = " ";
					} else {
						testSuite[testStepIndex -1][colIndex] = sheet.getRow(testStepIndex).getCell(colIndex).getStringCellValue();	
					}
				}				
			}
			wb.close();
			return testSuite;
	}
	public void writeDataArrayToExcel(String filePath, String[][] dataArray) throws IOException, EncryptedDocumentException, InvalidFormatException, InterruptedException {
		
		
		FileInputStream inputStream = new FileInputStream(new File(filePath));
		Workbook wb = WorkbookFactory.create( inputStream);
		Sheet sheet = wb.getSheet("Results");
		
		int totalStepRows  = dataArray.length;
		int colsCount = dataArray[1].length;	
		
		try {
			for (int rowIndex = 0; rowIndex < totalStepRows ; rowIndex ++) {				
				row = sheet.createRow(rowIndex);
				for(int colIndex = 0; colIndex < colsCount ; colIndex++) {
					Cell cell = row.createCell(colIndex);
					sheet.getRow(rowIndex).getCell(colIndex).setCellValue(dataArray[rowIndex][colIndex]);					
				}		
			}
		} catch(Exception e) {
			System.out.println(e.toString());
		}
		
		finally {
			 inputStream.close();
			 FileOutputStream fileOut = new FileOutputStream(filePath);
			 wb.write(fileOut);
			 fileOut.close();
			 wb.close();
			 
		}
	}
	/*public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		Excel excel = new Excel();
		//excel.createWorkbook("C:/javasamples/myfile.xls");	
		excel.getTestSuite("C:/KDF/runtime/testcases/testcases.xls");
	}*/
	
	

}
