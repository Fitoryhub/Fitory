package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.entity.UserHealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserHealthRepository extends JpaRepository<UserHealthProfile, String> {
    Optional<UserHealthProfile> findById(String id);
}
