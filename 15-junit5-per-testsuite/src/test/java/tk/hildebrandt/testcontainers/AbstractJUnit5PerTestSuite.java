package tk.hildebrandt.testcontainers;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

class AbstractJUnit5PerTestSuite {
   @Container
   static PostgreSQLContainer POSTGRESQL_CONTAINER =
      new PostgreSQLContainer("postgres:11-userdb")
         .withDatabaseName("userdb")
         .withUsername("userdb")
         .withPassword("test1234");
}
