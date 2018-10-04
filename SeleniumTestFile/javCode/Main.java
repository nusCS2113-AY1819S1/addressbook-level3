import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import java.util.*;

// note to self. running code: -> go to file->project structure->modules->dependencies then add the jar files.

public class Main {

    public static void main(String[] args) throws InterruptedException
    {
        System.out.println("Enter ivle user name: ie: E1234567");
        Scanner scanner= new Scanner(System.in);
        String ivleUsername=scanner.nextLine();
        System.out.println("Enter Password");
        String ivlePassword=scanner.nextLine();



        System.setProperty("webdriver.chrome.driver","D:\\seleniumwebdriver\\chromedriver.exe");
        // this path is relative to where the chromedriver is stored.

        WebDriver driver= new ChromeDriver();
        //website to retrieve LAPI key from ivle
        driver.get("https://ivle.nus.edu.sg/LAPI/default.aspx?eu="+ivleUsername);
        //input of credentials
        WebElement usernameInputField0= driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder1_userid"));
        usernameInputField0.sendKeys(ivleUsername);
        WebElement passwordInputField0= driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder1_password"));
        passwordInputField0.sendKeys(ivlePassword);
        driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder1_btnSignIn")).click();

        try
        {
            driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_btnRequest")).click();

        }
        catch (Exception e)
        {
        }
        //extracting the key
        String key=driver.findElement(By.id("ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_lblInfo")).getText();
        String[] parts=key.split("key is ");
        String LAPIKey=parts[1];
        System.out.println(LAPIKey);

        String ivleTemplate="https://ivle.nus.edu.sg/api/login/?apikey=";

        //website where Auth token resides.
        driver.get(ivleTemplate+LAPIKey);

        WebElement usernameInputField= driver.findElement(By.id("userid"));
        usernameInputField.sendKeys(ivleUsername);
        WebElement passwordInput=driver.findElement(By.id("password"));
        passwordInput.sendKeys(ivlePassword);

        driver.findElement(By.id("loginimg1")).click();
        //retrieve auth token
        String authToken = driver.findElement(By.xpath("/html/body")).getText();
        System.out.println(authToken);

        driver.close();
    }

}
