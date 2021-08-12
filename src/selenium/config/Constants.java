package selenium.config;

public class Constants {

	public static final String URL = "https://www.demoqa.com/login";
	public static final String Path_TestData = "C://Users//Administrator//eclipse-workspace//Selenium_KeyWord_Driven_Framwork//src//selenium//dataEngine//DataEngine.xlsx";
	public static final String File_TestData = "DataEngine.xlsx";
	public static final String Path_OR = "C://Users//Administrator//eclipse-workspace//Selenium_KeyWord_Driven_Framwork//src//selenium//config//OR.txt";

	// List of Data Sheet Column Numbers
	public static final int Col_TestCaseID = 0;
	public static final int Col_TestScenarioID = 1;
	public static final int Col_PageObject = 4;
	public static final int Col_ActionKeyword = 5;
	public static final int Col_DataSet = 6;
	public static final int Col_TestStepResult = 7;

	// New entry in Constant variable
	public static final int Col_RunMode = 2;
	public static final int Col_Result = 3;

	// List of Data Engine Excel sheets
	public static final String Sheet_TestSteps = "Test Steps";
	// List of Data Engine Excel sheets
	public static final String Sheet_TestCases = "Test Cases";

	// List of Test Data
	public static final String UserName = "testuser_3";
	public static final String Password = "Test@123";

	// PASS FAIL RESULT
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
	public static final String KEYWORD_NORUN = "NO RUN";

}
