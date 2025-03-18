package com.rm.project.controller;

import com.rm.project.model.Goal;
import com.rm.project.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {
    @Autowired
    private GoalService goalService;

    @GetMapping("/budget/{budgetId}")
    public List<Goal> getGoalsByBudget(@PathVariable Long budgetId) {
        return goalService.getGoalsByBudgetId(budgetId);
    }

    @PostMapping
    public Goal createGoal(@RequestBody Goal goal) {
        return goalService.saveGoal(goal);
    }
}
