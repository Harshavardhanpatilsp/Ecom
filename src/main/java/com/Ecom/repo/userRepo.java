package com.Ecom.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ecom.Model.user;

@Repository
public interface userRepo extends JpaRepository<user, Integer> {

}
