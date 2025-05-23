package com.Basha.Library.service;

import com.Basha.Library.util.Response.Response;

public interface CrudService<T, ID> {
    Response insert(T dto);

    Response update(T dto);

    Response findById(ID id);

    Response deleteById(ID id);
}
