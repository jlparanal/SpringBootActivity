package com.stacktrek.SpringBootActivity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ModelAndView getTasks() {
        List<Task> task = taskService.getAllTasks();
        ModelAndView mav = new ModelAndView("ViewHomePage");
        mav.addObject("AllTasks", task);
        return mav;
    }

    @GetMapping("/addnew")
    public ModelAndView addNewData() {
        Task task = new Task();
        ModelAndView mav = new ModelAndView("AddNewData");
        mav.addObject("task", task);
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createTask(@ModelAttribute Task task) {
        taskService.createTask(task);
        ModelAndView modelAndView = new ModelAndView("redirect:/tasks");
        return modelAndView;
    }
    @GetMapping("/tasks/{id}/toggle")
    public ModelAndView toggleTask(@PathVariable Long id) {
        taskService.toggleTask(id);
        ModelAndView mav = new ModelAndView("redirect:/tasks");
        return mav;
    }
    @GetMapping("/tasks/{id}")
    public ModelAndView getTaskById(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("ViewHomePage");
        Optional<Task> task = taskService.getTaskById(id);
        if(task.isPresent()) {
            modelAndView.addObject("task", task.get());
        } else {
            modelAndView.addObject("error", "Task not found");
        }
        return modelAndView;
    }

    @PutMapping("/update/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        task.setId(id);
        return taskService.updateTask(task);
    }

    @DeleteMapping("/delete/{id}/delete")
    public ModelAndView deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        ModelAndView mav = new ModelAndView("redirect:/tasks");
        return mav;
    }
}
