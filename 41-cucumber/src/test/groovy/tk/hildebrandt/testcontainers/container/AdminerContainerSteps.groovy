package tk.hildebrandt.testcontainers.container

import io.cucumber.java.en.Given
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import tk.hildebrandt.testcontainers.TestContext

class AdminerContainerSteps {

   static Logger LOG = LoggerFactory.getLogger(AdminerContainerSteps)

   TestContext testContext

   AdminerContainerSteps(TestContext testContext) {
      this.testContext = testContext
   }

   @Given('^a running instance of adminer$')
   public void startAdminerContainer() {
      testContext.adminerContainer = new GenericContainer('adminer')
         .withNetwork(testContext.network)
         .withNetworkAliases("adminer")
         .withExposedPorts(8080)
      testContext.adminerContainer.setWaitStrategy(
         Wait.forHttp("/all")
            .forStatusCode(200)
            .forStatusCode(401))
      testContext.adminerContainer.start()
      testContext.println "http://localhost:${testContext.adminerContainer.getMappedPort(8080)}/all"
//      sleep(30000)
   }
}
