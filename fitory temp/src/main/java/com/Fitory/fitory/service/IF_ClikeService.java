package com.Fitory.fitory.service;

import com.Fitory.fitory.entity.Clike;

import java.util.List;

public interface IF_ClikeService {
    List<Clike> findclike(Integer num);

    void save( Clike clike);

    void delete(Clike clike);
}
