package tk.hildebrandt.testcontainers.springboot.config;

import javax.sql.DataSource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import tk.hildebrandt.testcontainers.springboot.user.User;

@Configuration
@EnableJdbcRepositories
public class DataRepositoryConfiguration extends AbstractJdbcConfiguration {

   @Bean
   NamedParameterJdbcOperations operations(DataSource dataSource) {
      return new NamedParameterJdbcTemplate(dataSource);
   }

   @Bean
   PlatformTransactionManager transactionManager(DataSource dataSource) {
      return new DataSourceTransactionManager(dataSource);
   }

   @Bean
   public ApplicationListener<BeforeSaveEvent> idSetting() {

      return event -> {

         if (event.getEntity() instanceof User) {

            User user = (User) event.getEntity();
            if (user.getId() == null) {
               User.generateId(user);
            }
         }
      };
   }
}
