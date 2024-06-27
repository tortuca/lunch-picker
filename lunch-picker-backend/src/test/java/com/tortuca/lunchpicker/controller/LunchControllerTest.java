package com.tortuca.lunchpicker.controller;

import com.tortuca.lunchpicker.model.LunchPlan;
import com.tortuca.lunchpicker.repository.LunchPlanRepository;
import com.tortuca.lunchpicker.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class LunchControllerTest {
    private static final String CODE_INVALID = "NO_CODE";

    private static final String VENUE_1 = "Best Roti Prata";
    private static final String VENUE_2 = "Dry Ban Mian";
    private static final String VENUE_3 = "Hainanese Chicken RIce";

    private static final String USERNAME_1 = "test_user";
    private static final String USERNAME_INVALID = "invalid_user";
    private static final String USER_ID_1 = "00001111-aaaa-bbbb-cccc-000000000000";

    private LunchPlanController controller;

    @Autowired
    private LunchPlanRepository lunchPlanRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        controller = new LunchPlanController(lunchPlanRepository, userService);
    }

    @Test
    public void shouldCreateLunchPlan() {
        assertThat(controller.createPlan(USERNAME_1).getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldGetLunchPlan() {
        LunchPlan response = controller.createPlan(USERNAME_1).getBody();
        assert response != null;
        String code = response.getCode();

        assertThat(controller.getPlan(code).getStatusCode())
                .isEqualTo(HttpStatus.OK);

    }

    @Test
    public void givenNoMatchingCodeShouldReturnNotFound() {
        assertThat(controller.addVenue(CODE_INVALID, VENUE_1).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldAddVenue() {
        LunchPlan response = controller.createPlan(USERNAME_1).getBody();
        assert response != null;
        String code = response.getCode();

        assertThat(controller.addVenue(code, VENUE_1).getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenDifferentUsernameShouldReturnBadRequest() {
        LunchPlan response = controller.createPlan(USERNAME_1).getBody();
        assert response != null;
        String code = response.getCode();

        assertThat(controller.closePlan(code, USERNAME_INVALID).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldCloseLunchPlan() {
        LunchPlan response = controller.createPlan(USERNAME_1).getBody();
        assert response != null;
        String code = response.getCode();

        LunchPlan response2 = controller.closePlan(code, USERNAME_1).getBody();
        assert response2 != null;
        assertFalse(response2.getActive());
    }

    @Test
    public void shouldChooseSingleVenue() {
        LunchPlan response = controller.createPlan(USERNAME_1).getBody();
        assert response != null;
        String code = response.getCode();

        controller.addVenue(code, VENUE_1);
        LunchPlan response2 = controller.closePlan(code, USERNAME_1).getBody();
        assert response2 != null;
        assertFalse(response2.getActive());

        String choice = response2.getChoice();
        assertThat(choice).isEqualTo(VENUE_1);
    }

    @Test
    public void shouldChooseOneOfVenue() {
        List<String> options = List.of(VENUE_1, VENUE_2, VENUE_3);

        LunchPlan response = controller.createPlan(USERNAME_1).getBody();
        assert response != null;
        String code = response.getCode();

        controller.addVenue(code, VENUE_1);
        controller.addVenue(code, VENUE_2);
        controller.addVenue(code, VENUE_3);
        LunchPlan response2 = controller.closePlan(code, USERNAME_1).getBody();
        assert response2 != null;
        assertFalse(response2.getActive());

        String choice = response2.getChoice();
        assertThat(choice).isIn(options);
    }
}
