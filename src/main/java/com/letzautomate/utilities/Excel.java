package com.letzautomate.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import com.letzautomate.businessKeywords.BusinessKeywords;

public class Excel {
	WebDriver driver;
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
			String[][] testSuite = new String[totalStepRows + 1][colsCount - 1];
			String cellValue = null;
			for(int testStepIndex = 0 ; testStepIndex < totalStepRows ; testStepIndex++ ) {		
				for(int colIndex = 0; colIndex < colsCount - 1; colIndex++) {
					Cell cell = sheet.getRow(testStepIndex).getCell(colIndex);
					if(cell == null || cell.getCellType() == cell.CELL_TYPE_BLANK) {
						testSuite[testStepIndex][colIndex] = " ";
					} else {
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
		
		for (int tcIndex = 0; tcIndex <= numOfTCs; tcIndex++) {
			tcArray[tcIndex] = sheet.getRow(tcIndex).getCell(0).getStringCellValue();
		}
		wb.close();
		return tcArray;
	}
	
	public String getCellValueAsString(Sheet sheet, int rowID, int columnID) {
		Cell cell = sheet.getRow(rowID).getCell(columnID);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK ){
			return " ";
		}else {
			if (cell.getCellType() == Cell.CELL_TYPE_STRING ) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return Double.toString(cell.getNumericCellValue());
			} 
		}
		return "";
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
	
	public ArrayList<String> getTCsToExecute() throws EncryptedDocumentException, InvalidFormatException, IOException {
		ArrayList<String> tcsListToExecute = new ArrayList<String> ();
		String filePath = "C:/KDF/src/test/resources/testcases/tcsList.xls";
		Workbook wb = WorkbookFactory.create(new File(filePath));
		Sheet sheet = wb.getSheet("tcs");
		Iterator<Row> rowIterator = sheet.rowIterator();
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			tcsListToExecute.add(row.getCell(0).getStringCellValue());
		}
		return tcsListToExecute;
	}
	
	public void getTestSuiteFromTcDB() throws EncryptedDocumentException, InvalidFormatException, IOException {
		ArrayList<String> tcsToExecute = getTCsToExecute();
		ArrayList<String> tcInfoList = new ArrayList<String> ();
		LinkedHashMap<String, ArrayList<String>> tsMap = new LinkedHashMap<String, ArrayList<String>> ();				
		ArrayList<String> tcsListRaw = new ArrayList<String> ();
		String filePath = "C:/KDF/src/test/resources/testcases/testcasesDB.xls";
		Workbook dbWorkbook = WorkbookFactory.create(new File(filePath));
		Sheet dbSheet = dbWorkbook.getSheet("tcs");
		Iterator<Row> rowIterator = dbSheet.rowIterator();
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell cell = row.getCell(0);
			if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				tcsListRaw.add("NA");
			}else {
			tcsListRaw.add(row.getCell(0).getStringCellValue());
			}
		}
		String tcID = null;
		String tcInfo = null;
		for(String tcToExecute : tcsToExecute) {
			int tcRowID = tcsListRaw.indexOf(tcToExecute);
			tcID = getCellValueAsString(dbSheet, tcRowID, 0);
			String keywordVal = "";
			do {
				tcInfo = getCellValueAsString(dbSheet, tcRowID, 1)
											+ "||| " + getCellValueAsString(dbSheet, tcRowID, 2)
											+ "|||" + getCellValueAsString(dbSheet, tcRowID, 3)
											+ "|||" + getCellValueAsString(dbSheet, tcRowID, 4)
											+ "|||" + getCellValueAsString(dbSheet, tcRowID, 5)
											+ "|||" + getCellValueAsString(dbSheet, tcRowID, 6);
				
				/*tcInfo = getCellValueAsString(dbSheet, tcRowID, 0);
				String keyword = getCellValueAsString(dbSheet, tcRowID, 1);
				String input = getCellValueAsString(dbSheet, tcRowID, 2);
				String dep =  getCellValueAsString(dbSheet, tcRowID, 3);
				String status = getCellValueAsString(dbSheet, tcRowID, 4);
				String actual = getCellValueAsString(dbSheet, tcRowID, 5);
				String screenshot = getCellValueAsString(dbSheet, tcRowID, 6);*/
					
					tcInfoList.add(tcInfo);
					keywordVal = getCellValueAsString(dbSheet, tcRowID, 1);
					tcRowID = tcRowID + 1;						
				}while (!keywordVal.equals("tcEnd"));
				tsMap.put(tcID, tcInfoList)	;		
		}
		System.out.println(tsMap.get("login1"));		
	}
	
	public ArrayList<String> getASingleColumnValues(Sheet sheet, int columnIndex) throws EncryptedDocumentException, InvalidFormatException, IOException{
		ArrayList<String> colValuesList = new ArrayList<String> ();
		Iterator<Row> rowIterator = sheet.iterator();
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell cell = row.getCell(columnIndex);
			if( cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				colValuesList.add("NoValue");
			}else {
				colValuesList.add(cell.getStringCellValue());
			}
		}
		return colValuesList;	
	}
	
	public void getTestSuiteFromTcDB1() throws EncryptedDocumentException, InvalidFormatException, IOException {
		String filePath = "C:/KDF/src/test/resources/testcases/testcasesDB.xls";
		//ArrayList<String> tcsToExecute = getTCsToExecute();
		Workbook dbWorkbook = WorkbookFactory.create(new File(filePath));
		Sheet dbSheet = dbWorkbook.getSheet("tcs");
		ArrayList<String> tcIDsTempList = getASingleColumnValues(dbSheet, 0);	
		System.out.println(tcIDsTempList);
		int tcStartRowID = tcIDsTempList.indexOf("login2");
		System.out.println(tcStartRowID);
		dbWorkbook.close();
	}
	
		

}
