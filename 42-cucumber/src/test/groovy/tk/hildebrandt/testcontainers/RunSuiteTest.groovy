package tk.hildebrandt.testcontainers

import io.cucumber.testng.AbstractTestNGCucumberTests
import io.cucumber.testng.CucumberOptions
import io.cucumber.testng.FeatureWrapper
import io.cucumber.testng.PickleWrapper
import org.slf4j.bridge.SLF4JBridgeHandler
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

@CucumberOptions(
   plugin = ["pretty", "json:out/report/cucumber2.json", "de.monochromata.cucumber.report.PrettyReports:out/cucumber"],
   features = ["src/test/resources/"],
   glue = ["tk.hildebrandt.testcontainers"]
)
class RunSuiteTest extends AbstractTestNGCucumberTests {
   //TODO if you get testng dtd errors, add "-Dtestng.dtd.http=true" to the command line
   static {
      //redirect JUL Logs to Logback
      SLF4JBridgeHandler.removeHandlersForRootLogger();
      SLF4JBridgeHandler.install()
   }

   @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios", threadPoolSize = 2)
   void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
      super.runScenario(pickleWrapper, featureWrapper)
   }

   @Override
   @DataProvider(parallel = true)
   Object[][] scenarios() {
      return super.scenarios();
   }
}