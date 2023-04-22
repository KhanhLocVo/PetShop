package com.petshop.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.petshop.common.entity.Role;
import com.petshop.common.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	public static final int USERS_PER_PAGE = 4;
	@Autowired private UserRepository userRepo;
	@Autowired private RoleRepository roleRepo;
	@Autowired private PasswordEncoder passwordEncoder;
	
	public List<User> listAll(){
		return (List<User>) userRepo.findAll(Sort.by("firstName").ascending());
	}
	
	public Page<User> listByPage(int pageNum){
		Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE);
		return userRepo.findAll(pageable);
	}
	
	public List<Role> listRoles(){
		return (List<Role>) roleRepo.findAll();
	}
	
	public User save(User user) {
		boolean isUpdatingUser = (user.getId() != null);
		
		if(isUpdatingUser) {
			User existingUser = userRepo.findById(user.getId()).get();
			
			if(user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			}
			
		}else {
			encodePassword(user);
		}
		return userRepo.save(user);
	}
	
	private void encodePassword(User user) {
		String encoderPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encoderPassword);
	}
	
	public boolean isEmailUnique(Integer id,String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		
		if(userByEmail == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		if(isCreatingNew) {
			if(userByEmail != null) return false;
		}else {
			if(userByEmail.getId() != id) {
				return false;
			}
		}
		
		return true ;
	}
	
	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			// TODO: handle exception
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
		
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countByIdLong = userRepo.countById(id);
		if(countByIdLong == null || countByIdLong == 0) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
		userRepo.deleteById(id);
	}
	
	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}
}
