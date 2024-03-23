package com.example.manasfen.reposiroties;

import com.example.manasfen.model.entyties.User;
import com.example.manasfen.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Page<User> findAllByRole(Role roleStudent, Pageable pageable);
}
