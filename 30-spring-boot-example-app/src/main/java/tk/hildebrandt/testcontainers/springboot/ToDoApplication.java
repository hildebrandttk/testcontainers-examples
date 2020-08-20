package tk.hildebrandt.testcontainers.springboot;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class ToDoApplication implements CommandLineRunner {

   private static final Logger LOG =
      LoggerFactory.getLogger(ToDoApplication.class);
   @Autowired
   private JdbcTemplate jdbcTemplate;

   public static void main(String[] args) {
      SpringApplication.run(ToDoApplication.class, args);
   }

   @Override
   public void run(String... strings) throws Exception {

      LOG.info("Creating tables");

      jdbcTemplate.execute("DROP TABLE users IF EXISTS");
      jdbcTemplate.execute("CREATE TABLE users(" +
         "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

      // Split up the array of whole names into an array of first/last names
      List<Object[]> splitUpNames =
         Arrays.asList("Wans Hurst", "Pans Hurst", "Klans Klust", "Hans Wurst")
            .stream()
            .map(name -> name.split(" "))
            .collect(Collectors.toList());
      //TODO use repositorys

      // Use a Java 8 stream to print out each tuple of the list
      splitUpNames.forEach(name -> LOG.info(String
         .format("Inserting users record for %s %s", name[0], name[1])));

      // Uses JdbcTemplate's batchUpdate operation to bulk load data
      jdbcTemplate.batchUpdate(
         "INSERT INTO users_table(first_name, last_name) VALUES (?,?)",
         splitUpNames);
      LOG.info("Querying for users records where first_name = 'Josh':");
   }
}
