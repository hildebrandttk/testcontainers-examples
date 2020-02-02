package tk.hildebrandt.testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

public class JdbcContainerExample {

   private static final Logger LOG = LoggerFactory.getLogger(
      JdbcContainerExample.class);

   public static void main(String[] args) {
      try {
         runContainer();
      } catch (Exception ignore) {
         //
      }
   }

   private static void runContainer() throws Exception {
      JdbcDatabaseContainer postgresContainer =
         new PostgreSQLContainer();
      postgresContainer.start();
      printDatabaseNameAndVersion(postgresContainer.getJdbcUrl(),
         postgresContainer.getUsername(), postgresContainer.getPassword());
      postgresContainer.stop();
   }

   private static void printDatabaseNameAndVersion(String jdbcUrl1, String user,
                                                   String password)
      throws SQLException {
      String jdbcUrl = jdbcUrl1;
      try (Connection connection = DriverManager
         .getConnection(jdbcUrl, user, password)) {
         Object databaseProductName =
            connection.getMetaData().getDatabaseProductName();
         Object productVersion =
            connection.getMetaData().getDatabaseProductVersion();
         LOG.info("Greetings from {} in version {}", databaseProductName,
            productVersion);
      }
   }
}
