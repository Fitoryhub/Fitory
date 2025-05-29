package com.Fitory.fitory.repository;

import com.Fitory.fitory.DTO.CommentDTO;
import com.Fitory.fitory.entity.Comment;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {


    List<Comment> findByPnumOrderByCnumAsc(Integer num);

    @Modifying
    @Query("update Comment c set c.clike=c.clike+1 where c.cnum=:cnum")
    void clike(@Param("cnum") Integer cnum);

    @Modifying
    @Query("update Comment c set c.clike=c.clike-1 where c.cnum=:cnum")
    void chate(@Param("cnum") Integer cnum);

    @Modifying
    @Query("update Comment c set c.cbody=:cbody where c.cnum=:cnum")
    void commentmod(Integer cnum, String cbody);

    Comment findByCnum(Integer cnum);
}
