# App Proforma

Aplicación Android para registrar y administrar productos dentro de una proforma.

## Funcionalidades

- Registrar productos con código, nombre, precio y cantidad.
- Validar código de exactamente 6 caracteres.
- Limitar el nombre del producto a 40 caracteres.
- Calcular el total por producto y el total acumulado.
- Seleccionar registros desde un RecyclerView.
- Actualizar productos existentes.
- Eliminar productos registrados.
- Limpiar el formulario para iniciar un nuevo registro.

## Tecnologías

- Java
- Android SDK 34
- Material Components
- RecyclerView
- ConstraintLayout
- Gradle 8.2

## Estructura principal

```text
app/
└── src/main/
    ├── java/com/jijijija/appproforma/
    │   ├── MainActivity.java
    │   ├── ProformaAdapter.java
    │   └── ProformaItem.java
    └── res/layout/
        ├── activity_main.xml
        └── item_proforma_registro.xml
```

## Requisitos

- Android Studio
- JDK 17
- Android SDK 34

## Ejecutar el proyecto

1. Clona el repositorio.
2. Ábrelo en Android Studio.
3. Espera a que finalice Gradle Sync.
4. Ejecuta la app en un emulador o dispositivo Android.

También puedes compilar desde terminal:

```bash
./gradlew assembleDebug
```

En Windows:

```powershell
gradlew.bat assembleDebug
```

## Validaciones

| Campo | Regla |
| --- | --- |
| Código | Exactamente 6 caracteres |
| Producto | Máximo 40 caracteres |
| Precio | Obligatorio y no negativo |
| Cantidad | Obligatoria y mayor que 0 |

## Estado del proyecto

El repositorio incluye integración continua con GitHub Actions para verificar la compilación en pushes y pull requests hacia `main`.
