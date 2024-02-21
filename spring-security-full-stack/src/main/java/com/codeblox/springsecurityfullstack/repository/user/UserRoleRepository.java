package com.codeblox.springsecurityfullstack.repository.user;

import com.codeblox.springsecurityfullstack.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
