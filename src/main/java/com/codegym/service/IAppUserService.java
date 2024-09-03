package com.codegym.service;

import com.codegym.model.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAppUserService extends IGenerateService<AppUser>, UserDetailsService {
}
