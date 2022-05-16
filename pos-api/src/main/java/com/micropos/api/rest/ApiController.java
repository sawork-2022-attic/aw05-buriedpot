package com.micropos.api.rest;

import com.micropos.api.CartsApi;
import com.micropos.api.service.ApiService;
import com.micropos.dto.CartDto;
import com.micropos.dto.CartItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiController implements CartsApi {

    private ApiService cartService;

}
