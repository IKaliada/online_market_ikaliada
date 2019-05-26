package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.RoleRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Role;
import com.gmail.iikaliada.onlinemarket.servicemodule.RoleService;
import com.gmail.iikaliada.onlinemarket.servicemodule.converter.RoleConverter;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    @Transactional
    public List<RoleDTO> getRoles() {
        List<Role> roles = roleRepository.getRoles();
        return roles.stream().map(roleConverter::toRoleDTO).collect(Collectors.toList());
    }
}

