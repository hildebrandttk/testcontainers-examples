package tk.hildebrandt.testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@Testcontainers
class JUnit5PerTestSuite1 extends AbstractJUnit5PerTestSuite {

   @Test
   void selectExistingUser() throws SQLException {
      Connection connection = DriverManager
         .getConnection(POSTGRESQL_CONTAINER.getJdbcUrl(),
            POSTGRESQL_CONTAINER.getUsername(),
            POSTGRESQL_CONTAINER.getPassword());
      try (PreparedStatement preparedStatement =
              connection.prepareStatement(
                 "select ID, LAST_NAME, FIRST_NAME from USERS where ID=?")) {
         preparedStatement.setString(1, "666");
         ResultSet resultSet = preparedStatement.executeQuery();
         assertTrue(resultSet.next());
         assertEquals("Wurst", resultSet.getString("LAST_NAME"));
         assertEquals("Conchita", resultSet.getString("FIRST_NAME"));
      }
   }
}
