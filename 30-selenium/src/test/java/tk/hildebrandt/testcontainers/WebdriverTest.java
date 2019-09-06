package tk.hildebrandt.testcontainers;

import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testcontainers.containers.BrowserWebDriverContainer;

import static junit.framework.TestCase.assertEquals;

public class WebdriverTest {

   @ClassRule
   public static BrowserWebDriverContainer CHROME =
      new BrowserWebDriverContainer()
         .withCapabilities(new ChromeOptions());

   @Test
   public void searchBedCon() {
      RemoteWebDriver webDriver = CHROME.getWebDriver();
      webDriver.get("https://www.bing.com");
      WebDriverWait wait = new WebDriverWait(webDriver, 60);
      wait.until(ExpectedConditions.elementToBeClickable(By.id("sb_form_q")));
      WebElement searchField = webDriver.findElement(By.id("sb_form_q"));
      searchField.sendKeys("bed con");
      webDriver.findElement(By.id("sb_form_go")).click();
      WebElement bedConLink =
         webDriver.findElement(By.cssSelector("h2.b_topTitle a"));
      assertEquals("http://bed-con.org/", bedConLink.getAttribute("href"));
   }
}
