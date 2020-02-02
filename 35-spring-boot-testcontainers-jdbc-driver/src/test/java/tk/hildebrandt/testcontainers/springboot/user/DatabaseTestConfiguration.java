package tk.hildebrandt.testcontainers.springboot.user;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@TestConfiguration
@EnableJdbcRepositories
public class DatabaseTestConfiguration {

   private static final String JDBC_URL = "jdbc:tc:postgresql:11:///userdb?TC_INITSCRIPT=create-user-schema.sql";
   private static final String USER = "test";
   private static final String PASSWORD = "test";

   @Bean
   DataSource dataSource() {
      return DataSourceBuilder.create()
         .username(USER)
         .password(PASSWORD)
         .url(JDBC_URL)
         .build();
   }
}
