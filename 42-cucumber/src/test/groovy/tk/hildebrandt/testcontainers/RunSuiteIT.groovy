package tk.hildebrandt.testcontainers

import io.cucumber.testng.AbstractTestNGCucumberTests
import io.cucumber.testng.CucumberOptions
import org.testng.annotations.DataProvider

@CucumberOptions(
   plugin = ["pretty", "json:out/report/cucumber2.json", "de.monochromata.cucumber.report.PrettyReports:out/cucumber"],
   strict = true,
   features = ["src/test/resources/"],
   glue = ["tk.hildebrandt.testcontainers"]
)
class RunSuiteIT extends AbstractTestNGCucumberTests {

   @Override
   @DataProvider(parallel = true)
   public Object[][] scenarios() {
      return super.scenarios();
   }
}