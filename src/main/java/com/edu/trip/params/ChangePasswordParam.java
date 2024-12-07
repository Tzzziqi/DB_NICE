package com.edu.trip.params;

import lombok.Data;

@Data
public class ChangePasswordParam {

    private String oldPassword;
    private String newPassword;

}
