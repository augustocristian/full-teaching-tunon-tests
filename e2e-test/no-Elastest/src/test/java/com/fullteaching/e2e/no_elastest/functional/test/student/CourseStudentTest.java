package com.fullteaching.e2e.no_elastest.functional.test.student;

import com.fullteaching.e2e.no_elastest.common.BaseLoggedTest;
import com.fullteaching.e2e.no_elastest.common.CourseNavigationUtilities;
import com.fullteaching.e2e.no_elastest.common.NavigationUtilities;
import com.fullteaching.e2e.no_elastest.common.exception.BadUserException;
import com.fullteaching.e2e.no_elastest.common.exception.ElementNotFoundException;
import com.fullteaching.e2e.no_elastest.common.exception.NotLoggedException;
import com.fullteaching.e2e.no_elastest.common.exception.TimeOutExeception;
import com.fullteaching.e2e.no_elastest.utils.Click;
import com.fullteaching.e2e.no_elastest.utils.ParameterLoader;
import com.fullteaching.e2e.no_elastest.utils.Wait;
import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static com.fullteaching.e2e.no_elastest.common.Constants.*;
import static io.github.bonigarcia.seljup.BrowserType.CHROME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SeleniumExtension.class)
public class CourseStudentTest extends BaseLoggedTest {
	
	public String roles;
	

    public static Stream<Arguments> data() throws IOException {
        return ParameterLoader.getTestStudents();
    }
    
    /**
     * This tests get the login the user as student, go the the courses  and check if 
     * there is any course in the list.After it, click in the first course of the list 
     * and wait for the visibility of it.In second place, the student go to the home,
     * Session,Forum, Files and attenders tab to check if they are visible.
     */ 
    @ParameterizedTest
	@MethodSource("data")
    public void studentCourseMainTest(String user, String password, String role, WebDriver rwd)throws ElementNotFoundException, BadUserException, NotLoggedException, TimeOutExeception {
    	/*boolean chrome=true;
    	if(chrome) {
    	System.setProperty("webdriver.chrome.driver",
    	           "C:/chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();}
    	else {
    		System.setProperty("webdriver.gecko.driver",
     	           "C:/chromedriver_win32/geckodriver.exe");
 		driver = new FirefoxDriver();
    		
    		
    	}
    	driver.manage().window().maximize();*/
		driver = loginAndValidate(driver,  user, password);

    	try {
    		if(!NavigationUtilities.amIHere(driver,COURSES_URL.replace("__HOST__", host)))
        		driver = NavigationUtilities.toCoursesHome(driver);
	    	
    		//go to first course
    		//get course list
    		List<String>course_list = CourseNavigationUtilities.getCoursesList(driver, host);
    		if (course_list.size()<0)  fail("No courses available for test user");
    		
    		WebElement course_button = CourseNavigationUtilities.getCourseElement(driver, course_list.get(0)).findElement(By.className("title"));
    			    	
	    	driver = Click.element(driver, course_button);
	    	
	    	Wait.notTooMuch(driver).until(ExpectedConditions.visibilityOfElementLocated(By.id(COURSE_TABS_TAG)));
	    	
    	}catch(Exception e) {
			fail("Failed to load Courses Tabs"+ e.getClass()+ ": "+e.getLocalizedMessage());
    	}
    	//Check tabs
    	//Home tab 
    	try {
    
    		//WebDriverWait wait = new WebDriverWait(driver, 10); 
    		//wait.until(ExpectedConditions.presenceOfElementLocated(By.id(HOME_ICON_ID)));
    	
    		driver = CourseNavigationUtilities.go2Tab(driver, HOME_ICON);
    		
    		
    		
    	} catch(Exception e) {
    		fail("Failed to load home tab" + e.getClass() + ": "+e.getLocalizedMessage());
    	}
    	
    	try {
    		driver = CourseNavigationUtilities.go2Tab(driver, SESSION_ICON);
    	} catch(Exception e) {
    		fail("Failed to load session tab"+ e.getClass()+ ": "+e.getLocalizedMessage());
    	}
    
    	try {
    		driver = CourseNavigationUtilities.go2Tab(driver, FORUM_ICON);
    	} catch(Exception e) {
    		fail("Failed to load forum tab"+ e.getClass()+ ": "+e.getLocalizedMessage());
    	}
    	
    	try {
    		driver = CourseNavigationUtilities.go2Tab(driver, FILES_ICON);
    	} catch(Exception e) {
    		fail("Failed to load files tab"+ e.getClass()+ ": "+e.getLocalizedMessage());
    	}
    	
    	try {
    		driver = CourseNavigationUtilities.go2Tab(driver, ATTENDERS_ICON);	
    	} catch(Exception e) {
    		fail("Failed to load attenders tab"+ e.getClass()+ ": "+e.getLocalizedMessage());
    	}
    	
 

    	
    }
    


}
