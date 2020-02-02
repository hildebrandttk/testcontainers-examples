package tk.hildebrandt.testcontainers.geb

import io.cucumber.java.After
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.testcontainers.lifecycle.TestDescription
import tk.hildebrandt.testcontainers.TestContext

class GebLifeCycle {

   private TestContext testContext;

   GebLifeCycle(TestContext testContext) {
      this.testContext = testContext
   }

   @After(order = 1)
   public void closeBrowser() {
      if (testContext.scenario.failed) {
         byte[] data = ((TakesScreenshot) testContext.
            getWebDriverContainer().webDriver).
            getScreenshotAs(OutputType.BYTES);
         testContext.scenario.embed(data, "image/png")
         //use for development
         new File(new File('.').getAbsoluteFile(), 'out/error.png').
            withOutputStream { stream ->
               stream.write(data)
            }
      }
      def videoFileName = testContext.scenario.getName().replace(' ', '_')
      testContext.getWebDriverContainer().afterTest(new TestDescription() {
         @Override
         String getTestId() {
            return testContext.scenario.getName()
         }

         @Override
         String getFilesystemFriendlyName() {
            return videoFileName
         }
      }, Optional.empty())
      testContext.webDriverContainer.stop()
      testContext.scenario.embed(new String(
         "<video width=\"864\" height=\"576\" controls><source src=\"../${videoFileName}.mp4\" type=\"video/mp4\">Your browser does not support the video tag.</video>").
         getBytes(), 'text/html')
      testContext.webDriverContainer = null
   }
}
