package tk.hildebrandt.testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class JUnit4PerTestClass {

   @Rule
   //TODO start only once for test class
   public PostgreSQLContainer postgreSQLContainer =
      new PostgreSQLContainer("postgres:11-userdb");

   @Test
   public void createUser() throws SQLException {
      try (Connection connection = DriverManager
         .getConnection(postgreSQLContainer.getJdbcUrl(),
            postgreSQLContainer.getUsername(),
            postgreSQLContainer.getPassword())) {
         try (PreparedStatement preparedStatement =
                 connection.prepareStatement(
                    "INSERT INTO users_table(id, last_name, first_name) VALUES (?,?,?)")) {
            preparedStatement.setString(1, "1");
            preparedStatement.setString(2, "Wurst");
            preparedStatement.setString(3, "Hans");
            assertFalse(preparedStatement.execute());
            assertEquals(1, preparedStatement.getUpdateCount());
         }
      }
   }

   @Test
   public void selectExistingUser() throws SQLException {
      try (Connection connection = DriverManager
         .getConnection(postgreSQLContainer.getJdbcUrl(),
            postgreSQLContainer.getUsername(),
            postgreSQLContainer.getPassword())) {
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
