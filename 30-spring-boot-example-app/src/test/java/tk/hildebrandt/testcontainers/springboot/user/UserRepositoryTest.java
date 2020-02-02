package tk.hildebrandt.testcontainers.springboot.user;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import tk.hildebrandt.testcontainers.springboot.config.DataRepositoryConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {DatabaseTestConfiguration.class,
   DataRepositoryConfiguration.class})
public class UserRepositoryTest {

   @Autowired
   private UserRepository userRepository;

   @Test
   public void createUser() {
      User hansWurst = new User()
         .withFirstName("Hans")
         .withLastName("Wurst");
      userRepository.save(hansWurst);
      assertNotNull(hansWurst.getId());
   }

   @Test
   public void selectExistingUser() {
      List<User> byFirstNameAndLastName =
         userRepository.findByFirstNameAndLastName("Conchita", "Wurst");
      assertEquals(1, byFirstNameAndLastName.size());
   }
}
