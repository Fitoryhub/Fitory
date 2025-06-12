package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Rlikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RlikeRepository extends JpaRepository<Rlikes, Integer> {
    List<Rlikes> findByPnum(Integer num);

        void deleteByUidAndRnum(String uid, Integer rnum);
}
