package tk.hildebrandt.testcontainers.springboot.user;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("USERS")
public class User {

   @Id
   private String id;

   private String firstName;

   private String lastName;

   public static void generateId(User user) {
      user.id = UUID.randomUUID().toString();
   }

   public User withFirstName(String firstName) {
      this.firstName = firstName;
      return this;
   }

   public User withLastName(String lastName) {
      this.lastName = lastName;
      return this;
   }

   public String getId() {
      return id;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getLastName() {
      return lastName;
   }
}
