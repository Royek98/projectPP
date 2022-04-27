package com.rojek.projectpp.Security.Repositories;

import com.rojek.projectpp.Security.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
}
