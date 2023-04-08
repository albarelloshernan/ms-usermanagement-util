package com.microservice.usermanagement.controller;

import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.dto.resp.AccountErrorDto;
import com.microservice.usermanagement.dto.resp.AccountLoginRespDto;
import com.microservice.usermanagement.dto.resp.AccountRespDto;
import com.microservice.usermanagement.service.impl.AccountServiceImpl;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(AccountController.URI)
@Tag(name = "account", description = "Account Controller")
@Validated
public class AccountController {
    public static final String URI = "/ms-user-management";
    public static final String SIGN_UP = "/sign-up";
    public static final String LOGIN = "/login";
    private AccountServiceImpl accountServiceImpl;
    public AccountController(AccountServiceImpl accountServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccountRespDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccountErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccountErrorDto.class)))})
    @PostMapping(path= SIGN_UP, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> signUp(@Valid @RequestBody final AccountReqDto dto) {
        ResponseEntity<Object> response;
        response = accountServiceImpl.signUp(dto);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccountLoginRespDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccountErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected Error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccountErrorDto.class)))})
    @PostMapping(path= LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> logIn(@RequestHeader(AUTHORIZATION) String credentials,
                                                     @RequestParam String sub, @RequestParam String password) {
        ResponseEntity<Object> response;
        response = accountServiceImpl.logIn(credentials, sub, password);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
}
