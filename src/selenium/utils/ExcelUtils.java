package selenium.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import selenium.config.Constants;
import selenium.driven.DriverScript;

public class ExcelUtils {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;
	
	//This method is to set the File path and to open the Excel file
    //Pass Excel Path and SheetName as Arguments to this method
	public static void setExcelFile(String path) throws IOException {
		try {
			FileInputStream ExcelFile = new FileInputStream(path);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.error("File Not Found : "+e.getMessage());
			DriverScript.bResult = false;
		} catch (Exception e) {
			Log.error("Class Utils | Method setExcelFile | Exception desc : "+e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	//This method is to read the test data from the Excel cell
    //In this we are passing Arguments as Row Num, Col Num & Sheet Name
	public static String getCellData(int rowNum, int colNum, String sheetName) {
		try {
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
			Cell = ExcelWSheet.getRow(rowNum).getCell(colNum);
			String cellData = Cell.getStringCellValue();
			return cellData;
		} catch (Exception e) {
			Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
			DriverScript.bResult = false;
			return"";
		}
	}
	
	//This method is to get the row count used of the excel sheet
	public static int getRowCount(String sheetName) {
		int number=0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
			number = ExcelWSheet.getLastRowNum()+1;
		} catch (Exception e) {
			Log.error("Class Utils | Method getRowCount | Exception desc : "+e.getMessage());
			DriverScript.bResult = false;
		}
		return number;
	}
	
	//This method is to get the Row number of the test case
	//This methods takes three arguments(Test Case name , Column Number & Sheet name)
	public static int getRowContains(String sTestCaseName, int colNum, String sheetName) {
		int i=0;
		try {
			//ExcelWSheet = ExcelWBook.getSheet(sheetName);
			int rowCount = ExcelUtils.getRowCount(sheetName);
			
			for(i=0;i<rowCount;i++) {
				if(ExcelUtils.getCellData(i, colNum, sheetName).equalsIgnoreCase(sTestCaseName)) {
					break;
				}
			}
		} catch (Exception e) {
			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
			DriverScript.bResult = false;
		}
		return i;
	}
	
	//This method is to get the count of the test steps of test case
	//This method takes three arguments (Sheet name, Test Case Id & Test case row number)
	
	public static int getTestStepsCount(String sheetName, String sTestCaseID, int iTestCaseStart) {
		try {
			for(int i=iTestCaseStart;i<=ExcelUtils.getRowCount(sheetName);i++) {
				if(!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Col_TestCaseID, sheetName))) {
					int number =i-1;
					return number;
				}
			}
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
			int number = ExcelWSheet.getLastRowNum()+1;
			return number;
		} catch (Exception e) {
			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
			DriverScript.bResult = false;
			return 0;
		}
	}
	
	//This method is use to write value in the excel sheet
	//This method accepts four arguments (Result, Row Number, Column Number & Sheet Name)
	@SuppressWarnings("static-access")
	public static void setCellData(String result, int rowNum, int colNum, String sheetName) throws IOException {
		
		try {
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
			Row = ExcelWSheet.getRow(rowNum);
			Cell = Row.getCell(colNum, Row.RETURN_BLANK_AS_NULL);
			if(Cell == null) {
				Cell = Row.createCell(colNum);
				Cell.setCellValue(result);
			}else {
				Cell.setCellValue(result);
			}
			FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
			ExcelWBook.write(fileOut);
			//fileOut.flush();
			fileOut.close();
			ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Path_TestData));
			
		} catch (Exception e) {
			DriverScript.bResult = false;
		} 
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
