package tk.hildebrandt.testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

public class PlainDatabaseExample {

   private static final Logger LOG = LoggerFactory.getLogger(
      PlainDatabaseExample.class);
   private static final int POSTGRES_PORT = 5432;
   private static final String POSTGRES_PASSWORD = "test1234";
   private static final String POSTGRES_USER = "test";

   public static void main(String[] args) {
      try {
         runContainer();
      } catch (Exception e) {
         LOG.error("Error running container", e);
      }
   }

   @SuppressWarnings("unchecked")
   private static void runContainer() throws Exception {
      GenericContainer genericContainer = new GenericContainer("postgres:12");
      genericContainer.withLogConsumer(new Slf4jLogConsumer(LOG));
      genericContainer.addEnv("POSTGRES_PASSWORD", POSTGRES_PASSWORD);
      genericContainer.addEnv("POSTGRES_USER", POSTGRES_USER);
      genericContainer.addExposedPort(POSTGRES_PORT);
      genericContainer.start();
      String jdbcUrl = generateJdbcUrl(genericContainer);
      printDatabaseNameAndVersion(jdbcUrl, POSTGRES_USER, POSTGRES_PASSWORD);
      genericContainer.stop();
   }

   @SuppressWarnings("unchecked")
   private static String generateJdbcUrl(
      GenericContainer genericContainer) {
      Integer servicePort = genericContainer.getMappedPort(POSTGRES_PORT);
      return String
         .format("jdbc:postgresql://localhost:%d/?loggerLevel=OFF",
            servicePort);
   }

   private static void printDatabaseNameAndVersion(String jdbcUrl, String user,
                                                   String password) throws SQLException {
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
