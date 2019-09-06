package tk.hildebrandt.testcontainers.springboot.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import tk.hildebrandt.testcontainers.springboot.config.DataRepositoryConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {DatabaseTestConfiguration.class,
   DataRepositoryConfiguration.class})
class SpringbootJUnit5PerTestSuite1
   extends AbstractSpringbootJUnit5PerTestSuiteSuperClass {

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
}
