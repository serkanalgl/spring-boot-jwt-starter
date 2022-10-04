package com.serkanalgl.jwt.starter.user.repository;

import com.serkanalgl.jwt.starter.user.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    Optional<User> findByEmail(String email);

}
