package com.microservice.usermanagement.service.impl

import com.microservice.usermanagement.converter.AccountDtoConverter
import com.microservice.usermanagement.converter.AccountRespEntityConverter
import com.microservice.usermanagement.dto.req.AccountReqDto
import com.microservice.usermanagement.dto.req.AccountUsrPhonesReqDto
import com.microservice.usermanagement.dto.resp.AccountErrorDto
import com.microservice.usermanagement.dto.resp.AccountErrorListDto
import com.microservice.usermanagement.dto.resp.AccountLoginRespDto
import com.microservice.usermanagement.repository.AccountRepository
import com.microservice.usermanagement.security.JwtTokenProvider
import com.microservice.usermanagement.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Unroll

class AccountServiceImplTest extends Specification {

    AccountService accountService
    JwtTokenProvider jwtTokenProviderMock
    PasswordEncoder passwordEncoderMock
    AuthenticationManager authenticationManagerMock
    AccountRepository accountRepositoryMock

    def setup() {
        jwtTokenProviderMock = Mock(JwtTokenProvider)
        passwordEncoderMock = Mock(PasswordEncoder)
        authenticationManagerMock = Mock(AuthenticationManager)
        accountRepositoryMock = Mock(AccountRepository)
        accountService = new AccountServiceImpl(jwtTokenProviderMock, passwordEncoderMock,
            authenticationManagerMock, accountRepositoryMock)
    }

    @Unroll
    def "Service SignUp returns response"() {
        given:
        def request = new AccountReqDto()
        def phones = new AccountUsrPhonesReqDto()
        request.setName("Probador")
        request.setEmail("probador@gmail.com")
        request.setPassword("a2asfGfdfdf4")
        phones.setNumber(1166778899)
        phones.setCityCode(9)
        phones.setCountryCode("+54")

        def userEntity = AccountDtoConverter.getInstance().fromDto(request)
        def respDto = AccountRespEntityConverter.getInstance().fromEntity(userEntity)

        accountRepositoryMock.existsByUsername(request.getName())
        jwtTokenProviderMock.createToken(request.getEmail(), request.getPassword())
        passwordEncoderMock.encode(request.getPassword())
        accountRepositoryMock.save(userEntity)
        accountRepositoryMock.findOneByEmail(request.getEmail())

        def errorDto = new AccountErrorDto()
        def errorListDto = new AccountErrorListDto()
        errorDto.setTimestamp("2023-04-12T18:15:25.386")
        errorDto.setCodigo(HttpStatus.NOT_FOUND.value())
        errorDto.setDetail("User not found.")
        errorListDto.setErrorMsg(errorDto)

        when:
        def response = accountService.signUp(request)

        then:
        response == respDto

    }

    @Unroll
    def "Service LogIn returns response"() {
        given:
        def bearerToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcm9iYWRvckBnbWFpbC5jb20iLCJwYXNzd29yZCI6ImE" +
                "yYXNmR2ZkZmRmNCIsImlhdCI6MTY4MDk5OTY3MSwiZXhwIjoxNjgxMDAzMjcxfQ.4jFatDRd81mV7PmS_rVoBlQ1Upslx" +
                "4V90w1kUT13Oa4"
        def token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcm9iYWRvckBnbWFpbC5jb20iLCJwYXNzd29yZCI6ImEyYXNmR2ZkZmRmN" +
                "CIsImlhdCI6MTY4MDk5OTY3MSwiZXhwIjoxNjgxMDAzMjcxfQ.4jFatDRd81mV7PmS_rVoBlQ1Upslx4V90w1kUT13Oa4"
        def username = "probador@gmail.com"
        def password = "a2asfGfdfdf4"

        def respDto = new AccountLoginRespDto()
        def errorDto = new AccountErrorListDto()

        jwtTokenProviderMock.resolveToken(bearerToken)
        jwtTokenProviderMock.getUsername(token)
        authenticationManagerMock.authenticate(new UsernamePasswordAuthenticationToken(username, password))
        def optionalUser = accountRepositoryMock.findOneByEmail(username)
        jwtTokenProviderMock.createToken(username, password)
        accountRepositoryMock.saveAndFlush(Optional.of(optionalUser))

        when:
        def response = accountService.logIn(token,username,password)

        then:
        response == respDto

    }
}
