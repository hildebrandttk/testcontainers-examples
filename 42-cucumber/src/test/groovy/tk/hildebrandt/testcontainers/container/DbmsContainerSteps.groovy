package tk.hildebrandt.testcontainers.container

import io.cucumber.java.en.Given
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import tk.hildebrandt.testcontainers.DatabaseType
import tk.hildebrandt.testcontainers.TestContext

class DbmsContainerSteps {

   static Logger LOG = LoggerFactory.getLogger(DbmsContainerSteps)

   TestContext testContext

   DbmsContainerSteps(TestContext testContext) {
      this.testContext = testContext
   }

   @Given('^A running database instance of type (postgres|mysql) with version (latest|[0-9.]*)$')
   void startDatabaseContainer(String type, String version) {
      testContext.dbmsType = DatabaseType.byDockerImageName(type)
      switch (testContext.dbmsType) {
         case DatabaseType.POSTGRES:
            testContext.databaseContainer =
               new PostgreSQLContainer("${type}:${version}")
            break
         case DatabaseType.MYSQL:
            testContext.databaseContainer =
               new MySQLContainer("${type}:${version}")
            break
         default:
            throw new IllegalArgumentException("Not supported dbms ${type}")
      }
      testContext.databaseContainer
         .withUsername("adminertest")
         .withPassword("test1234")
         .withDatabaseName("adminertest")
         .withNetwork(testContext.network)
         .withNetworkAliases(testContext.dbmsType.getNetworkAlias())
         .withLogConsumer(new Slf4jLogConsumer(LOG))
         .start()
   }
}
