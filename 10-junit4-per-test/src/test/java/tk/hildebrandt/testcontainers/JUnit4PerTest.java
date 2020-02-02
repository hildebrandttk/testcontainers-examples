package tk.hildebrandt.testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class JUnit4PerTest {

   @Rule
   public PostgreSQLContainer postgreSQLContainer =
      //to create this image run docker/build.(sh|bat)
      new PostgreSQLContainer("postgres:11-userdb")
         .withDatabaseName("hans")
         .withPassword("klaus")
         .withUsername("peter");

   @Test
   public void createUser() throws SQLException {
      try (Connection connection = DriverManager
         .getConnection(postgreSQLContainer.getJdbcUrl(),
            postgreSQLContainer.getUsername(),
            postgreSQLContainer.getPassword())) {
         try (PreparedStatement preparedStatement =
                 connection.prepareStatement(
                    "insert into USERS(ID, LAST_NAME, FIRST_NAME) values (?,?,?)")) {
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
}
