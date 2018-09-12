package com.letzautomate.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

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
			System.out.println(totalStepRows);
			int colsCount = sheet.getRow(0).getLastCellNum();
			System.out.println(colsCount);
			
			String[][] testSuite = new String[totalStepRows + 1][colsCount - 1];
			String cellValue = null;
			
			for(int testStepIndex = 0 ; testStepIndex < totalStepRows ; testStepIndex++ ) {		
				for(int colIndex = 0; colIndex < colsCount - 1; colIndex++) {
					Cell cell = sheet.getRow(testStepIndex).getCell(colIndex);
					if(cell == null || cell.getCellType() == cell.CELL_TYPE_BLANK) {
						testSuite[testStepIndex][colIndex] = " ";
						//System.out.println(" ");
					} else {
						//System.out.println(sheet.getRow(testStepIndex).getCell(colIndex).getStringCellValue());
						testSuite[testStepIndex][colIndex] = sheet.getRow(testStepIndex).getCell(colIndex).getStringCellValue();	
					}
				}				
			}
			wb.close();
			return testSuite;
	}
	
	public String[] getTCToExecute(String filePath) throws EncryptedDocumentException, InvalidFormatException, IOException {
		Workbook wb = WorkbookFactory.create(new File(filePath));
		Sheet sheet = wb.getSheet("TCToExecute");
		int numOfTCs = sheet.getLastRowNum();
		String[] tcArray = new String[numOfTCs + 1];
		
		for (int tcIndex = 0; tcIndex < numOfTCs; tcIndex++) {
			tcArray[tcIndex] = sheet.getRow(tcIndex).getCell(0).getStringCellValue();
		}
		wb.close();
		return tcArray;
	}
	
	public String getCellValueAsString(Sheet sheet, int rowID, int columnID) {
		Cell cell = sheet.getRow(rowID).getCell(columnID);
		if (cell.getCellType() == Cell.CELL_TYPE_BLANK  || cell == null){
			return null;
		}else {
			if (cell.getCellType() == Cell.CELL_TYPE_STRING ) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return Double.toString(cell.getNumericCellValue());
			} 
		}
		return null;
		
		
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
	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		/*Excel excel = new Excel();
		//excel.createWorkbook("C:/javasamples/myfile.xls");	
		System.out.println(excel.getTCToExecute());;*/
		
	}
	
	

}
