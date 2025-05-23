package com.Basha.Library.service.UserService;

import com.Basha.Library.dto.LoginDTO;
import com.Basha.Library.dto.UserDTO;
import com.Basha.Library.service.CrudService;
import com.Basha.Library.util.Response.Response;

public interface UserService extends CrudService<UserDTO, Long> {
    Response getAllUsers();
    Response login(LoginDTO loginDTO);
}
