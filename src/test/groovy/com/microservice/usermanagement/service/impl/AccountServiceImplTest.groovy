package com.microservice.usermanagement.service.impl

import com.microservice.usermanagement.UserManagementApplication
import com.microservice.usermanagement.converter.AccountRespEntityConverter
import com.microservice.usermanagement.dto.req.AccountReqDto
import com.microservice.usermanagement.dto.resp.AccountLoginRespDto
import com.microservice.usermanagement.dto.resp.AccountRespDto
import com.microservice.usermanagement.repository.AccountRepository
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification
import spock.lang.Unroll

@ContextConfiguration(classes = UserManagementApplication.class)
@WebAppConfiguration
@SpringBootTest
class AccountServiceImplTest extends Specification {

    AccountServiceImpl accountServiceImplMock
    AccountRepository accountRepositoryMock
    AccountRespEntityConverter accountRespEntityConverterMock

    def setup() {
        accountServiceImplMock = Mock(AccountServiceImpl)
        accountRepositoryMock = Mock(AccountRepository)
        accountRespEntityConverterMock = Mock(AccountRespEntityConverter)
    }

    @Unroll
    def "SignUp"() {
        given:
        def req = new AccountReqDto()
        def resp = new AccountRespDto()
        def httpStatus = Mock(HttpStatus)

        when:
        def response = accountServiceImplMock.signUp(req)

        then:
        response == expected

        where:
        expected << new ResponseEntity(resp,httpStatus)
    }

    @Unroll
    def "LogIn"() {
        given:
        def resp = new AccountLoginRespDto()
        def httpStatus = Mock(HttpStatus)
        def token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcm9iYWRvckBnbWFpbC5jb20iLCJwYXNzd29yZCI6ImEyYXNmR2ZkZmRmN" +
                "CIsImlhdCI6MTY4MDk5OTY3MSwiZXhwIjoxNjgxMDAzMjcxfQ.4jFatDRd81mV7PmS_rVoBlQ1Upslx4V90w1kUT13Oa4"
        def username = "probador@gmail.com"
        def password = "a2asfGfdfdf4"

        when:
        def response = accountServiceImplMock.logIn(token,username,password)

        then:
        response == expected

        where:
        expected << new ResponseEntity(resp,httpStatus)

    }
}
