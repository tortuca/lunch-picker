package com.tortuca.lunchpicker.controller;

import com.tortuca.lunchpicker.model.LunchPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LunchControllerTest {
    private static final String CODE_INVALID = "NO_CODE";

    private static final String VENUE_1 = "Best Roti Prata";
    private static final String VENUE_2 = "Dry Ban Mian";
    private static final String VENUE_3 = "Hainanese Chicken RIce";

    private LunchController controller;

    @BeforeEach
    public void setUp() {
        controller = new LunchController();
    }

    @Test
    public void shouldCreateLunchPlan() {
        assertThat(controller.createPlan().getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldGetLunchPlan() {
        Object response = controller.createPlan().getBody();
        assert response != null;
        String code = ((LunchPlan) response).getCode();

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
        Object response = controller.createPlan().getBody();
        assert response != null;
        String code = ((LunchPlan) response).getCode();

        assertThat(controller.addVenue(code, VENUE_1).getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldChooseSingleVenue() {
        Object response = controller.createPlan().getBody();
        assert response != null;
        String code = ((LunchPlan) response).getCode();

        controller.addVenue(code, VENUE_1);
        Object response2 = controller.closePlan(code).getBody();
        assert response2 != null;
        String choice = ((LunchPlan) response2).getChoice();
        assertThat(choice).isEqualTo(VENUE_1);
    }

    @Test
    public void shouldChooseOneOfVenue() {
        List<String> options = List.of(VENUE_1, VENUE_2, VENUE_3);

        Object response = controller.createPlan().getBody();
        assert response != null;
        String code = ((LunchPlan) response).getCode();

        controller.addVenue(code, VENUE_1);
        controller.addVenue(code, VENUE_2);
        controller.addVenue(code, VENUE_3);
        Object response2 = controller.closePlan(code).getBody();
        assert response2 != null;
        String choice = ((LunchPlan) response2).getChoice();
        assertThat(choice).isIn(options);
    }
}
