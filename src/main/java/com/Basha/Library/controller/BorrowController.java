package com.Basha.Library.controller;

import com.Basha.Library.dto.BorrowDTO;
import com.Basha.Library.service.BorrowService.BorrowService;
import com.Basha.Library.util.Response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @PostMapping("/borrow")
    public ResponseEntity<Response> borrowBook(@RequestBody BorrowDTO dto) {
        return ResponseEntity.ok(borrowService.borrowBook(dto));
    }

    @PostMapping("/return")
    public ResponseEntity<Response> returnBook(@RequestBody BorrowDTO dto) {
        return ResponseEntity.ok(borrowService.returnBook(dto));
    }

    @GetMapping
    public ResponseEntity<Response> getAllBorrows() {
        return ResponseEntity.ok(borrowService.getAllBorrows());
    }

    @GetMapping("/extendedReturnDate")
    public ResponseEntity<Response> getBorrowsWithExtendedReturnDates() {
        return ResponseEntity.ok(borrowService.getBorrowsWithExtendedReturnDates());
    }
}
