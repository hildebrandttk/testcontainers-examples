package tk.hildebrandt.testcontainers.springboot.user;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@TestConfiguration
@EnableJdbcRepositories
public class DatabaseTestConfiguration {

   @Bean
   DataSource dataSource() {
      return DataSourceBuilder
         .create()
         .url(AbstractSpringbootJUnit5PerTestSuiteSuperClass.POSTGRES_SQL_CONTAINER.getJdbcUrl())
         .username(
            AbstractSpringbootJUnit5PerTestSuiteSuperClass.POSTGRES_SQL_CONTAINER.getUsername())
         .password(
            AbstractSpringbootJUnit5PerTestSuiteSuperClass.POSTGRES_SQL_CONTAINER.getPassword())
         .build();
   }
}
