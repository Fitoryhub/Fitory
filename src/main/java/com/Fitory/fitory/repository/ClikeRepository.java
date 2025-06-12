package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Clike;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClikeRepository extends JpaRepository<Clike, Integer> {

    @Modifying
    @Query("delete from Clike c where c.uid=:uid and c.cnum=:cnum")
    void deleteByCnumAndUid(@Param("cnum")Integer cnum,@Param("uid")String uid);

    List<Clike> findByPnum(Integer num);
}
