# 🍔 Food Store - Sistema de Gestión de Pedidos

Proyecto Integrador para la materia **Programación 2** (Tecnicatura Universitaria en Programación - UTN). Esta aplicación de consola gestiona el flujo completo de ventas de un comercio gastronómico, utilizando persistencia en base de datos relacional y una arquitectura profesional.

## 🛠️ Requisitos Previos
* **Java:** Versión 21 o superior.
* **Base de Datos:** MySQL Server 8.0+.
* **Gestor de Dependencias:** Maven.

## 🚀 Instalación y Configuración

### 1. Preparar la Base de Datos
1. Abra su cliente MySQL (Workbench o consola).
2. Ejecute el script SQL ubicado en `src/main/resources/schema.sql`. Esto creará la base de datos `food_store` y las tablas necesarias.
3. (Opcional) Ejecute los datos de prueba si desea testear el sistema inmediatamente.

### 2. Configurar la Conexión
El proyecto utiliza un archivo de configuración centralizado:
1. Diríjase a `src/main/resources/persistence.xml`.
2. Edite las etiquetas `<user>` y `<password>` con sus credenciales locales de MySQL.
3. El sistema leerá automáticamente estos datos al iniciar.

### 3. Ejecución
1. Realice un `Clean and Build` del proyecto para descargar el driver de MySQL mediante Maven.
2. Ejecute la clase `com.foodstore.Main`.

## 📂 Estructura del Proyecto (Capas)
- `entities`: Modelos de datos con herencia de la clase `Base`.
- `dao`: Persistencia mediante patrón DAO e interfaz genérica.
- `services`: Lógica de negocio y gestión de **Transacciones (Commit/Rollback)**.
- `ui`: Interfaz de usuario por consola y validación de entrada.
- `config`: Conexión Singleton con lectura de XML.

## Enlace al video: (ACA VA EL LINK DEL VIDEO)

## 👥 Integrantes
* **Gustavo Cortés** 
* **Santiago Villalba** 
* **Jorge Lorenzo** 
* **Facundo Flores** 
