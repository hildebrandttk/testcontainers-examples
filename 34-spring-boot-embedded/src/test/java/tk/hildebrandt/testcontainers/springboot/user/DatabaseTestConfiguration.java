package tk.hildebrandt.testcontainers.springboot.user;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

/**
https://github.com/Playtika/testcontainers-spring-boot
*/
@TestConfiguration
@EnableJdbcRepositories
public class DatabaseTestConfiguration {
   //TODO enable spring cloud contract for testing
//   @Value("jdbc:postgresql://${embedded.postgresql.host}:${embedded.postgresql.port}/${embedded.postgresql.schema}")
   private String jdbcUrl;
   //   @Value("${embedded.postgresql.user}")
   private String user;
   //   @Value("${embedded.postgresql.password}")
   private String password;

   @Bean
   DataSource dataSource() {
      return DataSourceBuilder.create()
         .username(user)
         .password(password)
         .url(jdbcUrl)
         .build();
   }
}
