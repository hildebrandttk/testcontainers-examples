package tk.hildebrandt.testcontainers;

import java.io.File;
import java.nio.file.Path;
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

@Testcontainers
class BuildContainerWithDslTest {

   private static final String CREATE_USER_SCHEMA_SQL =
      "create-user-schema.sql";
   private static final String DOCKER_ENTRYPOINT_INITDB =
      "/docker-entrypoint-initdb.d";
   private static final Path DOCKER_BASE_PATH =
      new File("src/test/docker").toPath();

   @Container
   private static GenericContainer POSTGRE_SQL_CONTAINER =
      new GenericContainer<>(
         new ImageFromDockerfile()
            .withFileFromPath(".", DOCKER_BASE_PATH)
            .withDockerfileFromBuilder(
               builder -> builder
                  .from("postgres")
                  .add(CREATE_USER_SCHEMA_SQL, DOCKER_ENTRYPOINT_INITDB))
      )
         .withEnv("POSTGRES_DB", "userdb")
         .withEnv("POSTGRES_USER", "userdb")
         .withEnv("POSTGRES_PASSWORD", "test1234")
         .withExposedPorts(5432);
   ;

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
      String serviceHost = POSTGRE_SQL_CONTAINER.getContainerIpAddress();
      Integer servicePort = POSTGRE_SQL_CONTAINER.getMappedPort(5432);
      return String
         .format("jdbc:postgresql://%s:%d/userdb?loggerLevel=OFF",
            serviceHost, servicePort);
   }
}
