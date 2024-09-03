package com.codegym.service.impl;

import com.codegym.model.entity.AppRole;
import com.codegym.repository.IAppRoleRepository;
import com.codegym.service.IAppRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppRoleService implements IAppRoleService {

    private final IAppRoleRepository appRoleRepository;

    public AppRoleService(IAppRoleRepository appRoleRepository) {
        this.appRoleRepository = appRoleRepository;
    }


    @Override
    public Page<AppRole> findAll(Pageable pageable) {
        return appRoleRepository.findAll(pageable);
    }

    @Override
    public Iterable<AppRole> findAll() {
        return appRoleRepository.findAll();
    }

    @Override
    public Optional<AppRole> findById(Long id) {
        return appRoleRepository.findById(id);
    }

    @Override
    public void save(AppRole appRole) {
        appRoleRepository.save(appRole);
    }

    @Override
    public void remove(Long id) {
        appRoleRepository.deleteById(id);
    }
}
