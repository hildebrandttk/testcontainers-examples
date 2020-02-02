package tk.hildebrandt.testcontainers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@Testcontainers
class JdbcDriverPerSuite {

   private static final String JDBC_URL = "jdbc:tc:postgresql:11:///userdb?TC_INITSCRIPT=create-user-schema.sql";
   private static final String USERNAME = "test";
   private static final String PASSWORD = "test";

   private static BasicDataSource DATA_SOURCE_POOL = new BasicDataSource();

   @BeforeAll
   static void setupDatasourcePool() {
      DATA_SOURCE_POOL.setUsername(USERNAME);
      DATA_SOURCE_POOL.setPassword(PASSWORD);
      DATA_SOURCE_POOL.setUrl(JDBC_URL);
      DATA_SOURCE_POOL.setMinIdle(1);
//      DATA_SOURCE.setMaxIdle(0);
   }

   @Test
   void createUser() throws SQLException {
      try (Connection connection = DATA_SOURCE_POOL.getConnection()) {
         try (PreparedStatement preparedStatement =
                 connection.prepareStatement(
                    "insert into USERS(ID, LAST_NAME, FIRST_NAME) values (?,?,?)")) {
            preparedStatement.setString(1, "2");
            preparedStatement.setString(2, "Wurst");
            preparedStatement.setString(3, "Hans");
            assertFalse(preparedStatement.execute());
            Assertions.assertEquals(1, preparedStatement.getUpdateCount());
         }
      }
   }

   @Test
   void selectExistingUser() throws SQLException {
      try (Connection connection = DATA_SOURCE_POOL.getConnection()) {
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
