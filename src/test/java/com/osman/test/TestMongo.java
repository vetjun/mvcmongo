package com.osman.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.osman.dao.UserRepository;
import com.osman.model.User;
import com.osman.service.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml",
		"file:src/main/webapp/WEB-INF/root-context.xml"})
public class TestMongo {
	
	@Autowired
	private UserService userService;

	@Autowired
	private User userObject;

	@Before
	public void init() {

	}
	@Test
	public void testCreateUser() {
		   User newuser = new User("name1","surname1","telephone1");
		   User createduser=userService.create(newuser);
		   /*System.out.println(createduser.getId());
		   System.out.println(newuser.getId());*/
		   assertEquals("users should be equal.",newuser, createduser);
	}
	@Test
	public void testDeleteUser() {
		List <User> users=userService.readAll();
		User willbedeleted = users.get(0);
		Boolean isdelete = false;
		isdelete=userService.delete(willbedeleted);
		assertTrue(isdelete);
	}
	@Test
	public void testUpdateUser() {
		List <User> users=userService.readAll();
		User willbeupdated = users.get(0);
		User tempuser = willbeupdated;
		willbeupdated.setName("updatename");
		willbeupdated.setSurname("updatename");
		willbeupdated.setTelephone("updatetel");
		User newuser=userService.update(willbeupdated);
		assertNotEquals("users should not be equal.",newuser, tempuser);
	}

}
