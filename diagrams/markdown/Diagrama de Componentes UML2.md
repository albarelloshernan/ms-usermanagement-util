@startuml

node "REST API" {
[POST /sign-up]
}

package "Spring Boot" {

	component "UsermanagementApplication" {
		port p8080

		folder "model" {
			[User] <-down- [UserPhones]
		}

		folder "repository" {
			interface "AccountRepository"
		}

		folder "converter" {
			interface "Converter" as CV
			[AccountDtoConverter] -left- CV
			[AccountEntityConverter] -right- CV
			[AccountUsrPhonesConverter] -up- CV
		}

		folder "exception" {
			[CustomException]
		}

		folder "service" {
			interface "AccountService" AS ACS
			[AccountServiceImpl] -left- ACS
		}

		folder "security" {
			[JwtTokenFilter]
			[JwtTokenFilterConfigurer]
			[JwtTokenProvider]
			[UserDetails]
		}

		folder "dto" {
			[AccountReqDto]
			[AccountUsrPhonesReqDto]
			[AccountRespDto]
			[AccountErrorDto]
		}
		
		folder "controller" {
			interface "AccountService" AS ACS
			[AccountCntroller] -right-> ACS
			[ExceptionHandleController]
		}
		
		folder "config" {
			[SwaggerConfig]
			[WebSecurityConfig]
		}
	}

}

database "H2 DB" {
[USERS] <-down- [PHONES]
}

[POST /sign-up] .down.> p8080
[AccountCntroller] <.up. p8080

[JwtTokenProvider] -down-> [WebSecurityConfig]
[JwtTokenProvider] <-down- [JwtTokenFilter]
[JwtTokenProvider] <-down- [JwtTokenFilterConfigurer]
[JwtTokenFilter] -right-> [JwtTokenFilterConfigurer]

[UserDetails] -right-> [JwtTokenProvider]
[UserDetails] -left-> AccountRepository

[SwaggerConfig] -left-> [AccountCntroller]
[WebSecurityConfig] -up-> [AccountCntroller]

[AccountCntroller] <-down- [AccountReqDto]
[AccountCntroller] <-down- [AccountUsrPhonesReqDto]
[AccountCntroller] <-down- [AccountRespDto]
[AccountCntroller] <-down- [AccountErrorDto]

[AccountServiceImpl] <-down- [JwtTokenProvider]
[AccountServiceImpl] <-down- [AccountDtoConverter]
[AccountServiceImpl] <-down- [AccountEntityConverter]
[AccountServiceImpl] <-down- [CustomException]
[AccountServiceImpl] -left-> AccountRepository

AccountRepository -down-> [User]
[User] <-left- [USERS]

@enduml
