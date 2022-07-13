package com.ecommerce.userservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;



import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.ecommerce.userservice.controller.UserController;
import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.exception.UserException;
import com.ecommerce.userservice.model.Address;


import com.ecommerce.userservice.service.UserService;




@SpringBootTest
class UserControllerTest {
	@Mock
	UserService userService;
	@InjectMocks
	UserController userController;
	UserDto userdto =new UserDto("123dhfjdfj","samuvel","joseph","samuvel joseph","samuveljoseph@gmail.com","samuvel",
			new Address("abcd house","kallettumkara","thrissur","kerala",680683,812966354));
	String userId="123dhfjdfj";
	@Test
	void test_getAllUserDetail()  {
		List<UserDto> userDto=new ArrayList<UserDto>();
		userDto.add(new UserDto("1223ndsjd","abcd","efg","abcdefg","abcdefg@gmail.com","abcde",
					new Address("zzz","zzz","zzz","zzz",680683,812966354)));
		userDto.add(new UserDto("123dhfjdfj","samuvel","joseph","samuvel joseph","samuveljoseph@gmail.com","samuvel",
				new Address("abcd house","kallettumkara","thrissur","kerala",680683,812966354)));
		when(userService.getAllUserDetails()).thenReturn(userDto);
		ResponseEntity<?> res=userController.getAllUserDetail();
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
	void test_registerUser() throws UserException {
		when(userService.signUp(userdto)).thenReturn(userdto);
		ResponseEntity<UserDto> res=userController.registerUser(userdto);
		
		assertEquals(HttpStatus.CREATED,res.getStatusCode());
		assertEquals(userdto,res.getBody());
	}
	@Test
	void test_getUserDetail() throws UserException {
		when(userService.userDetails(userdto.getId())).thenReturn(userdto);
		ResponseEntity<UserDto> res=userController.getUserDetail(userId);
		
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(userdto,res.getBody());
	}
	@Test
	void test_editUser() throws UserException {
		when(userService.editUserDetails(userId,userdto)).thenReturn(userdto);
		ResponseEntity<UserDto> res=userController.editUser(userId,userdto);
		
		assertEquals(HttpStatus.OK,res.getStatusCode());
		UserDto userd=res.getBody();
		assertEquals("123dhfjdfj",userd.getId());
		assertEquals("samuvel",userd.getFirstName());
		assertEquals("joseph",userd.getLastName());
		assertEquals(userdto.getAddress(),userd.getAddress());
	}
	@Test
	void test_deleteUser() throws UserException {
		String msg="user is deleted successfully!!";
		ResponseEntity<?> res=userController.deleteUser(userId);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(msg,res.getBody());
	}
	
	/*@Test
	void test_getAllUserDetail() {
		List<User> user=new ArrayList<User>();
		user.add(new User("1223ndsjd","abcd","efg","abcdefg","abcdefg@gmail.com","abcde",
				new Address("zzz","zzz","zzz","zzz",680683,812966354)));
	user.add(new User("123dhfjdfj","samuvel","joseph","samuvel joseph","samuveljoseph@gmail.com","samuvel",
			new Address("abcd house","kallettumkara","thrissur","kerala",680683,812966354)));
	List<UserDto> userDto=new ArrayList<UserDto>();
	userDto.add(new UserDto("1223ndsjd","abcd","efg","abcdefg","abcdefg@gmail.com","abcde",
				new Address("zzz","zzz","zzz","zzz",680683,812966354)));
	userDto.add(new UserDto("123dhfjdfj","samuvel","joseph","samuvel joseph","samuveljoseph@gmail.com","samuvel",
			new Address("abcd house","kallettumkara","thrissur","kerala",680683,812966354)));
		when(userService.getAllUserDetails()).thenReturn(userDto);
		ResponseEntity<ApiResponse> res=userController.getAllUserDetail();
		assertEquals(HttpStatus.OK,res.getStatusCode());
		List<User> userData=new ArrayList<User>();
		userData=(ArrayList)res.getBody().getResponseData();
		assertEquals(2,);
	}
	@Test
	void test_getUserDetail() {
	
		
		when(!userService.readUserById(userId)).thenReturn(true);
		when(userService.userDetails(userId)).thenReturn(Optional.of(user));
		ResponseEntity<User> res=userController.getUserDetail(userId);
		
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(user,res.getBody());
	}
	@Test
void test_registerUser() {

		when(userService.existUserName(user.getUserName())).thenReturn(false);
		when(userService.existEmailId(user.getEmailId())).thenReturn(false);
		when(userService.signUp(user)).thenReturn(user);
		ResponseEntity<ApiResponse> res=userController.registerUser(user);
		
		assertEquals(HttpStatus.CREATED,res.getStatusCode());
		assertEquals(user,res.getBody().getResponseData());
	}
	@Test
	void test_editUser() {
		UserDto userDto =new UserDto("samuvel","joseph",
				new Address("abcd house","kallettumkara","thrissur","kerala",680683,812966354));
		User user=new User("123dhfjdfj",userDto.getFirstName(),userDto.getLastName(),"samuvel joseph","samuveljoseph@gmail.com","samuvel",userDto.getAddress());

		when(!userService.readUserById(userId)).thenReturn(true);
		when(userService.editUserDetails(userId,userDto)).thenReturn(user);
		ResponseEntity<ApiResponse> res=userController.editUser(userId,userDto);
		
		assertEquals(HttpStatus.OK,res.getStatusCode());
		User userd=(User)res.getBody().getResponseData();
		assertEquals("123dhfjdfj",userd.getId());
		assertEquals("samuvel",userd.getFirstName());
		assertEquals("joseph",userd.getLastName());
		assertEquals(user.getAddress(),userd.getAddress());
	}
	@Test
void test_editUserFailure() {
		UserDto userDto =new UserDto("samuvel","joseph",
				new Address("abcd house","kallettumkara","thrissur","kerala",680683,812966354));
	String msg="user not found";
	when(!userService.readUserById(userId)).thenReturn(false);
		ResponseEntity<ApiResponse> res=userController.editUser(userId,userDto);
		
		assertEquals(HttpStatus.NOT_FOUND,res.getStatusCode());
		assertEquals(msg,res.getBody().getResponseData());
	}
	@Test
void test_registerUserFaNameFailure() {
	String msg="Error: user name exist!!" ;
		when(userService.existUserName(user.getUserName())).thenReturn(true);
		ResponseEntity<ApiResponse> res=userController.registerUser(user);
		
		assertEquals(HttpStatus.NOT_FOUND,res.getStatusCode());
		assertEquals(msg,res.getBody().getResponseData());
	}
	@Test
void test_registerUserEmailFailure() {

	String msg="Error: emailId exist!!";
		when(userService.existEmailId(user.getEmailId())).thenReturn(true);
		ResponseEntity<ApiResponse> res=userController.registerUser(user);
		
		assertEquals(HttpStatus.NOT_FOUND,res.getStatusCode());
		assertEquals(msg,res.getBody().getResponseData());
	}
	@Test
	void test_getAllUserDetailFailure() {
		List<User> user=new ArrayList<User>();
		user.add(new User("1223ndsjd","abcd","efg","abcdefg","abcdefg@gmail.com","abcde",
				new Address("zzz","zzz","zzz","zzz",680683,812966354)));
	user.add(new User("123dhfjdfj","samuvel","joseph","samuvel joseph","samuveljoseph@gmail.com","samuvel",
			new Address("abcd house","kallettumkara","thrissur","kerala",680683,812966354)));
		IllegalArgumentException illegalArgumentException=new IllegalArgumentException();
		when(userService.getAllUserDetails()).thenThrow(illegalArgumentException);
		ResponseEntity<ApiResponse> res=userController.getAllUserDetail();
		assertEquals(HttpStatus.NOT_FOUND,res.getStatusCode());
	
	}
	@Test
	void test_deleteUser() {

		String msg="user is deleted successfully!!";
		when(!userService.readUserById(userId)).thenReturn(true);
		ResponseEntity<ApiResponse> res=userController.deleteUser(userId);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(msg,res.getBody().getResponseData());
	}
	@Test
	void test_deleteUserFail() {
		String userId="123dhfjdfj";
		String msg="user not found";
		when(!userService.readUserById(userId)).thenReturn(false);
		ResponseEntity<ApiResponse> res=userController.deleteUser(userId);
		assertEquals(HttpStatus.NOT_FOUND,res.getStatusCode());
		assertEquals(msg,res.getBody().getResponseData());
	}
	@Test
	void test_getUserDetailFail() {
	
		when(!userService.readUserById(userId)).thenReturn(false);
		ResponseEntity<User> res=userController.getUserDetail(userId);
		
		assertEquals(HttpStatus.NOT_FOUND,res.getStatusCode());
	}*/
}
