package tk.hildebrandt.testcontainers;

import org.junit.BeforeClass;
import org.testcontainers.containers.PostgreSQLContainer;

class AbstractJUnit4PerTestSuite {
   static PostgreSQLContainer POSTGRESQL_CONTAINER =
      new PostgreSQLContainer("postgres:11-userdb")
         .withDatabaseName("userdb")
         .withUsername("userdb")
         .withPassword("test1234");

   protected static void startContainer() {
      POSTGRESQL_CONTAINER.start();
      //GenericContainer implements AutoCloseable, so no need to call stop() afterwards
   }
}
