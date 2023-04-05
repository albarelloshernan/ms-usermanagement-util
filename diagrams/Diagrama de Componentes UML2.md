@startuml

node "REST API" {
	[POST /sign-up]
}

package "Spring Boot" {

	component "UsermanagementApplication" {
		port p8080

		folder "model" {
			[User]
			[User] -down-> [UserPhones]
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
			[GlobalExceptionHandler] <-down- [AccountException]
		}

		folder "service" {
			interface "AccountService" AS ACS
			[AccountServiceImpl] -left- ACS
		}

		folder "controller" {
			interface "AccountService" AS ACS
			[AccountCntroller] -right-> ACS
		}

		folder "dto" {
			[AccountReqDto]
			[AccountUsrPhonesReqDto]
			[AccountRespDto]
			[AccountErrorDto]
		}
		
		folder "config" {
			[SwaggerConfig]
		}
	}
}

database "H2 DB" {
    [USERS]
	[USERS] -down-> [PHONES]
}

[POST /sign-up] .down.> p8080
[AccountCntroller] <.up. p8080
[SwaggerConfig] -left-> [AccountCntroller]
[AccountCntroller] <-down- [AccountReqDto]
[AccountCntroller] <-down- [AccountUsrPhonesReqDto]
[AccountCntroller] <-down- [AccountRespDto]
[AccountCntroller] <-down- [AccountErrorDto]
[AccountServiceImpl] <-down- [AccountDtoConverter]
[AccountServiceImpl] <-down- [AccountEntityConverter]
[AccountServiceImpl] <-down- [GlobalExceptionHandler]
[AccountServiceImpl] -left-> AccountRepository
AccountRepository -down-> [User]
[User] -left-> [USERS]

@enduml
