package tk.hildebrandt.testcontainers;

import java.io.File;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testcontainers.containers.BrowserWebDriverContainer;

import static junit.framework.TestCase.assertEquals;

public class WebdriverRecordingTest {

   @Rule
   public BrowserWebDriverContainer CHROME =
//      TODO enable recoding
      new BrowserWebDriverContainer()
         .withCapabilities(new ChromeOptions());

   @Test
   public void searchConferenceOnBing() {
      RemoteWebDriver webDriver = CHROME.getWebDriver();
      webDriver.get("https://www.bing.com");
      WebDriverWait wait = new WebDriverWait(webDriver, 10);
      wait.until(ExpectedConditions.elementToBeClickable(By.id("sb_form_q")));
      WebElement searchField = webDriver.findElement(By.id("sb_form_q"));
      searchField.sendKeys("OOP Konferenz");
      webDriver.findElement(By.cssSelector("label[for=sb_form_go]")).click();
      WebElement conferenceLink =
         webDriver.findElement(By.cssSelector("li.b_algo h2 a"));
      assertEquals("https://www.oop-konferenz.de/", conferenceLink.getAttribute("href"));
   }
}
