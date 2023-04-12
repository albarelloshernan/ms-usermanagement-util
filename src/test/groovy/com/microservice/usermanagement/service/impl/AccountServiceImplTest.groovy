package com.microservice.usermanagement.service.impl


import com.microservice.usermanagement.dto.req.AccountReqDto
import com.microservice.usermanagement.repository.AccountRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Unroll

class AccountServiceImplTest extends Specification {

    AccountServiceImpl accountServiceImplMock
    AccountRepository accountRepositoryMock
    //agregar resto de dependencias (se mockean), el service se inicializa, los tests agregar los datos y dependencias en el given

    def setup() {
        accountServiceImplMock = Mock(AccountServiceImpl)
        accountRepositoryMock = Mock(AccountRepository)
    }

    @Unroll
    def "Service SignUp returns #statusCode and #reasonPhrase on response"() {
        given:
        AccountReqDto request = Mock()
        ResponseEntity<Object> responseEntity = Mock() {
            getStatusCode() >> httpStatus
            getBody() >> reasonPhrase
        }

        when:
        def response = accountServiceImplMock.signUp(request)

        then:
        response == responseEntity

        where:
        httpStatus                          | statusCode    |   reasonPhrase
        HttpStatus.OK                       | 200           |   "OK"
        HttpStatus.NOT_FOUND                | 404           |   "User not found."
        HttpStatus.INTERNAL_SERVER_ERROR    | 500           |   "Error al insertar datos."
    }

    @Unroll
    def "Service LogIn returns #statusCode and #reasonPhrase on response"() {
        given:
        def token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcm9iYWRvckBnbWFpbC5jb20iLCJwYXNzd29yZCI6ImEyYXNmR2ZkZmRmN" +
                "CIsImlhdCI6MTY4MDk5OTY3MSwiZXhwIjoxNjgxMDAzMjcxfQ.4jFatDRd81mV7PmS_rVoBlQ1Upslx4V90w1kUT13Oa4"
        def username = "probador@gmail.com"
        def password = "a2asfGfdfdf4"
        ResponseEntity<Object> responseEntity = Mock() {
            getStatusCode() >> httpStatus
            getBody() >> reasonPhrase
        }

        when:
        def response = accountServiceImplMock.logIn(token,username,password)

        then:
        response == responseEntity

        where:
        httpStatus                          | statusCode    |   reasonPhrase
        HttpStatus.OK                       | 200           |   "OK"
        HttpStatus.NOT_FOUND                | 404           |   "User not found."
        HttpStatus.UNPROCESSABLE_ENTITY     | 422           |   "Wrong username or password."

    }
}
