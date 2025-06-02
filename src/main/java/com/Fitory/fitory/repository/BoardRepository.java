package com.Fitory.fitory.repository;

import com.Fitory.fitory.entity.Board;
import com.Fitory.fitory.entity.Clike;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {


    Board findByPnum(Integer pnum);


    Page<Board> findByPcategoryAndPtitleContaining(String pcategory, String ptitle, Pageable pageable);

    Page<Board> findByPtitleContaining(String ptitle,Pageable pageable);

    @Modifying
    @Query("update Board b set b.plook=b.plook+1 where b.pnum= :pnum")
    void updateplook(@Param("pnum") Integer pnum);

    @Modifying
    @Query("update Board b set b.plike=b.plike+1 where b.pnum=:pnum ")
    void plike(@Param("pnum") Integer pnum);

    @Modifying
    @Query("update Board b set b.plike=b.plike-1 where b.pnum=:pnum")
    void bhate(Integer pnum);

    @Modifying
    @Query("update Board b set b.ptitle=:ptitle , b.pbody=:pbody , b.pcategory=:pcategory where b.pnum =:pnum ")
    void modpost(Integer pnum, String ptitle, String pcategory, String pbody);


    List<Board> findByUid(String uid);
}
