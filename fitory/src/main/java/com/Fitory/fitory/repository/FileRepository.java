package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<Files, Integer> {

    List<Files> findByPnum(Integer pnum);

    List<Files> findByDnum(Integer dnum);

    void deleteByPnum(Integer pnum);

    void deleteByDnum(Integer dnum);
}
