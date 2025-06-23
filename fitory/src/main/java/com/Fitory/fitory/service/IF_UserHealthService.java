package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.UserHealthInfoDTO;

public interface IF_UserHealthService {

    public void Infosave(UserHealthInfoDTO udto);

    public UserHealthInfoDTO findInfo(String id);

}
