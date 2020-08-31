package tk.hildebrandt.testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;

public class PlainDatabaseExample {

   private static final Logger LOG = LoggerFactory.getLogger(
      PlainDatabaseExample.class);
   private static final int POSTGRES_PORT = 5432;
   private static final String POSTGRES_PASSWORD = "test1234";
   private static final String POSTGRES_USER = "test";
   public static final String IMAGE_NAME = "postgres";

   public static void main(String[] args) {
      try {
         runContainer();
      } catch (Exception ignore) {
         //
      }
   }

   @SuppressWarnings("unchecked")
   private static void runContainer() throws Exception {
      //TODO create container
      //TODO start container
      //TODO stop container
      //TODO log container output
      //TODO configure container
      //TODO print database name and version
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
