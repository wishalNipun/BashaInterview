package com.Basha.Library.repository;

import com.Basha.Library.util.mapping.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isBorrowed = :isBorrowed WHERE u.id = :userId")
    void updateIsBorrowedStatus(Long userId, Boolean isBorrowed);
}
