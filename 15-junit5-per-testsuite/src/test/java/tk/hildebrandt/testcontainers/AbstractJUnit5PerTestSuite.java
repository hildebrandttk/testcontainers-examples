package tk.hildebrandt.testcontainers;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

class AbstractJUnit5PerTestSuite {
   static PostgreSQLContainer POSTGRESQL_CONTAINER =
      new PostgreSQLContainer("postgres:11-userdb")
         .withDatabaseName("userdb")
         .withUsername("userdb")
         .withPassword("test1234");

   static void startContainer() {
      POSTGRESQL_CONTAINER.start();
      //GenericContainer implements AutoCloseable, so no need to call stop() afterwards
   }
}
