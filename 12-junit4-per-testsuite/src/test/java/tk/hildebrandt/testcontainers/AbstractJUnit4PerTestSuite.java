package tk.hildebrandt.testcontainers;

import org.junit.ClassRule;
import org.testcontainers.containers.PostgreSQLContainer;

public class AbstractJUnit4PerTestSuite {
   @ClassRule
   public static PostgreSQLContainer POSTGRESQL_CONTAINER =
      new PostgreSQLContainer("postgres:11-userdb")
         .withDatabaseName("userdb")
         .withUsername("userdb")
         .withPassword("test1234");

}
