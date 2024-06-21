# TEST INTERBANK

Este proyecto es un servicio backend desarrollado con Spring WebFlux para manejar clientes y sus productos financieros. A continuación, se proporcionan las instrucciones para configurar y ejecutar el proyecto en su máquina local utilizando Docker.

## Comenzando

Estas instrucciones le permitirán obtener una copia del proyecto en funcionamiento en su máquina local para propósitos de desarrollo y pruebas.

### Prerrequisitos

Asegúrese de tener instalados los siguientes software en su máquina:

- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Docker](https://www.docker.com/products/docker-desktop)
- [Docker Compose](https://docs.docker.com/compose/install/)

### Instalación

1. **IMPORTANTE**

    ```bash
    1.1: Primero cree un producto -> http://localhost:8090/swagger-doc/swagger-ui.html
    1.2: cree un cliente y pon el id de un producto existente  ->  http://localhost:8080/swagger-doc/swagger-ui.html
    1.3: Liste los clientes y copia el Código Único para poder filtrar con CU encriptado->  http://localhost:8090/swagger-doc/swagger-ui.html
    ```
 ****
2. **Clonar el repositorio**

    ```bash
    git clone https://github.com/jesuscangalaya1/test-interbank.git
    cd test-interbank
    ```

3. **Construir y ejecutar los contenedores Docker**

    ```bash
    docker-compose up --build
    ```
4. **Acceder a Swagger para probar ENDPOINT DE MS-BFF**
    ```
    http://localhost:9090/swagger-doc/swagger-ui.html
    ```

5. **Acceder a Swagger para probar ENDPOINT DE MS-CLIENT**
    ```
    http://localhost:8080/swagger-doc/swagger-ui.html
    ```

6. **Acceder a Swagger para probar ENDPOINT DE MS-PRODUCT**
    ```
    http://localhost:8090/swagger-doc/swagger-ui.html
    ```

   










