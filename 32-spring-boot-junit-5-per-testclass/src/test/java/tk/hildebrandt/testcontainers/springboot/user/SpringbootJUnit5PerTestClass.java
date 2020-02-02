package tk.hildebrandt.testcontainers.springboot.user;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import tk.hildebrandt.testcontainers.springboot.config.DataRepositoryConfiguration;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = {DatabaseTestConfiguration.class,
   DataRepositoryConfiguration.class})
class SpringbootJUnit5PerTestClass {

   @Container
   static PostgreSQLContainer POSTGRES_SQL_CONTAINER =
      new PostgreSQLContainer("postgres:11-userdb");

   @Autowired
   private UserRepository userRepository;

   @Test
   void createUser() {
      User hansWurst = new User()
         .withFirstName("Hans")
         .withLastName("Wurst");
      userRepository.save(hansWurst);
      assertNotNull(hansWurst.getId());
   }

   @Test
   void selectExistingUser() {
      List<User> byFirstNameAndLastName =
         userRepository.findByFirstNameAndLastName("Conchita", "Wurst");
      assertEquals(1, byFirstNameAndLastName.size());
   }
}
