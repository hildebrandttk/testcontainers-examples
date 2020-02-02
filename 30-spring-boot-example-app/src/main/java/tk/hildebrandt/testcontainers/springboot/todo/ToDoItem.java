package tk.hildebrandt.testcontainers.springboot.todo;

import java.util.UUID;

import org.springframework.data.annotation.Id;

public class ToDoItem {

   @Id
   private String id;
   private String firstName;
   private String lastName;

   public static void generateId(ToDoItem toDoItem) {
      toDoItem.id = UUID.randomUUID().toString();
   }

   public ToDoItem withFirstName(String firstName) {
      this.firstName = firstName;
      return this;
   }

   public ToDoItem withLastName(String lastName) {
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
