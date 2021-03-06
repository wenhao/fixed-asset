package com.thoughtworks.fam.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.thoughtworks.fam.domain.User;
import com.thoughtworks.fam.exception.AuthenticationException;
import com.thoughtworks.fam.exception.ErrorCode;


@Service
public class LoginService
{
    public User login(String userName, String password)
    {

        User user1 = new User("test1", "123456") ;
        User user2 = new User("test2", "123456") ;
        User user3 = new User("test3", "123456") ;
        Map<String, User> users = new HashMap<String, User>();
        users.put(user1.getAccount(), user1);
        users.put(user2.getAccount(), user2);
        users.put(user3.getAccount(), user3);

        User user = users.get(userName);
        if (user == null) {
            throw new AuthenticationException(ErrorCode.USER_NOT_EXIST, "The user is not exist.");
        }
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException(ErrorCode.PASSWORD_NOT_MATCHED,
                    "The password is not correct, please input again.");
        }
        return user;
    }

}
