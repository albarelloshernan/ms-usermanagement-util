package com.microservice.usermanagement.controller;

import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.dto.resp.AccountErrorDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AccountController.URI)
@Tag(name = "account", description = "Account Controller")
@Validated
public class AccountController {
    public static final String URI = "/ms-user-management";
    public static final String PATH = "/sign-up";
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
    @PostMapping(path= PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountRespDto> signUp(@Valid @RequestBody final AccountReqDto dto) {
        AccountRespDto response = new AccountRespDto();
        response = accountServiceImpl.signUp(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
