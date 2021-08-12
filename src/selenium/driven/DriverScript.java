package selenium.driven;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.xml.DOMConfigurator;

import selenium.config.ActionKeywords;
import selenium.config.Constants;
import selenium.utils.ExcelUtils;
import selenium.utils.Log;

public class DriverScript {

	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static String sData;
	public static Method[] methods;

	public static String sTestCaseID;
	public static String sRunMode;
	public static int iTestStep;
	public static int iLastTestStep;

	public static boolean bResult;

	public DriverScript() throws IOException {
		// TODO Auto-generated constructor stub
		actionKeywords = new ActionKeywords();
		methods = actionKeywords.getClass().getMethods();
	}

	public static void main(String[] args) throws Exception {
		ExcelUtils.setExcelFile(Constants.Path_TestData);
		// This is to start the Log4j logging in the test case
		DOMConfigurator.configure("log4j.xml");

		DriverScript startEgine = new DriverScript();

		startEgine.execute_TestCase();

	}

	private void execute_TestCase()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		// TODO Auto-generated method stub
		// This will return the total number of test cases mentioned in the Test cases
		// sheet
		int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);

		// This loop will execute number of times equal to Total number of test cases
		for (int iTestCase = 1; iTestCase <= iTotalTestCases; iTestCase++) {
			// Setting the value of bResult variable to 'true' before starting every test
			// case
			bResult = true;
			// This is to get the Test case name from the Test Cases sheet
			sTestCaseID = ExcelUtils.getCellData(iTestCase, Constants.Col_TestCaseID, Constants.Sheet_TestCases);
			// This is to get the value of the Run Mode column for the current test case
			sRunMode = ExcelUtils.getCellData(iTestCase, Constants.Col_RunMode, Constants.Sheet_TestCases);

			// This is the condition statement on RunMode value
			if (sRunMode.equalsIgnoreCase("Yes")) {
				// Only if the value of Run Mode is 'Yes', this part of code will execute
				iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
				iLastTestStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
				Log.startTestCase(sTestCaseID);

				// This loop will execute number of times equal to Total number of test steps
				for (; iTestStep <= iLastTestStep; iTestStep++) {

					sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,
							Constants.Sheet_TestSteps);
					sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject,
							Constants.Sheet_TestSteps);
					// Now we will use the data value and pass it to the methods
					sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);

					execute_Actions();

					// This is the result code, this code will execute after each test step
					// The execution flow will go in to this only if the value of bResult is 'false'
					if (bResult == false) {
						// If 'false' then store the test case result as Fail
						ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestCase, Constants.Col_Result,
								Constants.Sheet_TestCases);
						// End the test case in the logs
						Log.endTestCase(sTestCaseID);
						// By this break statement, execution flow will not execute any more test step
						// of the failed test case
						break;
					}
				}
			} else if (sRunMode.equalsIgnoreCase("No")) {
				// Only if the value of Run Mode is 'No', this part of code will execute
				ExcelUtils.setCellData(Constants.KEYWORD_NORUN, iTestCase, Constants.Col_Result,
						Constants.Sheet_TestCases);

				iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
				iLastTestStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);

				Log.info("Mark NO RUN for each test step");
				// This loop will execute number of times equal to Total number of test steps
				for (; iTestStep <= iLastTestStep; iTestStep++) {
					ExcelUtils.setCellData(Constants.KEYWORD_NORUN, iTestStep, Constants.Col_TestStepResult,
							Constants.Sheet_TestSteps);
				}
				bResult = false;

			}
			// This will only execute after the last step of the test case, if value is not
			// 'false' at any step
			if (bResult == true) {
				// Storing the result as Pass in the excel sheet
				ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestCase, Constants.Col_Result,
						Constants.Sheet_TestCases);
				Log.endTestCase(sTestCaseID);
			}
		}
	}

	private static void execute_Actions()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		// TODO Auto-generated method stub
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(sActionKeyword)) {
				// This code will pass three parameters to every invoke method
				methods[i].invoke(actionKeywords, sPageObject, sData);
				// This code block will execute after every test stepd

				if (bResult = true) {
					// If the executed test step value is true, Pass the test step in Excel sheet
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult,
							Constants.Sheet_TestSteps);
					break;
				} else {
					// If the executed test step value is false, Fail the test step in Excel sheet
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult,
							Constants.Sheet_TestSteps);
					// In case of false, the test execution will not reach to last step of closing
					// browser
					// So it make sense to close the browser before moving on to next test case
					ActionKeywords.closeBrowser("", "");
					break;
				}
			}
		}
	}

}
