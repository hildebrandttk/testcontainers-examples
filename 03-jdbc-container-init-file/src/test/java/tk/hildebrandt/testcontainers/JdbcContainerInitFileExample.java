package tk.hildebrandt.testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

public class JdbcContainerInitFileExample {

   private static final Logger LOG = LoggerFactory.getLogger(
      JdbcContainerInitFileExample.class);
   private static final String SCHEMA_SQL = "create-user-schema.sql";

   public static void main(String[] args) {
      try {
         runContainer();
      } catch (Exception ignore) {
         //
      }
   }

   private static void runContainer() throws Exception {
      JdbcDatabaseContainer postgresContainer =
         new PostgreSQLContainer("postgres:12");
      //TODO add test data on startup
      postgresContainer.start();
      printUserTablesContent(postgresContainer.getJdbcUrl(),
         postgresContainer.getUsername(), postgresContainer.getPassword());
      postgresContainer.stop();
   }

   private static void printUserTablesContent(String jdbcUrl, String user,
                                              String password)
      throws SQLException {
      try (Connection connection = DriverManager
         .getConnection(jdbcUrl, user, password)) {
         PreparedStatement preparedStatement =
            connection.prepareStatement("select ID, LAST_NAME, FIRST_NAME from users");
         try(ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
               LOG.info("User Data {} - {} {}", resultSet.getInt("ID"), resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"));
            }
         }
      }
   }
}
