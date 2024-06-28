package com.tortuca.lunchpicker.controller;

import com.tortuca.lunchpicker.model.LunchPlan;
import com.tortuca.lunchpicker.model.User;
import com.tortuca.lunchpicker.repository.LunchPlanRepository;

import com.tortuca.lunchpicker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Controller
@CrossOrigin(origins = "${cors.origin}")
public class LunchPlanController {
    private final LunchPlanRepository lunchPlanRepository;
    private final UserService userService;

    private static final int CODE_LENGTH = 6;
    private static final int MAX_ATTEMPTS = 3;

    @Autowired
    public LunchPlanController(
            LunchPlanRepository lunchPlanRepository,
            UserService userService) {
        this.lunchPlanRepository = lunchPlanRepository;
        this.userService = userService;
    }

    @GetMapping("/hello")
    @ResponseBody
    public Map<String, Object> hello() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }

    @PostMapping("/plan")
    public ResponseEntity<LunchPlan> createPlan(@RequestBody String username) {
        if (username == null || username.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (username.trim().isEmpty() || !isValidUsername(username.trim())) {
            return ResponseEntity.badRequest().build();
        }

        String code = generateUniqueCode();
        LunchPlan plan = new LunchPlan(UUID.randomUUID(), code);

        Optional<User> user = userService.getUser(username.trim());
        if (user.isEmpty()) {
            user = userService.createUser(username.trim());
        }
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        plan.setUserId(user.get().getId());
        LunchPlan saved  = lunchPlanRepository.save(plan);
        System.out.println(plan.getCode());
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/plan/{code}")
    public ResponseEntity<LunchPlan> getPlan(@PathVariable String code) {
        if (code == null || code.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (code.trim().isEmpty() || !isValidCode(code.trim())) {
            return ResponseEntity.badRequest().build();
        }
        LunchPlan found = lunchPlanRepository.findByCode(code);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @PostMapping("/plan/{code}/add")
    public ResponseEntity<LunchPlan> addVenue(@PathVariable String code, @RequestBody String venue) {
        if (code == null || code.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (code.trim().isEmpty() || !isValidCode(code.trim())) {
            return ResponseEntity.badRequest().build();
        }
        LunchPlan plan = lunchPlanRepository.findByCode(code);
        if (plan == null || !plan.getActive()) {
            return ResponseEntity.notFound().build();
        }

        // only add to venue if unique
        boolean unique = plan.getVenues().isEmpty() ||
                plan.getVenues().stream().noneMatch(x -> x.equalsIgnoreCase(venue));

        if (!unique) {
            return ResponseEntity.ok().build();
        }

        plan.addVenue(venue.trim());
        LunchPlan saved = lunchPlanRepository.save(plan);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/plan/{code}/close")
    public ResponseEntity<LunchPlan> closePlan(@PathVariable String code, @RequestBody String username) {
        if (code == null || code.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (code.trim().isEmpty() || !isValidCode(code.trim())) {
            return ResponseEntity.badRequest().build();
        }
        if (username == null || username.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (username.trim().isEmpty() || !isValidUsername(username.trim())) {
            return ResponseEntity.badRequest().build();
        }

        LunchPlan plan = lunchPlanRepository.findByCode(code);
        if (plan == null || !plan.getActive()) {
            return ResponseEntity.notFound().build();
        }

        Optional<User> user = userService.getUser(username.trim());
        if (user.isEmpty()|| !plan.getUserId().equals(user.get().getId())) {
            // lunch plan can only be closed by user
            System.out.println(user.isPresent() ? user.get().getUsername() : "No such user");
            return ResponseEntity.badRequest().build();
        }
        plan.setActive(false);
        if (plan.getVenues().isEmpty()) {
            // no choice can be made, close session
            return ResponseEntity.ok(lunchPlanRepository.save(plan));
        }

        String choice = chooseVenue(plan);
        plan.setChoice(choice);

        LunchPlan saved = lunchPlanRepository.save(plan);
        System.out.printf("%s %s\n", saved.getCode(), choice);
        return ResponseEntity.ok(saved);
    }

    private String generateUniqueCode() {
        String code = null;
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            code = generateCode(CODE_LENGTH);
            // expensive call, TODO: implement cache for recent generated codes
            if (lunchPlanRepository.findByCodeAndActive(code, true) == null) {
                return code;
            }
            attempts++;
        }

        if (attempts == MAX_ATTEMPTS) {
            System.out.println("Exceeded maximum number of attempts");
        }

        return code;
    }

    public static String generateCode(int n) {
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(abc.length() * Math.random());
            sb.append(abc.charAt(index));
        }
        return sb.toString();
    }

    public static String chooseVenue(LunchPlan plan) {
        int choice = (int) (Math.random() * plan.getVenues().size());
        return plan.getVenues().get(choice);
    }

    public static boolean isValidCode(String input) {
        final Pattern VALID_CODE = Pattern.compile("^[a-zA-Z0-9]*$");
        return input != null && VALID_CODE.matcher(input).matches();
    }

    public static boolean isValidUsername(String input) {
        final Pattern VALID_USERNAME = Pattern.compile("^[a-zA-Z0-9_.]*$");
        return input != null && VALID_USERNAME.matcher(input).matches();
    }
}
