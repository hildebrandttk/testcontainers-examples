package tk.hildebrandt.testcontainers.springboot.user;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
@EnableJdbcRepositories
public class DatabaseTestConfiguration {

   //TODO create bean

   @Bean
   DataSource dataSource() {
      return DataSourceBuilder
         .create()
//         .url(postgreSQLContainer.getJdbcUrl())
//         .username(
//            postgreSQLContainer.getUsername())
//         .password(
//            postgreSQLContainer.getPassword())
         .build();
   }
}
