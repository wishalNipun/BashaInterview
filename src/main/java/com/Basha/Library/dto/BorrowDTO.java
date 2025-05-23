package com.Basha.Library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowDTO {
    private Long borrowId;
    private Long userId;
    private String name;
    private String email;
    private Long bookId;
    private String isbn;
    private String bookTitle;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}
