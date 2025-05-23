package com.Basha.Library.service.BorrowService;

import com.Basha.Library.dto.BorrowDTO;
import com.Basha.Library.util.Response.Response;

public interface BorrowService {
    Response borrowBook(BorrowDTO dto);

    Response returnBook(BorrowDTO dto);

    Response getAllBorrows();

    Response getBorrowsWithExtendedReturnDates();
}
