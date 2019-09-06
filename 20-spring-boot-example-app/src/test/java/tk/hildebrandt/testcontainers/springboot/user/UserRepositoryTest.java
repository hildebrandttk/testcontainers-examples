package tk.hildebrandt.testcontainers.springboot.user;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import tk.hildebrandt.testcontainers.springboot.config.DataRepositoryConfiguration;

import static junit.framework.TestCase.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = {DatabaseTestConfiguration.class,
   DataRepositoryConfiguration.class})
class UserRepositoryTest {

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
