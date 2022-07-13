package com.ecommerce.userservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecommerce.userservice.converter.UserConverter;
import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.exception.UserException;
import com.ecommerce.userservice.model.Address;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.repository.UserRepo;
import com.ecommerce.userservice.serviceimplementation.UserServiceImplementation;

@SpringBootTest
class UserServiceApplicationTests {
	@Mock
	UserRepo userRepo;
	@Mock
	UserConverter converter;
	@InjectMocks
	UserServiceImplementation userServiceImplementation;
	
	UserDto userdto =new UserDto("123dhfjdfj","samuvel","joseph","samuvel joseph","samuveljoseph@gmail.com","samuvel",
			new Address("abcd house","kallettumkara","thrissur","kerala",680683,812966354));
	User user =new User("123dhfjdfj","samuvel","joseph","samuvel joseph","samuveljoseph@gmail.com","samuvel",
			new Address("abcd house","kallettumkara","thrissur","kerala",680683,812966354));
	
	
	@Test
	
	 void test_getAllUser() {
		List<User> user3=new ArrayList<User>();
		user3.add(new User(user.getId(),user.getFirstName(),user.getLastName(),user.getUserName(),user.getEmailId(),user.getPassword(),
				user.getAddress()));
		user3.add(new User("123dhfjdfj","samuvel","joseph","samuvel joseph","samuveljoseph@gmail.com","samuvel",
				new Address("abcd house","kallettumkara","thrissur","kerala",680683,812966354)));
		List<UserDto> userDto3=new ArrayList<UserDto>();
		userDto3.add(new UserDto(userdto.getId(),userdto.getFirstName(),userdto.getLastName(),userdto.getUserName(),userdto.getEmailId(),userdto.getPassword(),
				userdto.getAddress()));
		userDto3.add(new UserDto("123dhfjdfj","samuvel","joseph","samuvel joseph","samuveljoseph@gmail.com","samuvel",
				new Address("abcd house","kallettumkara","thrissur","kerala",680683,812966354)));
		when(userRepo.findAll()).thenReturn(user3);//mocking
		when(converter.userToDto(user3)).thenReturn(userDto3);
		assertEquals(2,userServiceImplementation.getAllUserDetails().size());
	}
	@Test
	
	 void signUpTest() throws UserException {
		Address address=new Address();
		address.setAddressLine1("abcd house");
		address.setAddressLine2("kallettumkara");
		address.setCity("thrissur");
		address.setMobileNumber(8129663541L);
		address.setPincode(680683);
		address.setState("kerala");
		User user1=new User();
		user1.setId("123dhfjdfj");
		user1.setEmailId("samuveljoseph@gmail.com");
		user1.setFirstName("samuvel");
		user1.setAddress(new Address(address.getAddressLine1(),
				address.getAddressLine2(),
				address.getCity(),
				address.getState(),
				address.getPincode(),
				address.getMobileNumber()));
		user1.setLastName("joseph");
		user1.setPassword("samuvel");
		user1.setUserName("samuvel joseph");
		UserDto userdto1=new UserDto();
		userdto1.setId("123dhfjdfj");
		userdto1.setEmailId("samuveljoseph@gmail.com");
		userdto1.setFirstName("samuvel");
		userdto1.setAddress(address);
		userdto1.setLastName("joseph");
		userdto1.setPassword("samuvel");
		userdto1.setUserName("samuvel joseph");
		Mockito.when(converter.dtoToUser(userdto1)).thenReturn(user1);
		Mockito.when(userRepo.save(user1)).thenReturn(user1);
		Mockito.when(converter.userToDto(user1)).thenReturn(userdto1);
		assertEquals(userServiceImplementation.signUp(userdto1),userdto1);
	}
	@Test
	
