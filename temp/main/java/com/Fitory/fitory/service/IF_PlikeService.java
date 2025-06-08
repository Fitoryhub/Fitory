package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Plike;

public interface IF_PlikeService {
    Plike findplike(String uid, Integer pnum);

    void saveplike(Plike plike);

    void phate(Integer pnum, String uid);
}
