package com.thoughtworks.fam.service;


import com.thoughtworks.fam.exception.AuthException;
import com.thoughtworks.fam.resource.domain.User;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class LoginServiceTest {

    @Test
    public void should_be_able_to_login_when_name_and_password_both_correct()
    {
        User user=new User();
        user.setAccount("test1");
        user.setPassword("123456");

        LoginService loginService = new LoginService();
        User userFromDB = loginService.login(user.getAccount(),user.getPassword());

        assertThat(userFromDB.getAccount(),is("test1"));
    }
    @Test(expected = AuthException.class)
    public void should_get_not_exist_when_name_not_exist()
    {
        User user=new User();
        user.setAccount("test4");
        user.setPassword("123456");

        LoginService loginService = new LoginService();
        loginService.login(user.getAccount(),user.getPassword());
    }

    @Test(expected = AuthException.class)
    public void should_get_password_not_right_when_password_not_right()
    {
        User user=new User();
        user.setAccount("test1");
        user.setPassword("1234");

        LoginService loginService = new LoginService();
        loginService.login(user.getAccount(),user.getPassword());
    }

}