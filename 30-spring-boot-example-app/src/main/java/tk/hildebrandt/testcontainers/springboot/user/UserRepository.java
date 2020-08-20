package tk.hildebrandt.testcontainers.springboot.user;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

   @Query("select \"id\", \"first_name\", \"last_name\" from \"users_table\" where upper(\"first_name\") like '%' || upper(:firstName) || '%' AND  upper(\"last_name\") like '%' || upper(:lastName) || '%'  ")
   List<User> findByFirstNameAndLastName(@Param("firstName") String firstName,
                                         @Param("lastName") String lastName);
}
