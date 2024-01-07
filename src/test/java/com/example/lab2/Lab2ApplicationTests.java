package com.example.lab2;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class Lab2ApplicationTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	UserController userController;





	@Test
	void getAllUsersReturnAllUsers() throws Exception {
		Mockito.when(this.userController.index()).thenReturn(getUsers());

		mvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(3));
	}



	@Test
	void findOneShouldReturnValidUser() throws Exception {
		Mockito.when(this.userController.getUser(1)).thenReturn(getUsers().get(0));

		mvc.perform(get("/user/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.age").value(20))
				.andExpect(jsonPath("$.name").value("oleg"))
				.andExpect(jsonPath("$.role").value("teacher"));
	}

	@Test
	void addUserShouldAddNewUser() throws Exception {
		User user = new User(200, "sanya", "teacher", 64);
		this.userController.addUser(user);
		Mockito.when(this.userController.getUser(200)).thenReturn(user);

		mvc.perform(get("/user/200"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(200))
				.andExpect(jsonPath("$.age").value(64))
				.andExpect(jsonPath("$.name").value("sanya"))
				.andExpect(jsonPath("$.role").value("teacher"));
	}

	@Test
	void updateUserShouldChangeUser() throws Exception {
		User user = new User(1, "oleg", "student", 7);
		this.userController.updateUser(1,user);
		Mockito.when(this.userController.getUser(1)).thenReturn(user);

		mvc.perform(get("/user/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.age").value(7))
				.andExpect(jsonPath("$.name").value("oleg"))
				.andExpect(jsonPath("$.role").value("student"));
	}

	@Test
	void deleteUserShouldDeleteUser() throws Exception {
		this.userController.deleteUser(1);
		Mockito.when(this.userController.getUser(1)).thenReturn(null);

		mvc.perform(get("/user/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").doesNotExist());
	}
	private List<User> getUsers() {
		User one = new User(1, "oleg", "teacher", 20);
		User two = new User(52, "ivan", "student", 20);
		User three = new User(102, "stepan", "student", 25);
		return List.of(one, two, three);
	}

}
