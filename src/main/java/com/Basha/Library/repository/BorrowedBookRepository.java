package com.Basha.Library.repository;

import com.Basha.Library.util.mapping.Book;
import com.Basha.Library.util.mapping.BorrowedBook;
import com.Basha.Library.util.mapping.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    Optional<BorrowedBook> findByUser(User user);
    boolean existsByUser(User user);
    boolean existsByBook(Book book);
}
