# 📚 Sistema de Gestión de Libros

Aplicación full-stack para gestionar tu biblioteca personal con autenticación JWT, CRUD completo y diseño responsive.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-green)
![React](https://img.shields.io/badge/React-18.2.0-blue)
![TypeScript](https://img.shields.io/badge/TypeScript-5.2.2-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18.1-blue)

## 🎯 Características

- ✅ Autenticación segura con JWT
- ✅ Registro e inicio de sesión de usuarios
- ✅ CRUD completo de libros
- ✅ Búsqueda de libros por título, autor o género
- ✅ Control de disponibilidad (disponible/prestado)
- ✅ Estadísticas visuales de la biblioteca
- ✅ Exportar datos a CSV
- ✅ Modo oscuro/claro
- ✅ Diseño responsive
- ✅ Animaciones suaves
- ✅ Loading states con skeleton loader

## 🛠️ Stack Tecnológico

### Backend
- **Java 21** - Lenguaje de programación
- **Spring Boot 3.5.0** - Framework backend
- **Spring Security** - Autenticación y autorización
- **JWT (jjwt 0.12.3)** - Tokens de autenticación
- **JPA/Hibernate** - ORM
- **PostgreSQL 18.1** - Base de datos
- **Lombok** - Reducción de boilerplate
- **Maven** - Gestión de dependencias

### Frontend
- **React 18.2.0** - Biblioteca UI
- **TypeScript 5.2.2** - Tipado estático
- **Vite 5.0.8** - Build tool y dev server
- **React Router 6.20.1** - Navegación
- **Axios 1.6.2** - Cliente HTTP
- **React Icons 4.12.0** - Iconos
- **CSS Variables** - Theming dinámico

## 📦 Instalación

### Requisitos Previos
- Java 21+
- Node.js 20+
- PostgreSQL 18+
- Maven 3.9+

### 1. Configurar Base de Datos

```bash
# Conectar a PostgreSQL
sudo -u postgres psql

# Crear base de datos y usuario
CREATE DATABASE libros_db;
CREATE USER libros_user WITH PASSWORD 'libros_password_123';
GRANT ALL PRIVILEGES ON DATABASE libros_db TO libros_user;
ALTER DATABASE libros_db OWNER TO libros_user;
\q
```

### 2. Backend

```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

El backend estará disponible en `http://localhost:8080`

### 3. Frontend

```bash
cd frontend
npm install
npm run dev
```

El frontend estará disponible en `http://localhost:5173` (o 5174 si el puerto está ocupado)

## 🚀 Uso

1. Abre el navegador en `http://localhost:5173`
2. Regístrate con tu nombre, email y contraseña
3. Inicia sesión con tus credenciales
4. Comienza a agregar libros a tu biblioteca
5. Usa la búsqueda para filtrar tus libros
6. Cambia la disponibilidad haciendo clic en el badge
7. Exporta tu biblioteca a CSV
8. Activa el modo oscuro desde el navbar

## 📂 Estructura del Proyecto

```
proyecto-libros/
├── backend/
│   └── src/main/java/com/tuapp/libros/
│       ├── config/         # Configuración (Security, CORS)
│       ├── controller/     # Controladores REST
│       ├── dto/           # Data Transfer Objects
│       ├── exception/     # Manejo de excepciones
│       ├── model/         # Entidades JPA
│       ├── repository/    # Repositorios JPA
│       ├── security/      # JWT y filtros
│       └── service/       # Lógica de negocio
└── frontend/
    └── src/
        ├── components/    # Componentes reutilizables
        ├── contexts/      # Context API (Auth)
        ├── hooks/         # Custom hooks
        ├── pages/         # Páginas principales
        ├── services/      # Servicios API
        ├── types/         # Tipos TypeScript
        └── utils/         # Utilidades
```

## 🔒 Seguridad

- Contraseñas encriptadas con **BCrypt**
- Tokens JWT con expiración de 24 horas
- Validación de datos en backend y frontend
- Protección CORS configurada
- Rutas privadas protegidas
- Headers de seguridad habilitados

## 🎨 Características de UI/UX

- **Responsive Design**: Funciona en móviles, tablets y desktop
- **Dark Mode**: Tema oscuro/claro con persistencia
- **Animaciones**: Transiciones suaves y efectos visuales
- **Loading States**: Skeleton loaders durante carga
- **Error Handling**: Mensajes de error claros
- **Validaciones**: Feedback inmediato en formularios

## 📊 API Endpoints

### Autenticación
- `POST /api/auth/register` - Registrar usuario
- `POST /api/auth/login` - Iniciar sesión

### Libros (requiere autenticación)
- `GET /api/libros` - Listar todos los libros del usuario
- `GET /api/libros/{id}` - Obtener libro por ID
- `POST /api/libros` - Crear nuevo libro
- `PUT /api/libros/{id}` - Actualizar libro
- `DELETE /api/libros/{id}` - Eliminar libro
- `PUT /api/libros/{id}/disponibilidad` - Cambiar disponibilidad
- `GET /api/libros/buscar?query={query}` - Buscar libros

## 🧪 Testing

### Backend
```bash
cd backend
./mvnw test
```

### Frontend
```bash
cd frontend
npm run test
```

## 📦 Build para Producción

### Backend
```bash
cd backend
./mvnw clean package -DskipTests
java -jar target/libros-backend-0.0.1-SNAPSHOT.jar
```

### Frontend
```bash
cd frontend
npm run build
npm run preview
```

## 🤝 Contribuir

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add: nueva característica'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver `LICENSE` para más información.

## 👨‍💻 Autor

**Hiro**
- Email: hiro@ejemplo.com
- GitHub: [@hiro](https://github.com/hiro)

## 🙏 Agradecimientos

- Spring Boot Documentation
- React Documentation
- PostgreSQL Team
- Vite Team
- Comunidad de desarrolladores

---

⭐ Si este proyecto te fue útil, considera darle una estrella en GitHub!
