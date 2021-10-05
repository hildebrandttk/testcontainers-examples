package tk.hildebrandt.testcontainers.geb

import geb.waiting.DefaultWaitingSupport
import io.cucumber.java.After
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.lifecycle.TestDescription
import tk.hildebrandt.testcontainers.TestContext

class GebLifeCycle {

   private static final Logger LOG = LoggerFactory.getLogger(GebLifeCycle.class)

   private TestContext testContext

   GebLifeCycle(TestContext testContext) {
      this.testContext = testContext
   }

   @After(order = 1)
   void closeBrowser() {
      if (testContext.scenario.failed) {
         byte[] data = ((TakesScreenshot) testContext.
            getWebDriverContainer().webDriver).
            getScreenshotAs(OutputType.BYTES);
         testContext.scenario.attach(data, "image/png", "Browser exit")
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
      testContext.scenario.attach(new String(
         "<video width=\"864\" height=\"576\" controls><source src=\"../${videoFileName}.mp4\" type=\"video/mp4\">Your browser does not support the video tag.</video>"),
         'text/html', "video")
      testContext.webDriverContainer = null
   }
}
