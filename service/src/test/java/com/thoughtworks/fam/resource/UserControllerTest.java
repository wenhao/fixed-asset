package com.thoughtworks.fam.resource;

import com.thoughtworks.fam.domain.Asset;
import com.thoughtworks.fam.domain.User;
import com.thoughtworks.fam.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest
{

    UserService userService;
    @InjectMocks
    private UserController userController;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        userService = mock(UserService.class);
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void should_be_able_to_get_user_assets() throws Exception
    {
        given(userService.getAssets()).willReturn(
                newArrayList(
                        new Asset("foo", "bar", "awe", "some"),
                        new Asset("aaa", "bbb", "ccc", "ddd")));
        mockMvc.perform(get("/users/uid/assets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].assetName", is("foo")))
                .andExpect(jsonPath("$[0].assetNumber", is("bar")))
                .andExpect(jsonPath("$[0].assignedDate", is("awe")))
                .andExpect(jsonPath("$[0].assetType", is("some")));
    }

    @Test
    public void should_be_able_to_create_user() throws Exception {
        given(userService.createUser(any(User.class))).willReturn(true);

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .content("{\"account\": \"test\", \"password\": \"123456\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_get_conflict_status_code_when_account_exist() throws Exception {
        given(userService.createUser(any(User.class))).willReturn(false);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"account\": \"twer\", \"password\": \"123456\"}"))
                .andExpect(status().isConflict());
    }
}