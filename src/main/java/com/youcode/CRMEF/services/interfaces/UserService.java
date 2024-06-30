package com.youcode.CRMEF.services.interfaces;

import com.youcode.CRMEF.models.Dtos.UserDto.UserDto;
import com.youcode.CRMEF.models.Dtos.ValidationDto.ValidationDto;
import com.youcode.CRMEF.models.Enums.RoleUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional Login (UserDto userDto);
    Boolean activateAccount (ValidationDto validationDto);

    RoleUser sendCodeForgetPassword(String email);

    Boolean updatePassword(ValidationDto validationDto, String newPassword);

    Boolean signUp(Object userObject);

    List getAll(RoleUser roleUser);

    void deleteById(RoleUser roleUser, Long id);

    Object getById(RoleUser roleUser, Long id);

    Object create(RoleUser roleUser, Object object);

    Object update(RoleUser roleUser, Object object);
}
