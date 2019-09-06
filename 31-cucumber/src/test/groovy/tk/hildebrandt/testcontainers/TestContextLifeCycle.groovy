package tk.hildebrandt.testcontainers

import io.cucumber.core.api.Scenario
import io.cucumber.java.After
import io.cucumber.java.Before

class TestContextLifeCycle {

   private TestContext testContext

   TestContextLifeCycle(TestContext testContext) {
      this.testContext = testContext
   }

   @Before
   void setScenario(Scenario scenario) {
      testContext.scenario = scenario
   }

   @After
   void stopContainers() {
      testContext.adminerContainer.stop()
      testContext.adminerContainer = null
      testContext.databaseContainer.stop()
      testContext.databaseContainer = null
   }
}
