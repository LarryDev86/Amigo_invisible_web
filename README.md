# Amigo Invisible – Aplicación Web con Spring Boot

Aplicación web desarrollada con Spring Boot que automatiza el proceso de “Amigo Invisible” mediante asignaciones automáticas y envío de correos electrónicos personalizados.

El usuario selecciona su nombre desde un formulario, introduce su email y recibe automáticamente un correo con la persona a la que debe regalar.

El sistema controla que cada participante solo pueda jugar una vez, ocultándolo automáticamente del formulario tras completar el proceso.

Además, incluye un panel de administración para reiniciar los participantes y reutilizar la aplicación en futuros sorteos familiares.

---

# Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Base de datos relacional
- Jakarta Mail (SMTP)
- Thymeleaf
- HTML
- CSS
- Maven

---

# Funcionalidades

## Participantes

- Carga automática de participantes desde base de datos
- Selección de usuario desde formulario web
- Introducción de email para recibir el resultado
- Asignación automática de amigo invisible
- Envío de correo personalizado con el resultado
- Actualización automática del estado del participante (`disponible = false`)
- Ocultación automática de usuarios ya participantes

## Panel de administración

Acceso exclusivo para administrador.

El administrador puede realizar las siguientes acciones:

- Reiniciar participantes para nuevas ediciones
- Volver a habilitar usuarios ocultos
- Gestionar el estado del sorteo anual

---

# Arquitectura

El proyecto sigue una arquitectura en capas:

- `controller` → gestión de rutas y peticiones web
- `service` → lógica de negocio y reglas del sorteo
- `repository` → acceso a base de datos mediante JPA
- `entity` → modelo persistente

---

# Estructura principal

```plaintext
src/main/java/com/larryDev
├── controller
│   └── AmigoInvisibleController.java
│
├── entity
│   ├── Familiar.java
│   └── ContenedorAmigoSeleccionado.java
│
├── repository
│   ├── FamiliarRepository.java
│   └── ContenedorAmigoRepository.java
│
├── service
│   ├── FamiliarService.java
│   ├── ContenedorAmigoService.java
│   └── ClaseEmailService.java
│
└── AmigoInvisibleApplication.java
