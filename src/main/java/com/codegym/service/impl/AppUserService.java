package com.codegym.service.impl;

import com.codegym.model.entity.AppUser;
import com.codegym.model.entity.UserPrinciple;
import com.codegym.repository.IAppUserRepository;
import com.codegym.service.IAppUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService implements IAppUserService {

    private final IAppUserRepository userRepository;

    public AppUserService(IAppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<AppUser> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Iterable<AppUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(AppUser appUser) {
        userRepository.save(appUser);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> userOptional = userRepository.findByName(username);
        AppUser appUser = userOptional.get();
        if (appUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return UserPrinciple.build(appUser);
    }
}
