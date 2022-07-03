package com.plats.allinoutservice.accountservice.repositories;

import com.plats.allinoutservice.accountservice.pojo.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {


}
