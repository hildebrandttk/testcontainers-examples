package tk.hildebrandt.testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static junit.framework.TestCase.assertFalse;

public class JUnit4PerTestSuite2 extends AbstractJUnit4PerTestSuite {
   @BeforeClass
   public static void startContainers() {
      AbstractJUnit4PerTestSuite.startContainer();
   }

   @Test
   public void createUser() throws SQLException {
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
