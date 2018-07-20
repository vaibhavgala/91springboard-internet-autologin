import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SpringboardInternetAutoLogin
{
	public static void main(String args[])
	{
		isInternetAvailable();
		System.out.println(isInternetAvailable());
	}
	
	public static boolean isInternetAvailable()
	{
		StringBuffer html = null;
		try {
	
			String url = "http://portal.91springboard.com";
	
			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setReadTimeout(5000);
			conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
			conn.addRequestProperty("User-Agent", "Mozilla");
			conn.addRequestProperty("Referer", "google.com");
	
			//System.out.println("Request URL ... " + url);
	
			boolean redirect = false;
	
			// normally, 3xx is redirect
			int status = conn.getResponseCode();
			if (status != HttpURLConnection.HTTP_OK) {
				if (status == HttpURLConnection.HTTP_MOVED_TEMP
					|| status == HttpURLConnection.HTTP_MOVED_PERM
						|| status == HttpURLConnection.HTTP_SEE_OTHER)
				redirect = true;
			}
	
			//System.out.println("Response Code ... " + status);
	
			if (redirect) 
			{
	
				// get redirect url from "location" header field
				String newUrl = conn.getHeaderField("Location");
	
				// get the cookie if need, for login
				String cookies = conn.getHeaderField("Set-Cookie");
	
				// open the new connnection again
				conn = (HttpURLConnection) new URL(newUrl).openConnection();
				conn.setRequestProperty("Cookie", cookies);
				conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
				conn.addRequestProperty("User-Agent", "Mozilla");
				conn.addRequestProperty("Referer", "google.com");
										
				//System.out.println("Redirect to URL : " + newUrl);
	
			}
	
			BufferedReader in = new BufferedReader(
		                              new InputStreamReader(conn.getInputStream()));
			String inputLine;
			html = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) 
			{
				html.append(inputLine);
			}
			in.close();
			
		    } catch (Exception e) {
			e.printStackTrace();
		    }

		if (html.indexOf("91Springboard Captive Portal - Login") > -1)
		{
			//System.out.println("internet not on");
			return false;
		}
		else
		{
			//System.out.println("internet on");
			return true;
		}

		//System.out.println("URL Content... \n" + html.toString());
		//System.out.println("Done");
	}
	
	public void portalAutoLogin() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Utils\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://portal.91springboard.com/login");
		driver.manage().window().maximize();
		driver.findElement(By.id("emailField")).sendKeys("dummy@test.com");
		driver.findElement(By.id("passwordField")).sendKeys("dummy.", Keys.ENTER);
	}
}