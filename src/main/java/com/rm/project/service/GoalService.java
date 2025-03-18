package com.rm.project.service;

import com.rm.project.model.Goal;
import com.rm.project.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;

    public List<Goal> getGoalsByBudgetId(Long budgetId) {
        return goalRepository.findByBudgetId(budgetId);
    }

    public Goal saveGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public void deleteGoal(Goal goal) {
        goalRepository.delete(goal);
    }
}
