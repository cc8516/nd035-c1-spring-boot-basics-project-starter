package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.File;
import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@Autowired
	private EncryptionService encryptionService;

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
			driver = null;
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You have successfully signed up!"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	private void doLogOut() {
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-btn")));
		WebElement logoutButton = driver.findElement(By.id("logout-btn"));
		logoutButton.click();
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		driver.get("http://localhost:" + this.port + "/home?tab=file");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		String fileName = "upload5m.zip";

		WebElement fileTab = driver.findElement(By.id("nav-files-tab"));
		fileTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

		doLogOut();
	}

	@Test
	public void testUnauthorizedUserAccess() {
		// Unauthorized user cannot access Home page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

		// Unauthorized user can access Login page
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

		// Unauthorized user can access Signup page
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());
	}

	@Test
	public void testSignUpLoginLogoutAccess() {
		doMockSignUp("loginlogout","Test","loginlogout","123");
		doLogIn("loginlogout", "123");
		doLogOut();

		// Unauthorized user cannot access Home page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	private void doMockCreateNote(String firstName, String lastName, String userName, String pw) {
		doMockSignUp(firstName,lastName,userName,pw);
		doLogIn(userName, pw);

		driver.get("http://localhost:" + this.port + "/home?tab=note");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-btn")));
		WebElement createNoteButton = driver.findElement(By.id("add-note-btn"));
		createNoteButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputTitle = driver.findElement(By.id("note-title"));
		inputTitle.click();
		inputTitle.sendKeys("Title");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputDescription = driver.findElement(By.id("note-description"));
		inputDescription.click();
		inputDescription.sendKeys("Desc");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-note-btn")));
		WebElement submitNoteBtn = driver.findElement(By.id("submit-note-btn"));
		submitNoteBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		Assertions.assertTrue(driver.findElement(By.id("note-t")).getText().equals("Title"));
		Assertions.assertTrue(driver.findElement(By.id("note-desc")).getText().equals("Desc"));
	}

	private void removeNote() {
		driver.get("http://localhost:" + this.port + "/home?tab=note");

		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-delete-btn")));
		WebElement deleteNoteButton = driver.findElement(By.id("note-delete-btn"));
		deleteNoteButton.click();
	}
	@Test
	public void testCreateNote() {
		doMockCreateNote("Note", "Note", "Note", "123");

		removeNote();

		doLogOut();
	}

	@Test
	public void testEditNote() {
		doMockCreateNote("Note2", "Note2", "Note2", "123");

		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-edit-btn")));
		WebElement editNoteButton = driver.findElement(By.id("note-edit-btn"));
		editNoteButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputTitle = driver.findElement(By.id("note-title"));
		inputTitle.click();
		inputTitle.clear();
		inputTitle.sendKeys("Changed");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputDescription = driver.findElement(By.id("note-description"));
		inputDescription.click();
		inputDescription.clear();
		inputDescription.sendKeys("Changed");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-note-btn")));
		WebElement submitNoteBtn = driver.findElement(By.id("submit-note-btn"));
		submitNoteBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		Assertions.assertTrue(driver.findElement(By.id("note-t")).getText().equals("Changed"));
		Assertions.assertTrue(driver.findElement(By.id("note-desc")).getText().equals("Changed"));

		removeNote();

		doLogOut();
	}

	@Test
	public void testDeleteNote() throws InterruptedException {
		doMockCreateNote("Note3", "Note3", "Note3", "123");

		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-delete-btn")));
		WebElement deleteNoteButton = driver.findElement(By.id("note-delete-btn"));
		deleteNoteButton.click();

		Thread.sleep(3000);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("td"));

		Assertions.assertEquals(0, notesList.size());

		doLogOut();
	}

	private void doMockCreateCredential(String firstName, String lastName, String userName, String pw) {
		doMockSignUp(firstName,lastName,userName,pw);
		doLogIn(userName, pw);

		String password = "1234";
		String key = "1234567890123456";

		driver.get("http://localhost:" + this.port + "/home?tab=credential");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-credential-btn")));
		WebElement createCredentialButton = driver.findElement(By.id("add-credential-btn"));
		createCredentialButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement inputCredentialUrl = driver.findElement(By.id("credential-url"));
		inputCredentialUrl.click();
		inputCredentialUrl.sendKeys("URL");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement inputCredentialUsername = driver.findElement(By.id("credential-username"));
		inputCredentialUsername.click();
		inputCredentialUsername.sendKeys("Username");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement inputCredentialPassword = driver.findElement(By.id("credential-password"));
		inputCredentialPassword.click();
		inputCredentialPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialSubmitBtn")));
		WebElement submitCredentialBtn = driver.findElement(By.id("credentialSubmitBtn"));
		submitCredentialBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		Assertions.assertTrue(driver.findElement(By.id("cred-url")).getText().equals("URL"));
		Assertions.assertTrue(driver.findElement(By.id("cred-username")).getText().equals("Username"));
		Assertions.assertTrue(encryptionService.decryptValue(driver.findElement(By.id("cred-password")).getText(), key).equals(password));

	}

	private void removeCredential() {
		driver.get("http://localhost:" + this.port + "/home?tab=credential");

		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-cred-link")));
		WebElement deleteCredentialBtn = driver.findElement(By.id("delete-cred-link"));
		deleteCredentialBtn.click();
	}

	@Test
	public void testCreateCredential() {
		doMockCreateCredential("cred", "cred", "cred", "123");

		removeCredential();

		doLogOut();
	}

	@Test
	public void testEditCredential() {
		doMockCreateCredential("cred1", "cred1", "cred1", "123");

		String password = "9999";
		String key = "1234567890123456";

		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-cred-btn")));
		WebElement editCredentialButton = driver.findElement(By.id("edit-cred-btn"));
		editCredentialButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement inputCredentialUrl = driver.findElement(By.id("credential-url"));
		inputCredentialUrl.click();
		inputCredentialUrl.clear();
		inputCredentialUrl.sendKeys("Changed");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement inputCredentialUsername = driver.findElement(By.id("credential-username"));
		inputCredentialUsername.click();
		inputCredentialUsername.clear();
		inputCredentialUsername.sendKeys("Changed");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement inputCredentialPassword = driver.findElement(By.id("credential-password"));
		inputCredentialPassword.click();
		inputCredentialPassword.clear();
		inputCredentialPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialSubmitBtn")));
		WebElement submitCredentialBtn = driver.findElement(By.id("credentialSubmitBtn"));
		submitCredentialBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		Assertions.assertTrue(driver.findElement(By.id("cred-url")).getText().equals("Changed"));
		Assertions.assertTrue(driver.findElement(By.id("cred-username")).getText().equals("Changed"));
		Assertions.assertTrue(encryptionService.decryptValue(driver.findElement(By.id("cred-password")).getText(), key).equals(password));

		removeCredential();

		doLogOut();
	}

	@Test
	public void testDeleteCredential() throws InterruptedException {
		doMockCreateCredential("cred2", "cred2", "cred2", "123");

		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-cred-link")));
		WebElement deleteCredentialBtn = driver.findElement(By.id("delete-cred-link"));
		deleteCredentialBtn.click();

		Thread.sleep(3000);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		WebElement notesTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("td"));

		Assertions.assertEquals(0, notesList.size());

		doLogOut();
	}
}
