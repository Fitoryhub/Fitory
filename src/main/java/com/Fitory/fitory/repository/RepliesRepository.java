package com.Fitory.fitory.repository;

import com.Fitory.fitory.DTO.RepliesDTO;
import com.Fitory.fitory.entity.Replies;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepliesRepository extends JpaRepository<Replies, Integer> {


    List<Replies> findByPnum(Integer num);

    @Modifying
    @Query("update Replies r set r.rlikes=r.rlikes+1 where r.rnum=:rnum")
    void update(@Param("rnum") Integer rnum);

    @Modifying
    @Query("update Replies r set r.rlikes=r.rlikes-1 where r.rnum=:rnum")
    void replieshate(@Param("rnum") Integer rnum);

    @Modifying
    @Query("update Replies r set r.rbody=:rbody where r.rnum=:rnum")
    void replymod(@Param("rnum") Integer rnum, @Param("rbody") String rbody);
}
