package com.youcode.ProMegaEnergie.services.interfaces;

import com.youcode.ProMegaEnergie.models.Dtos.UserDto.UserDto;
import com.youcode.ProMegaEnergie.models.Dtos.ValidationDto.ValidationDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional Login (UserDto userDto);
    Boolean activateAccount (ValidationDto validationDto);

    Boolean sendCodeForgetPassword(UserDto userDto);

    Boolean updatePassword(ValidationDto validationDto, String newPassword);

    Boolean signUp(Object userObject);
}
