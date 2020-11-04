package tk.hildebrandt.testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

public class PlainDatabaseExample {

   private static final Logger LOG = LoggerFactory.getLogger(
      PlainDatabaseExample.class);
   private static final int POSTGRES_PORT = 5432;
   private static final String POSTGRES_PASSWORD = "test1234";
   private static final String POSTGRES_USER = "test";
   public static final String IMAGE_NAME = "postgres";
   public static final String POSTGRES_STARTED_LOG_MESSAGE = ".*IPv6.*address.*";

   public static void main(String[] args) {
      try {
         runContainer();
      } catch (Exception e) {
         LOG.error("Error running container", e);
      }
   }

   @SuppressWarnings("unchecked")
   private static void runContainer() throws Exception {
      GenericContainer genericContainer = new GenericContainer("postgres:11")
         .withLogConsumer(new Slf4jLogConsumer(LOG))
         .withEnv("POSTGRES_PASSWORD", POSTGRES_PASSWORD)
         .withEnv("POSTGRES_USER", POSTGRES_USER)
         .withExposedPorts(POSTGRES_PORT);
      genericContainer.start();
      printDatabaseNameAndVersion(generateJdbcUrl(genericContainer), POSTGRES_USER, POSTGRES_PASSWORD);
      genericContainer.stop();
      //TODO create container
      //TODO start container
      //TODO stop container
      //TODO log container output
      //TODO configure container to
      //TODO print database name and version
   }

   @SuppressWarnings("unchecked")
   private static String generateJdbcUrl(
      GenericContainer genericContainer) {
//      TODO integrate port mapping
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
