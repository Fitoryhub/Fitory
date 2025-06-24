package com.Fitory.fitory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApitokenDTO {
    private String access_token;
    private int expires_in;
    private String token_type;
    private String scope;
}
