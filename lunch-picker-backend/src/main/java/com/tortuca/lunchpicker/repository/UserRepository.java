package com.tortuca.lunchpicker.repository;

import com.tortuca.lunchpicker.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    User findByUsername(String username);

    User findByUsernameAndActive(String username, boolean active);
}
