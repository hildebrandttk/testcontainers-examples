package tk.hildebrandt.testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static junit.framework.TestCase.assertFalse;

class JUnit5PerTestSuite2 extends AbstractJUnit5PerTestSuite {

   @Test
   void createUser() throws SQLException {
      try (Connection connection = DriverManager
         .getConnection(POSTGRESQL_CONTAINER.getJdbcUrl(),
            POSTGRESQL_CONTAINER.getUsername(),
            POSTGRESQL_CONTAINER.getPassword())) {
         try (PreparedStatement preparedStatement =
                 connection.prepareStatement(
                    "insert into USERS(ID, LAST_NAME, FIRST_NAME) values (?,?,?)")) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Wurst");
            preparedStatement.setString(3, "Hans");
            assertFalse(preparedStatement.execute());
            assertEquals(1, preparedStatement.getUpdateCount());
         }
      }
   }
}
