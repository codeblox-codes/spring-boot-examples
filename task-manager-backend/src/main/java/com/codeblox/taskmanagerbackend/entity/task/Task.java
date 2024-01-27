package com.codeblox.taskmanagerbackend.entity.task;

import com.codeblox.taskmanagerbackend.entity.BaseEntity;
import com.codeblox.taskmanagerbackend.entity.person.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task extends BaseEntity {

    private String title;

    @Column(columnDefinition = "text")
    private String content;

    private Date creationDate;

    private Date startDate;

    private Date dueDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @ManyToOne
    private User creator;

    @ManyToOne
    private User assignee;

}
