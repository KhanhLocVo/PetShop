package com.petshop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.petshop.common.entity.Role;
import com.petshop.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired private UserRepository repo;
	@Autowired private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userLoc = new User("loc@gmail.com", "loc2023", "loc", "vo");
		userLoc.addRole(roleAdmin);
		User saveUser = repo.save(userLoc);
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userTeo = new User("teo@gmail.com","teo2023","teo","nguyen");
		Role roleEdito = new Role(3);
		Role roleAssistant = new Role(5);
		
		userTeo.addRole(roleAssistant);
		userTeo.addRole(roleEdito);
		
		User saveUser = repo.save(userTeo);
		
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
}
