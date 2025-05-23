package com.Basha.Library.service.UserService;


import com.Basha.Library.dto.UserDTO;
import com.Basha.Library.service.CrudService;
import com.Basha.Library.util.Response.Response;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService,CrudService<UserDTO, Long> {
    Response getAllUsers();
}
