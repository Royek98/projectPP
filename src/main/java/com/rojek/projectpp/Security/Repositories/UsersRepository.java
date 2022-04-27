package com.rojek.projectpp.Security.Repositories;

import com.rojek.projectpp.Security.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findUsersByLogin(String login);

    Optional<Users> findUsersByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Users u set u.enabled = true WHERE u.email =?1")
    int enableUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Users u set u.locked = false WHERE u.id =?1")
    int unlockUser(Long id);
}
