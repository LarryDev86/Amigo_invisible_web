##Amigo Invisible – Aplicación Web con Spring Boot

Aplicación web desarrollada con Spring Boot que automatiza el proceso de “Amigo Invisible” mediante asignaciones automáticas y envío de correos electrónicos personalizados.

El usuario selecciona su nombre desde un formulario, introduce su email y recibe automáticamente un correo con la persona a la que debe regalar.

El sistema controla que cada participante solo pueda jugar una vez, ocultándolo automáticamente del formulario tras completar el proceso.

Además, incluye un panel de administración para reiniciar los participantes y reutilizar la aplicación en futuros sorteos familiares.

##Tecnologías
Java 17
Spring Boot
Spring Data JPA
Base de datos relacional
Jakarta Mail (SMTP)
Thymeleaf
HTML
CSS
Maven
Funcionalidades
Participantes
Carga automática de participantes desde base de datos
Selección de usuario desde formulario web
Introducción de email para recibir el resultado
Asignación automática de amigo invisible
Envío de correo personalizado con el resultado
Actualización automática del estado del participante (disponible = false)
Ocultación automática de usuarios ya participantes
Panel de Administración

Acceso exclusivo para administrador.

##Permite:

Reiniciar participantes para nuevas ediciones
Volver a habilitar usuarios ocultos
Gestionar el estado del sorteo anual
Arquitectura

##El proyecto sigue una arquitectura en capas:

controller → gestión de rutas y peticiones web
service → lógica de negocio y reglas del sorteo
repository → acceso a base de datos mediante JPA
entity → modelo persistente
Estructura principal
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
Flujo de funcionamiento
El usuario accede al formulario principal
Selecciona su nombre de la lista disponible
Introduce su dirección de correo electrónico
El sistema genera automáticamente una asignación válida
Se almacena el resultado en base de datos
El usuario pasa a estado disponible = false
Se envía automáticamente un email con el resultado del sorteo
Vistas disponibles
formulario.html → formulario principal
vista.html → resultado correcto
error.html → errores de validación
vistaAdmin.html → panel de administración
Objetivo del proyecto

##Este proyecto fue desarrollado como práctica de:

Desarrollo backend con Spring Boot
Persistencia de datos con JPA
Gestión de lógica de negocio
Automatización mediante correo electrónico
Arquitectura en capas
Desarrollo de aplicaciones web server-side con Thymeleaf
Estado actual
Funcional
Sistema de selección operativo
Persistencia de participantes
Envío automático de emails
Gestión de disponibilidad
Panel de administración
Autor

Proyecto desarrollado con fines formativos y de uso familiar anual.
