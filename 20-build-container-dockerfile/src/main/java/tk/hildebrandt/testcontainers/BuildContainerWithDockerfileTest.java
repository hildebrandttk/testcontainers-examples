package tk.hildebrandt.testcontainers;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

//HINT building image on the fly without junit lifecycle didn't work
@Testcontainers
class BuildContainerWithDockerfileTest {

   private static final String DOCKERFILE = "src/main/docker/Dockerfile";

   @Container
   private static GenericContainer POSTGRES_SQL_CONTAINER =
      //TODO build image
      new GenericContainer<>("postgres:11-userdb")
         .withExposedPorts(5432);

   @Test
   void createUser() throws SQLException {
      Connection connection = getConnection();
      try (PreparedStatement preparedStatement =
              connection.prepareStatement(
                 "INSERT INTO users_table(id, last_name, first_name) VALUES (?,?,?)")) {
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
                    "SELECT id, last_name, first_name FROM users_table WHERE id=?")) {
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
