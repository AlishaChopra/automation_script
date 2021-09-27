package Github;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Automation_Github_Repository {
	
	WebDriver driver;
	Scanner sc;
	public Automation_Github_Repository()
	{
		String path = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver",path+"\\drivers\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
		sc = new Scanner(System.in);
	}
	
	public void github_login()
	{
			driver.get("https://github.com/");
			System.out.println("Enter user name");
			String user_name = sc.nextLine();
			System.out.println("Enter password");
			String password = sc.nextLine();
			
			WebElement signin = driver.findElement(By.xpath("//a[@href='/login']"));
			signin.click();

			WebElement uname = driver.findElement(By.xpath("//input[@id='login_field']"));
	        uname.click();
		
	        uname.sendKeys(user_name);

	        // clicks and inputs in the password field
	        WebElement pwd = driver.findElement(By.xpath("//input[@id='password']"));
	        pwd.click();
	        pwd.sendKeys(password);

	       	// click the login button
	        WebElement button = driver.findElement(By.name("commit"));
	        button.click();
	          
	}
	
	public void create_repository()
	{
		driver.get("https://github.com/new");
		
		System.out.println("Enter repository name");
		String repository_name = sc.nextLine();
		System.out.println("Enter description");
		String description = sc.nextLine();
		String getOwner = driver.findElement(By.xpath("//*[@id='repository-owner']")).getText();
		WebElement repo_name = driver.findElement(By.xpath("//input[@id='repository_name']"));
		repo_name.click();
		repo_name.sendKeys(repository_name);

		//fill in the description	
		WebElement desc = driver.findElement(By.xpath("//*[@id='repository_description']"));
		desc.click();
		desc.sendKeys(description);

		//to create public repository
		WebElement public_selected = driver.findElement(By.xpath("//*[@id='repository_visibility_public']"));
		public_selected.click();

		//to add readme file
		WebElement add_readme_checkbox = driver.findElement(By.xpath("//*[@id='repository_auto_init']"));
		add_readme_checkbox.click();


		//create repository
		WebElement create_repo = driver.findElement(By.xpath("//*[contains(text(),'Create repository')]"));
		create_repo.submit();
		
	}
	
	public void delete_repository() throws InterruptedException
	{
		
		String url = driver.getCurrentUrl();
		driver.get(url+"/settings");
		int secondLastIndexOf = url.substring(0, url.lastIndexOf('/')).lastIndexOf('/');
		String str = url.substring(secondLastIndexOf+1, url.length());
		WebElement delete_repo = driver.findElement(By.xpath("//*[contains(text(),'Delete this repository')]"));
		delete_repo.click();
		WebElement confirm_name =  driver.findElement(By.xpath("//input[@aria-label='Type in the name of the repository to confirm that you want to delete this repository.']"));
		if(confirm_name.isDisplayed())
		{
			confirm_name.click();
			confirm_name.sendKeys(str);
		}
		WebElement delete_select = driver.findElement(By.xpath("//*[contains(text(),'I understand the consequences, delete this repository')]"));
		delete_select.submit();
		String output=driver.findElement(By.xpath("//*[@id='js-flash-container']")).getText();
		if(output!="")
		{
			System.out.println(output);
		}
	}
	
	public static void main(String args[]) throws InterruptedException
	{
		Automation_Github_Repository repo = new Automation_Github_Repository();
		repo.github_login();
		repo.create_repository();
		repo.delete_repository();
	}
	
}
