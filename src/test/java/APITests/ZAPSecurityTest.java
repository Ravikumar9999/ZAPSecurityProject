package APITests;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class ZAPSecurityTest {
	
	
	static final String ZAP_PROXY_ADDRESS ="localhost";
	static final int ZAP_PROXY_PORT = 8080;
	static final String ZAP_API_KEY = "nc29ohrsk6sgecme198g57uq2r";
	
	
	private WebDriver driver;
	private ClientApi api;
	
	@BeforeMethod
	public void setUp() {
		String proxyServerUrl = ZAP_PROXY_ADDRESS + ":" +ZAP_PROXY_PORT;
		
		Proxy proxy = new Proxy();
		proxy.setHttpProxy(proxyServerUrl);
		proxy.setSslProxy(proxyServerUrl);
		
		ChromeOptions options = new ChromeOptions();
		options.setAcceptInsecureCerts(true);
		options.setProxy(proxy);
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium Drivers\\chromedriver.exe");
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
		api = new ClientApi(ZAP_PROXY_ADDRESS, ZAP_PROXY_PORT, ZAP_API_KEY);
		
	}
	
	@Test
	public void amazonSecurityTest() {
		driver.get("https://www.flipkart.com");
		//Assert.assertTrue(driver.getTitle().contains("Amazon"));
	}

	@AfterMethod
	public void tearDown() {
		if (api != null) {
			String title = "flipkart ZAP Security Report";
			String template = "traditional-html";
			String discription = "This is flipkart zap security test report";
			String reportfilename = "flipkart-zap-report.html";
			String targetFolder = System.getProperty("user.dir");
			
			try {
				ApiResponse response = api.reports.generate(title, template, null, discription, null, null, null, 
						null, null, reportfilename, null, targetFolder, null);
				System.out.println("ZAP report generated at this location : " + response.toString());
			} catch (ClientApiException e) {
				e.printStackTrace();
			}
		}
		driver.quit();
	}
}
