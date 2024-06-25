package com.tortuca.lunchpicker.controller;

import com.tortuca.lunchpicker.model.LunchPlan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class LunchController {
    Map<String, LunchPlan> sessions = new HashMap<>();

    @GetMapping("/hello")
    @ResponseBody
    public Map<String, Object> hello() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }

    @PostMapping("/plan")
    public ResponseEntity createPlan() {
        LunchPlan plan = new LunchPlan(UUID.randomUUID().toString(), generateCode(6));
        sessions.put(plan.getCode(), plan);
        System.out.println(plan.getCode());
        return ResponseEntity.ok(plan);
    }

    @GetMapping("/plan/{code}")
    public ResponseEntity getPlan(@PathVariable String code) {
        LunchPlan plan = sessions.getOrDefault(code, null);
        if (plan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(plan);
    }

    @PostMapping("/plan/{code}/add")
    public ResponseEntity addVenue(@PathVariable String code, @RequestBody String venue) {
        LunchPlan plan = sessions.getOrDefault(code, null);
        if (plan == null || !plan.getActive()) {
            return ResponseEntity.notFound().build();
        }

        // only add to venue if unique
        boolean unique = plan.getVenues().isEmpty() ||
                plan.getVenues().stream().noneMatch(x -> x.equalsIgnoreCase(venue));
//        System.out.printf("%s", plan.getVenues());
//        System.out.printf("%s %s", venue, unique);
        if (unique) {
            plan.addVenue(venue);
        }
        return ResponseEntity.ok(plan);
    }

    @PostMapping("/plan/{code}/close")
    public ResponseEntity closePlan(@PathVariable String code) {
        LunchPlan plan = sessions.getOrDefault(code, null);
        if (plan == null || !plan.getActive()) {
            return ResponseEntity.notFound().build();
        }
        plan.setActive(false);
        if (plan.getVenues().isEmpty()) {
            // no choice can be made, close session
            return ResponseEntity.ok(plan);
        }

        String choice = chooseVenue(plan);
        plan.setChoice(choice);
        System.out.printf("%s %s\n", plan.getCode(), choice);
        return ResponseEntity.ok(plan);
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
}
