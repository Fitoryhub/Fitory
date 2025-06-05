package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Rlikes;

import java.util.List;

public interface IF_RlikeService {

    List<Rlikes> findrlike(Integer num);

    void rlikesave(Rlikes rlikes);

    void rhate(String uid, Integer rnum);
}
