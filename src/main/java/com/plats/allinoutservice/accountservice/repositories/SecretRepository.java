package com.plats.allinoutservice.accountservice.repositories;

import com.plats.allinoutservice.accountservice.pojo.Secret;
import org.springframework.data.repository.CrudRepository;

public interface SecretRepository extends CrudRepository<Secret, String> {

    boolean existsSecretByUsername(String username);
}
