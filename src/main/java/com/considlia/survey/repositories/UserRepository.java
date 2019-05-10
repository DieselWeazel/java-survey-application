package com.considlia.survey.repositories;

import com.considlia.survey.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  /*
  Is it that I now get the User instead of the optional? I remember it being good for some reason.
   */
  User findByEmail(String email);
// THIS METHOD WILL PROBABLY NOT WORK(?)

}
