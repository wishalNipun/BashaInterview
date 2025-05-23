package com.Basha.Library.controller;

import com.Basha.Library.dto.BookDTO;
import com.Basha.Library.service.BookService.BookService;
import com.Basha.Library.util.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/insert")
    public ResponseEntity<Response> createBook(@RequestBody BookDTO dto) {
        return ResponseEntity.ok(bookService.insert(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<Response> updateBook(@RequestBody BookDTO dto) {
        return ResponseEntity.ok(bookService.update(dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Response> deleteBook(@RequestParam Long id) {
        return ResponseEntity.ok(bookService.deleteById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Response> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}

