package com.example.petProject.services.impl;

import com.example.petProject.models.Role;
import com.example.petProject.repositories.RoleRepository;
import com.example.petProject.services.RoleServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements RoleServiceInterface {
    @Autowired
    RoleRepository roleRepository;

    public Role findByName(String name)
    {
        return roleRepository.findByName("USER").orElse(null);
    }
}
