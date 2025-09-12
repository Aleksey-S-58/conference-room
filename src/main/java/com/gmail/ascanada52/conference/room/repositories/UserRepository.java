package com.gmail.ascanada52.conference.room.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.ascanada52.conference.room.entities.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByLogin(String login);
    void deleteByLogin(String login);
    List<User> findByIdIn(List<Integer> ids);
    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    List<User> findByNameLike(@Param("name") String name);
    @Query("SELECT u FROM User u WHERE u.login LIKE %:login%")
    List<User> findByLoginLike(@Param("login") String login);
    @Query("SELECT u FROM User u WHERE u.name LIKE %:name% and u.login LIKE %:login%")
    List<User> findByNameLikeAndLoginLike(@Param("name") String name, @Param("login") String login);
}
