package com.rojek.projectpp.Security.Services;

import com.rojek.projectpp.Security.Model.Roles;
import com.rojek.projectpp.Security.Repositories.RolesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class RolesService {

    private final RolesRepository rolesRepository;

    Roles getRoleUser() {
        return rolesRepository.findById(2l).get();
    }
}
