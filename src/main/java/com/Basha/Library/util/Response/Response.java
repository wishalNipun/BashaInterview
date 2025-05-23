package com.Basha.Library.util.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private String code;
    private String message;
    private List<Object> data;

}
