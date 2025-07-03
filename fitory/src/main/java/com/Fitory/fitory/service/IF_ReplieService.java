package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.RepliesDTO;
import com.Fitory.fitory.entity.Replies;

import java.util.List;

public interface IF_ReplieService {
    List<RepliesDTO> findreplies(Integer num , String uid);

    void repliessave(Replies replies);


    void replieslike(Integer rnum);

    int rlike(Integer rnum);

    void replieshate(Integer rnum);

    void repliedelete(Integer rnum);

    void replymod(Replies replie);
}
