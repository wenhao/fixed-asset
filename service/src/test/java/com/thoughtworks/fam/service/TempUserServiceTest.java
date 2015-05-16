package com.thoughtworks.fam.service;

import com.thoughtworks.fam.domain.TempAsset;
import com.thoughtworks.fam.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TempUserServiceTest
{
    private TempUserService userService;

    @Before
    public void setUp()
    {
        userService = new TempUserService();
    }

    @Test
    public void should_be_able_to_get_assets()
    {
        List<TempAsset> assets = userService.getAssets();

        assertThat(assets.size()).isGreaterThan(0);
    }

    @Test
    public void should_be_able_to_create_user()
    {
        boolean result = userService.createUser(new User("test", "123"));

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void should_create_user_failed_when_user_exist()
    {
        boolean result = userService.createUser(new User("twer", "123"));

        assertThat(result).isEqualTo(false);
    }
}