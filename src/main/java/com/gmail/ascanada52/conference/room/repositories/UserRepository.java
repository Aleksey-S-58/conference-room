package com.gmail.ascanada52.conference.room.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.ascanada52.conference.room.entities.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByLogin(String login);
    void deleteByLogin(String login);
}
