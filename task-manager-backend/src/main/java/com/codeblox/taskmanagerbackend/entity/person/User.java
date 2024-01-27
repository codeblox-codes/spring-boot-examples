package com.codeblox.taskmanagerbackend.entity.person;

import com.codeblox.taskmanagerbackend.entity.BaseEntity;
import com.codeblox.taskmanagerbackend.entity.task.Task;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "APP_USER")
public class User extends BaseEntity implements UserDetails {

    private String name;

    private String email;

    private String password;

    private Boolean accountStatus;

    @OneToOne(mappedBy = "user")
    private UserConfirmation userConfirmation;

    @OneToMany(mappedBy = "creator")
    private List<Task>tasks;

    @OneToMany(mappedBy = "assignee")
    private List<Task>taskList;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private UserAuthority userAuthority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("AUTHORITY_"+this.userAuthority.getAuthorityType()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountStatus;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountStatus;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.accountStatus;
    }

    @Override
    public boolean isEnabled() {
        return this.accountStatus;
    }
}
