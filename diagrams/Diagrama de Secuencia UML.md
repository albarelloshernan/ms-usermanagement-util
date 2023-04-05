@startuml
Cliente -> UserManagementApplication ++: \n POST /ms-user-management/sign-up \n {name, email, password, phones[]}\n 
UserManagementApplication->UserManagementApplication: \n Guarda los datos del usuario en la DB \n y se responde con el usuario creado.
UserManagementApplication->UserManagementApplication: \n La applicación genera JWT token \n usando secreto.
UserManagementApplication --> Cliente --: return {id, created, lastLogin, token, isActive}\n 

Cliente -> UserManagementApplication ++: \n\n POST /ms-user-management/login \n Request Header con token obtenido.\n 
UserManagementApplication->UserManagementApplication: \n Autenticación de firma del token. \n Si se autentica correctamente \n se da autorización, caso contrario no.
UserManagementApplication->UserManagementApplication: \n Con autorización obtenida \n consulta datos del usuario.
Cliente <-- UserManagementApplication --: return {id, created, lastLogin, token, isActive, \n name, email, password, phones[]}\n
@enduml
