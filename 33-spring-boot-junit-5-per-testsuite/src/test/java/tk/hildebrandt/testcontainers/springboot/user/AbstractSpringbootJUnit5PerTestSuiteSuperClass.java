package tk.hildebrandt.testcontainers.springboot.user;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

class AbstractSpringbootJUnit5PerTestSuiteSuperClass {

   static PostgreSQLContainer POSTGRES_SQL_CONTAINER =
      new PostgreSQLContainer("postgres:11-userdb");

   @BeforeAll
   //TODO combined example
   static void startContainer() {
      POSTGRES_SQL_CONTAINER.start();
      //GenericContainer implements AutoCloseable, so no need to call stop() afterwards
   }
}
