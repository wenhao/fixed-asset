package com.thoughtworks.fam.service;

import com.google.common.collect.Lists;
import com.thoughtworks.fam.domain.Asset;
import com.thoughtworks.fam.domain.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService
{
    private static Map<String, User> users = new HashMap<String, User>()
    {
        {
            put("twer", new User("twer", "123456"));
        }
    };

    public List<Asset> getAssets()
    {
        List<Asset> assets = Lists.newArrayList(
                new Asset("Macbook", "123456", "2015-05-08", "Laptop"),
                new Asset("iPhone", "123457", "2015-05-09", "Mobile")
        );
        return assets;
    }

    public boolean createUser(User user)
    {
        if(!users.containsKey(user.getAccount())) {
            users.put(user.getAccount(), user);
            return true;
        }
        return false;
    }
}