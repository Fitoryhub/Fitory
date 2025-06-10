package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Plike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface PlikeRepository extends JpaRepository<Plike, Integer>  {


    Plike findByUid(String uid);

    Plike findByUidAndPnum(String uid, Integer pnum);

    Plike findByUidAndDnum(String uid, int dnum);

    @Modifying
    void deleteByPnumAndUid(Integer pnum, String uid);

    public void deleteAllByDnum(int did);
}
