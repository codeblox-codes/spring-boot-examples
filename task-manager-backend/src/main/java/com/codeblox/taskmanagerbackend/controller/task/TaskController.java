package com.codeblox.taskmanagerbackend.controller.task;

import com.codeblox.taskmanagerbackend.service.task.TaskServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("tasks")
public class TaskController {

    private TaskServiceImpl taskService;
}
