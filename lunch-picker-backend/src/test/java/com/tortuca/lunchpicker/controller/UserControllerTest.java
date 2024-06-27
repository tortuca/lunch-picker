package com.tortuca.lunchpicker.controller;

import com.tortuca.lunchpicker.model.LunchPlan;
import com.tortuca.lunchpicker.model.User;
import com.tortuca.lunchpicker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserControllerTest {

    private static final String USERNAME_NEW = "new_user";
    private static final String USERNAME_1 = "test_user";
    private static final String USERNAME_INVALID = "invalid_user";

    private UserController controller;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        controller = new UserController(userService);
    }

    @Test
    public void shouldCreateUser() {
        User response = controller.createUser(USERNAME_NEW).getBody();
        assertThat(response).isNotNull();

        assert response != null;
        assertThat(response.getUsername()).isEqualTo(USERNAME_NEW);
    }

    @Test
    public void shouldGetUser() {
        User response = controller.getUser(USERNAME_1).getBody();
        assert response != null;

        assertThat(response.getUsername()).isEqualTo(USERNAME_1);
    }

    @Test
    public void givenNoMatchingCodeShouldReturnNotFound() {
        assertThat(controller.getUser(USERNAME_INVALID).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
