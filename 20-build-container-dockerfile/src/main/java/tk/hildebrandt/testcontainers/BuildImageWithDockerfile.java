package tk.hildebrandt.testcontainers;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class BuildImageWithDockerfile {
   private static final String DOCKERFILE = "src/main/docker/Dockerfile";
   private static final Logger LOG = LoggerFactory.getLogger(
      BuildImageWithDockerfile.class);
   private static final int POSTGRES_PORT = 5432;
   private static final String POSTGRES_PASSWORD = "test1234";
   private static final String POSTGRES_USER = "userdb";

   public static void main(String[] args) {
      try {
         runContainer();
      } catch (Exception ignore) {
         //
      }
   }

   private static void runContainer() throws Exception {
      POSTGRES_SQL_CONTAINER.start();
      POSTGRES_SQL_CONTAINER.stop();
   }

   private static GenericContainer POSTGRES_SQL_CONTAINER =
      new GenericContainer<>(
         new ImageFromDockerfile()
            .withDockerfile(new File(DOCKERFILE).toPath())
      )
         .withExposedPorts(5432);

   @Test
   void createUser() throws SQLException {
      Connection connection = getConnection();
      try (PreparedStatement preparedStatement =
              connection.prepareStatement(
                 "insert into USERS(ID, LAST_NAME, FIRST_NAME) values (?,?,?)")) {
         preparedStatement.setString(1, "1");
         preparedStatement.setString(2, "Wurst");
         preparedStatement.setString(3, "Hans");
         assertFalse(preparedStatement.execute());
         Assertions.assertEquals(1, preparedStatement.getUpdateCount());
      }
   }

   @Test
   void selectExistingUser() throws SQLException {
      try (Connection connection = getConnection()) {
         try (PreparedStatement preparedStatement =
                 connection.prepareStatement(
                    "select ID, LAST_NAME, FIRST_NAME from USERS where ID=?")) {
            preparedStatement.setString(1, "666");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
               assertTrue(resultSet.next());
               assertEquals("Wurst", resultSet.getString("LAST_NAME"));
               assertEquals("Conchita", resultSet.getString("FIRST_NAME"));
            }
         }
      }
   }

   private Connection getConnection() throws SQLException {
      return DriverManager
         .getConnection(generateJdbcUrl(), "userdb", "test1234");
   }

   private String generateJdbcUrl() {
      String serviceHost = POSTGRES_SQL_CONTAINER.getContainerIpAddress();
      Integer servicePort = POSTGRES_SQL_CONTAINER.getMappedPort(5432);
      return String
         .format("jdbc:postgresql://%s:%d/userdb?loggerLevel=OFF", serviceHost,
            servicePort);
   }
}
