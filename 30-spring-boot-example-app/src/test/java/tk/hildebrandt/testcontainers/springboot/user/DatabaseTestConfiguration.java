package tk.hildebrandt.testcontainers.springboot.user;

import javax.sql.DataSource;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@TestConfiguration
@EnableJdbcRepositories
public class DatabaseTestConfiguration {

   @Bean
//TODO this test didn't use test containers
   DataSource dataSource() {
      return new EmbeddedDatabaseBuilder()
         .generateUniqueName(true)
         .setType(EmbeddedDatabaseType.H2)
         .addScript("create-user-schema.sql")
         .build();
   }
}
