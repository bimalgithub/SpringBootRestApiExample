package com.itg.springbootRestApi.repository;


import com.itg.springbootRestApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {

}
