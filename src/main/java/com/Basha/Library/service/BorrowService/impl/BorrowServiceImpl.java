package com.Basha.Library.service.BorrowService.impl;

import com.Basha.Library.dto.BorrowDTO;
import com.Basha.Library.repository.BookRepository;
import com.Basha.Library.repository.BorrowedBookRepository;
import com.Basha.Library.repository.UserRepository;
import com.Basha.Library.service.BorrowService.BorrowService;
import com.Basha.Library.util.Response.Response;

import com.Basha.Library.util.mapping.Book;
import com.Basha.Library.util.mapping.BorrowedBook;
import com.Basha.Library.util.mapping.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    private BorrowedBookRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Response borrowBook(BorrowDTO dto) {

        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        if (userOpt.isEmpty()) {
            return new Response("404", "User not found", null);
        }


        Optional<Book> bookOpt = bookRepository.findById(dto.getBookId());
        if (bookOpt.isEmpty()) {
            return new Response("404", "Book not found", null);
        }

        Book book = bookOpt.get();
        if (book.isAvailable()) {
            return new Response("400", "Book is not available", null);
        }


        Boolean isBorrowed = userOpt.get().getIsBorrowed();
        if (isBorrowed) {
            return new Response("400", "User has already borrowed a book", null);
        }


        BorrowedBook borrow = new BorrowedBook();
        borrow.setUser(userOpt.get());
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setReturnDate(LocalDate.now().plusDays(7));
        borrowRepository.save(borrow);

        userRepository.updateIsBorrowedStatus(userOpt.get().getId(),true);

        book.setAvailable(false);
        bookRepository.save(book);

       return new Response("200", "Book borrowed successfully", null);
    }

    @Override
    public Response returnBook(BorrowDTO dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        if (userOpt.isEmpty()) {
            return new Response("404", "User not found", null);
        }

        Optional<Book> bookOpt = bookRepository.findById(dto.getBookId());
        if (bookOpt.isEmpty()) {
            return new Response("404", "Book not found", null);
        }

        User user = userOpt.get();
        Book book = bookOpt.get();


        Optional<BorrowedBook> borrowOpt = borrowRepository
                .findActiveBorrow(user, book);

        if (borrowOpt.isEmpty()) {
            return new Response("404", "No active borrow record found", null);
        }

        BorrowedBook borrow = borrowOpt.get();
        borrow.setReturnDate(LocalDate.now());
        borrowRepository.save(borrow);


        book.setAvailable(true);
        bookRepository.save(book);


        userRepository.updateIsBorrowedStatus(user.getId(), false);

        return new Response("200", "Book returned successfully", null);
    }

    @Override
    public Response getAllBorrows() {
        List<BorrowDTO> borrowList = borrowRepository.findAll().stream().map(b -> {
            BorrowDTO dto = new BorrowDTO();
            dto.setBorrowId(b.getId());
            dto.setUserId(b.getUser().getId());
            dto.setName(b.getUser().getName());
            dto.setEmail(b.getUser().getEmail());
            dto.setBookId(b.getBook().getId());
            dto.setBookTitle(b.getBook().getTitle());
            dto.setIsbn(b.getBook().getIsbn());
            dto.setBorrowDate(b.getBorrowDate());
            dto.setReturnDate(b.getReturnDate());
            return dto;
        }).collect(Collectors.toList());

        return new Response("200", "All borrow records listed", Collections.singletonList(borrowList));
    }

    @Override
    public Response getBorrowsWithExtendedReturnDates() {
        List<BorrowDTO> extendedBorrows = borrowRepository.findAll().stream()
                .filter(b -> {
                    LocalDate borrowDate = b.getBorrowDate();
                    LocalDate returnDate = b.getReturnDate();
                    return borrowDate != null && returnDate != null &&
                            returnDate.isAfter(borrowDate.plusDays(7));
                })
                .map(b -> {
                    BorrowDTO dto = new BorrowDTO();
                    dto.setBorrowId(b.getId());
                    dto.setUserId(b.getUser().getId());
                    dto.setName(b.getUser().getName());
                    dto.setEmail(b.getUser().getEmail());
                    dto.setBookId(b.getBook().getId());
                    dto.setBookTitle(b.getBook().getTitle());
                    dto.setIsbn(b.getBook().getIsbn());
                    dto.setBorrowDate(b.getBorrowDate());
                    dto.setReturnDate(b.getReturnDate());
                    return dto;
                })
                .collect(Collectors.toList());

        if (extendedBorrows.isEmpty()) {
            return new Response("204", "No borrowings exceeded 7 days", null);
        }

        return new Response("200", "Borrowings with extended return dates listed", Collections.singletonList(extendedBorrows));
    }


}
