package tk.hildebrandt.testcontainers;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@Testcontainers
class JUnit5DockerComposeTest {
   private static final int POSTGRESS_PORT = 5432;
   private static final String POSTGRES_SERVICE_NAME = "db_1";
   private static final File DOCKER_COMPOSE =
      new File("docker-compose-userdb.yml");
   @Container
   //TODO 01 explain compose syntax
   private static final DockerComposeContainer COMPOSE_CONTAINER =
      new DockerComposeContainer(DOCKER_COMPOSE)
         .withLocalCompose(true)
         .withExposedService(POSTGRES_SERVICE_NAME, POSTGRESS_PORT);

   private Connection createConnection() throws SQLException {
      return DriverManager
         .getConnection(generateJdbcUrl(), "userdb", "test1234");
   }

   private String generateJdbcUrl() {
      String serviceHost = COMPOSE_CONTAINER
         .getServiceHost(POSTGRES_SERVICE_NAME, POSTGRESS_PORT);
      Integer servicePort = COMPOSE_CONTAINER
         .getServicePort(POSTGRES_SERVICE_NAME, POSTGRESS_PORT);
      return String
         .format("jdbc:postgresql://%s:%d/userdb?loggerLevel=OFF",
            serviceHost, servicePort);
   }

   @Test
   void createUser() throws SQLException {
      try (Connection connection = createConnection()) {
         try (PreparedStatement preparedStatement =
                 connection.prepareStatement(
                    "INSERT INTO users_table(id, last_name, first_name) VALUES (?,?,?)")) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Wurst");
            preparedStatement.setString(3, "Hans");
            assertFalse(preparedStatement.execute());
            Assertions.assertEquals(1, preparedStatement.getUpdateCount());
         }
      }
   }

   @Test
   void selectExistingUser() throws SQLException {
      try (Connection connection = createConnection()) {
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
}
