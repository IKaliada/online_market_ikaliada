package com.gmail.iikaliada.onlinemarket.servicemodule.converter;

import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Role;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.RoleDTO;

public interface RoleConverter {

    RoleDTO toRoleDTO(Role role);

    Role fromRoleDTO(RoleDTO roleDTO);
}
