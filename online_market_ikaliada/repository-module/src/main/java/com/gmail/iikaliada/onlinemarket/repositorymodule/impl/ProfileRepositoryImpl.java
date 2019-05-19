package com.gmail.iikaliada.onlinemarket.repositorymodule.impl;

import com.gmail.iikaliada.onlinemarket.repositorymodule.ProfileRepository;
import com.gmail.iikaliada.onlinemarket.repositorymodule.model.Profile;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepositoryImpl extends GenericRepositoryImpl<Long, Profile> implements ProfileRepository {
}
