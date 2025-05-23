package com.Basha.Library.service.BookService.impl;

import com.Basha.Library.dto.BookDTO;
import com.Basha.Library.repository.BookRepository;
import com.Basha.Library.service.BookService.BookService;
import com.Basha.Library.util.Response.Response;
import com.Basha.Library.util.mapping.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Response insert(BookDTO dto) {
        Optional<Book> existingBook = bookRepository.findByIsbn(dto.getIsbn());
        if (existingBook.isPresent()) {
            return new Response("409", "Book with this ISBN already exists!", null);
        }

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setQuantity(dto.getQuantity());

        bookRepository.save(book);
        return new Response("201", "Book added successfully", null);
    }

    @Override
    public Response update(BookDTO dto) {
        Optional<Book> optionalBook = bookRepository.findById(dto.getId());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            if (dto.getTitle() != null) book.setTitle(dto.getTitle());
            if (dto.getAuthor() != null) book.setAuthor(dto.getAuthor());
            if (dto.getIsbn() != null && !dto.getIsbn().equals(book.getIsbn())) {
                if (bookRepository.findByIsbn(dto.getIsbn()).isPresent()) {
                    return new Response("409", "ISBN already in use by another book", null);
                }
                book.setIsbn(dto.getIsbn());
            }
            book.setQuantity(dto.getQuantity());

            bookRepository.save(book);
            return new Response("200", "Book updated successfully", null);
        } else {
            return new Response("404", "Book not found", null);
        }
    }

    @Override
    public Response findById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BookDTO dto = new BookDTO(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getQuantity(),
                    book.getCreatedAt(),
                    book.getUpdatedAt()
            );
            return new Response("200", "Book found", Collections.singletonList(dto));
        } else {
            return new Response("404", "Book not found", null);
        }
    }

    @Override
    public Response deleteById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookRepository.deleteById(id);
            return new Response("200", "Book deleted successfully", null);
        } else {
            return new Response("404", "Book not found", null);
        }
    }

    @Override
    public Response getAllBooks() {
        List<BookDTO> books = bookRepository.findAll().stream().map(book -> new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getQuantity(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        )).collect(Collectors.toList());

        return new Response("200", "All books listed", Collections.singletonList(books));
    }
}
