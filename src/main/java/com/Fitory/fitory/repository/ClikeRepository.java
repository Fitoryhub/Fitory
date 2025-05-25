package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Clike;
import com.Fitory.fitory.entity.Plike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClikeRepository extends JpaRepository<Clike, Integer> {


    void deleteByCnumAndUid(Integer cnum, String uid);

    List<Clike> findByPnum(Integer num);
}
