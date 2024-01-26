package com.youcode.ProMegaEnergie.services.interfaces;


import com.youcode.ProMegaEnergie.models.Enums.RoleUser;

import java.util.List;

public interface AdminService{
    List getAll(RoleUser roleUser);

    void deleteById(RoleUser roleUser, Long id);

    Object getById(RoleUser roleUser, Long id);

    Object create(RoleUser roleUser, Object object);

    Object update(RoleUser roleUser, Object object);
}
