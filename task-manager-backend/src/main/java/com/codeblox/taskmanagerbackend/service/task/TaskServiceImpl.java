package com.codeblox.taskmanagerbackend.service.task;

import com.codeblox.taskmanagerbackend.repository.task.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements ITaskService{

    private TaskRepository taskRepository;
}
