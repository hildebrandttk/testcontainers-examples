package tk.hildebrandt.testcontainers.geb

import geb.Browser
import geb.Configuration
import geb.ConfigurationLoader
import geb.Page
import org.openqa.selenium.chrome.ChromeOptions
import org.slf4j.LoggerFactory
import org.testcontainers.containers.BrowserWebDriverContainer
import org.testcontainers.containers.VncRecordingContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import tk.hildebrandt.testcontainers.TestContext

class GebEnabledStep {

   public static final String RECORDING_DIR = "out/cucumber"
   String gebConfEnv = null
   String gebConfScript = null

   TestContext testContext

   GebEnabledStep(TestContext testContext) {
      this.testContext = testContext
   }

   protected Browser browser

   protected <T extends Page> T to(Map params = [:], Class<T> pageType,
                                   Object[] args) {
      return getBrowser().to(params, pageType, args)
   }

   Configuration createConf() {
      def configuration = new ConfigurationLoader(gebConfEnv, System.properties,
         new GroovyClassLoader(getClass().classLoader)).getConf(gebConfScript)
      testContext.configuration = configuration
      configuration
   }

   Browser createBrowser() {
      testContext.webDriverContainer = new BrowserWebDriverContainer()
         .withCapabilities(new ChromeOptions().addArguments("--disable-dev-shm-usage"))
         .withRecordingMode(
            BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL,
            new File(RECORDING_DIR),
            VncRecordingContainer.VncRecordingFormat.MP4)
         .withRecordingFileFactory(new CustomRecordingFileFactory())
         .withSharedMemorySize(2147483648L) as BrowserWebDriverContainer
      testContext.webDriverContainer.withNetwork(testContext.network)
      testContext.webDriverContainer.start()
      new Browser(createConf())
   }

   Browser getBrowser() {
      if (browser == null) {
         browser = createBrowser()
      }
      browser
   }

   def <T extends Page> T continueAt(Class<T> pageClass){
      browser.at(pageClass)
   }

   def methodMissing(String name, args) {
      getBrowser()."$name"(*args)
   }

   def propertyMissing(String name) {
      getBrowser()."$name"
   }

   void propertyMissing(String name, value) {
      getBrowser()."$name" = value
   }
}
