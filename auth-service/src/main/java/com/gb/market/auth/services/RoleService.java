package com.gb.market.auth.services;

import com.gb.market.auth.entities.Role;
import com.gb.market.auth.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    //TODO доделать
    public Role getUserRole(){
        return roleRepository.findByName("ROLE_USER").get();
    }
}
