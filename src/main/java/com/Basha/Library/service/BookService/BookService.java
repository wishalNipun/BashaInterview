package com.Basha.Library.service.BookService;

import com.Basha.Library.dto.BookDTO;

import com.Basha.Library.service.CrudService;
import com.Basha.Library.util.Response.Response;

public interface BookService extends CrudService<BookDTO, Long> {
    Response getAllBooks();
}
