
# User management microservice

Microservicio para registro y consulta de usuarios autenticados.

## Características

- Registro de nuevos usuarios.
- Generación de JWT token.
- Login de usuarios creados con autenticación.

---

## Diagramas UML

Los diagramas se encuentran dentro del proyecto en la carpeta diagrams/

### Diagrama de componentes
![Diagrama de componentes](diagrams/Diagrama%20de%20Componentes%20UML2.png)

### Diagrama de secuencia
![Diagrama de secuencia](diagrams/Diagrama%20de%20Secuencia%20UML.png)

Nota: Una versión posterior implementará las características de autenticación y consulta. 

---

## Instrucciones de construcción

**Spring Initializr:** A través del portal https://start.spring.io/ se puede iniciar la construcción de una aplicación introduciendo el nombre del proyecto, sus coordenadas gradle y realizando la selección de las dependencias que conocemos de antemano que va a necesitar nuestro proyecto. Estas dependencias únicamente nos las agregará al fichero pom.xml por lo que en caso de no agregarlas en la definición del proyecto podremos agregarlas manualmente con gran facilidad. Para nuestro primero proyecto el formulario quedará del siguiente modo:

![Construcción de proyecto](diagrams/Construcción%20de%20proyecto.png)

Adicionalmente se agregarán manualmente las siguientes dependencias el el archivo build.gradle:

Reglas de validación de restricciones de Hibernate:

	implementation group: 'org.hibernate.validator', name: 'hibernate-validator', version: '8.0.0.Final'

Documentation de API JSON para aplicaciones basadas en spring:

	implementation 'io.springfox:springfox-boot-starter:3.0.0'

Reglas de validación para API JSON:

    implementation group: 'commons-validator', name: 'commons-validator', version: '1.7'

---

## Instrucciones de ejecución

Para ejecutar el proyecto primero se deben editar las run configurations en el desplegable superior:

![Ejecución de proyecto 1](diagrams/Ejecución%20de%20proyecto%201.png)

A continuación se edita el comando build indicando en el input las tasks `clean build`.
Luego se edita una segunda configuración con el comando `bootRun` para ejecutar. Ejemplo:

![Ejecución de proyecto 2](diagrams/Ejecución%20de%20proyecto%202.png)

Finalmente se lanza la aplicación:

![Ejecución de proyecto 3](diagrams/Ejecución%20de%20proyecto%203.png)

---

## Referencia de API

#### Registro de usuario

```http
  POST /ms-user-management/sign-up
```

| Body       | Tipo     | Descripción                            |
|:-----------|:---------|:---------------------------------------|
| `name`     | `string` | **Opcional**. Nombre de usuario.       |
| `email`    | `string` | **Requerido**. Email del usuario.      |
| `password` | `string` | **Requerido**. Contraseña del usuario. |
| `phones`   | `[]`     | **Opcional**. Teléfonos del usuario.   |

#### Consulta de usuario

```http
  GET /ms-user-management/login
```

| Auth    | Tipo        | Descripción                                                          |
|:--------|:------------|:---------------------------------------------------------------------|
| `token` | `JWT token` | **Requerido**. Token que autentica y retrae información del usuario. |



