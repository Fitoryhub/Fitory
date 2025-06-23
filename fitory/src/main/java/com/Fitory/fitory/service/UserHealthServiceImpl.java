package com.Fitory.fitory.service;

import com.Fitory.fitory.dto.UserDTO;
import com.Fitory.fitory.dto.UserHealthInfoDTO;
import com.Fitory.fitory.entity.User;
import com.Fitory.fitory.entity.UserHealthProfile;
import com.Fitory.fitory.repository.UserHealthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserHealthServiceImpl implements IF_UserHealthService{

    @Autowired
    UserHealthRepository userHealthRepository;

    @Override
    public void Infosave(UserHealthInfoDTO udto) {
        UserHealthProfile ufile = new UserHealthProfile();
        ufile.update_profile(udto);
        userHealthRepository.save(ufile);
    }

    @Override
    public UserHealthInfoDTO findInfo(String id) {
        Optional<UserHealthProfile> optionalUser2 = userHealthRepository.findById(id);
        UserHealthInfoDTO udto = new UserHealthInfoDTO();
        if (optionalUser2.isPresent()) {
            UserHealthProfile user = optionalUser2.get();
            udto.update_user(user);
            return udto;
        } else {
            return null;
        }
    }

}
