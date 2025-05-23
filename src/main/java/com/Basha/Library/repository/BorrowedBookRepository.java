package com.Basha.Library.repository;

import com.Basha.Library.util.mapping.Book;
import com.Basha.Library.util.mapping.BorrowedBook;
import com.Basha.Library.util.mapping.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    @Query("SELECT b FROM BorrowedBook b WHERE b.user = :user AND b.book = :book AND b.returnDate IS NULL")
    Optional<BorrowedBook> findActiveBorrow(@Param("user") User user, @Param("book") Book book);
}
