package com.Fitory.fitory.repository;


import com.Fitory.fitory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    boolean existsByNickname(String nickname);
    Optional<User> findById(String user_id);
}