	 void userDetailsTest () throws UserException {
		//Mockito.when(this.userRepo.findById(user.getId()))
	   // .thenReturn(java.util.Optional.of(user));
		Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.ofNullable(user));
		Mockito.when(converter.userToDto(user)).thenReturn(userdto);
		assertEquals(userServiceImplementation.userDetails("123dhfjdfj"),userdto);
	}
	@Test
	
	 void deleteUserDetailsTest () throws UserException {
		Mockito.when(this.userRepo.findById(user.getId()))
	    .thenReturn(java.util.Optional.of(user));
		//Mockito.when(userRepo.findById(user.getId()).isPresent()).thenReturn(true);
		userServiceImplementation.deleteUserDetails(user.getId());
		verify(userRepo,times(1)).deleteById(user.getId());
	}
	@Test
	
	 void editUserDetailsTest() throws UserException {
		//Mockito.when(this.userRepo.findById(user.getId()))
	    //.thenReturn(java.util.Optional.of(user));
		Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.ofNullable(user));
		Mockito.when(converter.dtoToUserUpdate(userdto,user)).thenReturn(user);
		Mockito.when(userRepo.save(user)).thenReturn(user);
		Mockito.when(converter.userToDto(user)).thenReturn(userdto);
		assertEquals(userdto, userServiceImplementation.editUserDetails(user.getId(), userdto));
	}
	

	@Test
	void editUserDetailsTestFail() throws UserException{
		Mockito.when(userRepo.findById("1hgf")).thenReturn(Optional.empty());
		assertThrows(UserException.class,()-> userServiceImplementation.editUserDetails("1hgf", userdto));
	}
	@Test
	void deleteUserDetailsTestFail() throws UserException{
		//User user1=new User(null,null,null,null,null,null,null);
		Mockito.when(userRepo.findById("1hgf")).thenReturn(Optional.empty());
		assertThrows(UserException.class,()-> userServiceImplementation.deleteUserDetails("1hgf"));

}
	@Test
	void userDetailsTestFail() throws UserException{
		Mockito.when(userRepo.findById("1hgf")).thenReturn(Optional.empty());
		assertThrows(UserException.class,()->userServiceImplementation.userDetails("1hgf"));
	}
	@Test
	void signupEmailidTestFail() throws UserException{
		when(userRepo.existByEmailId(userdto.getEmailId())).thenReturn(Optional.ofNullable(user));
		assertThrows(UserException.class,()-> userServiceImplementation.signUp(userdto));
	}
	@Test
	void signupUserNameTestFail() throws UserException{
		when(userRepo.existByUserName(userdto.getUserName())).thenReturn(Optional.ofNullable(user));
		assertThrows(UserException.class,()-> userServiceImplementation.signUp(userdto));
	}
	

	/*@Order(6)
	 void existuserNameTest() {
		Address address=new Address();
		address.setAddressLine1("abcd house");
		address.setAddressLine2("kallettumkara");
		address.setCity("thrissur");
		address.setMobileNumber(8129663541L);
		address.setPincode(680683);
		address.setState("kerala");
		User user=new User();
		user.setId("123dhfjdfj");
		user.setEmailId("samuveljoseph@gmail.com");
		user.setFirstName("samuvel");
		user.setAddress(address);
		user.setLastName("joseph");
		user.setPassword("samuvel");
		user.setUserName("samuvel joseph");
		
		Mockito.when(userRepo.existByUserName(user.getId())).thenReturn(Optional.ofNullable(user));
		assertEquals(true, userServiceImplementation.existUserName("123dhfjdfj"));
	}
	@Test
	@Order(7)
	 void existemaulidTest() {
		Address address=new Address();
		address.setAddressLine1("abcd house");
		address.setAddressLine2("kallettumkara");
		address.setCity("thrissur");
		address.setMobileNumber(8129663541L);
		address.setPincode(680683);
		address.setState("kerala");
		User user=new User("123dhfjdfj","samuvel","joseph","samuvel joseph","samuveljoseph@gmail.com","samuvel",
				new Address(address.getAddressLine1(),
				address.getAddressLine2(),
				address.getCity(),
				address.getState(),
				address.getPincode(),
				address.getMobileNumber()
				));
		Mockito.when(userRepo.existByEmailId(user.getId())).thenReturn(Optional.ofNullable(user));
		assertEquals(true, userServiceImplementation.existEmailId("123dhfjdfj"));
	}
	@Test
	@Order(8)
	 void readUserByIdTest () {
		
		Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.ofNullable(user));
		assertEquals(true, userServiceImplementation.readUserById("123dhfjdfj"));
	}*/
}
