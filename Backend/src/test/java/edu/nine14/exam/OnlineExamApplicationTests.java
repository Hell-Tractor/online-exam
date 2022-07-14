package edu.nine14.exam;

import edu.nine14.exam.entity.User;
import edu.nine14.exam.dao.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class OnlineExamApplicationTests {
	@Autowired
	UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void TestUserRepo() {
		Optional<User> user = userRepository.findById("admin");
		System.out.println(user.get().getUserType());
	}

}
