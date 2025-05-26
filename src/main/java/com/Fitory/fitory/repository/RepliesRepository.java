package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Replies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepliesRepository extends JpaRepository<Replies, Integer> {



}
