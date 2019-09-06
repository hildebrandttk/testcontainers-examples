package tk.hildebrandt.testcontainers.springboot.todo;

import org.springframework.data.annotation.Id;

public class ToDoItem {

   @Id
   Long id;
   String firstName;
   String lastName;

   public ToDoItem withFirstName(String firstName) {
      this.firstName = firstName;
      return this;
   }

   public ToDoItem withLastName(String lastName) {
      this.lastName = lastName;
      return this;
   }
}
