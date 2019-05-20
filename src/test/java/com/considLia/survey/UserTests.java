//package com.considLia.survey;
//
//import com.considlia.survey.model.User;
//import com.considlia.survey.repositories.UserRepository;
//import java.sql.SQLIntegrityConstraintViolationException;
//import org.hibernate.exception.ConstraintViolationException;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//
//public class UserTests {
//
//
// TODO: Fix Tests, right now not finished.

//  @Mock
//  private UserRepository userRepository;
//////
//  @Rule
//  public ExpectedException thrown = ExpectedException.none();
//
//  @Test
//  public void duplicateUserInfoTest(){
////    this.userRepository = userRepository;
//    User user1 = new User();
//    user1.setUsername("user");
//    user1.setEmail("email@email.com");
//    user1.setRole("USER");
//    user1.setFirstName("USER");
//    user1.setLastName("USER");
//
//    User user2 = new User();
//    user2.setUsername("user");
//    user2.setRole("USER");
//    user2.setFirstName("USER");
//    user2.setLastName("USER");
//    user2.setEmail("email@email.com");
//
//    userRepository.save(user1);
//    userRepository.save(user2);
//
//    thrown.expect(ConstraintViolationException.class);
//    thrown.expect(SQLIntegrityConstraintViolationException.class);
//    thrown.expect(DataIntegrityViolationException.class);
//  }
//}
