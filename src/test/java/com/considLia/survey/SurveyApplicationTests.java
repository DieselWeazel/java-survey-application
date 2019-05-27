package com.considLia.survey;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SurveyApplicationTests {

  //  @Autowired
  //  private UserRepository userRepository;
  //
  //  @Rule
  //  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void contextLoads() {}

  //  @Test
  //  public void duplicateUserInfoTest(){
  //    this.userRepository = userRepository;
  ////    UserRepository userRepository;
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

}
