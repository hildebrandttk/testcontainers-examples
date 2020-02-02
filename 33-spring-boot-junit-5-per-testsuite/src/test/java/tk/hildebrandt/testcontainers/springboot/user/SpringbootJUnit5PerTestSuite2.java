package tk.hildebrandt.testcontainers.springboot.user;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import tk.hildebrandt.testcontainers.springboot.config.DataRepositoryConfiguration;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {DatabaseTestConfiguration.class,
   DataRepositoryConfiguration.class})
class SpringbootJUnit5PerTestSuite2
   extends AbstractSpringbootJUnit5PerTestSuiteSuperClass {

   @Autowired
   private UserRepository userRepository;

   @Test
   void selectExistingUser() {
      List<User> byFirstNameAndLastName =
         userRepository.findByFirstNameAndLastName("Conchita", "Wurst");
      assertEquals(1, byFirstNameAndLastName.size());
   }
}
