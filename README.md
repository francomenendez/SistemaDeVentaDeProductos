Aplicación en Java para sistema de venta de productos desarrollada como Trabajo Obligatorio de la materia Bases de Datos 3 en la Licenciatura en Informática en la UDE (Universidad de la Empresa)

El proyecto implementa una aplicación con arquitectura en 3 capas (3-Tier), utilizando Java, MySQL y archivos de texto plano como mecanismos de persistencia. La solución permite alternar dinámicamente entre ambos medios de persistencia mediante archivos de configuración, aplicando el patrón Abstract Factory, sin modificar la lógica de negocio.

Se hace uso de patrones de diseño vistos en el curso, tales como: 
-DAO
-Facade
-MVC
-Value Object
-Abstract Factory

La aplicación incluye manejo de concurrencia mediante Pool de Conexiones, tanto para la base de datos MySQL como para la persistencia en archivos de texto, implementando un esquema de lectores y escritores para el acceso concurrente a los archivos.

🔧 Características principales:
-Creación automática de la base de datos desde un programa main de prueba.
-Persistencia de productos y ventas en:
    -Base de datos MySQL
    -Archivos de texto plano (.txt)
-Cambio de mecanismo de persistencia sin afectar la fachada.
-Control de concurrencia en ambos mecanismos.
-Implementación completa de los ejercicios prácticos 4 y 5 del curso.

Proyecto desarrollado con fines académicos, enfocado en buenas prácticas de diseño, separación de responsabilidades y escalabilidad.