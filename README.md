# Aplicación de gestión de DRONES — Arquitectura hexagonal

Aplicación de escritorio para la gestión de drones (Ingeniería de Software 2, Politécnico Grancolombiano). Permite registrar y administrar drones (CRUD sobre PostgreSQL), consultar el clima de una ciudad y evaluar si es viable volar, calcular el consumo de batería de una ruta, programar tareas de misión, coordinar una misión de vigilancia y controlar el estado operativo de cada dron.

El proyecto está construido con **arquitectura hexagonal (puertos y adaptadores)** y aplica, en cada entrega, un patrón de diseño nuevo.

## Tecnologías

| Herramienta | Versión |
|---|---|
| Java | 17 |
| Maven | 3.9+ |
| JavaFX | 21.0.2 |
| PostgreSQL (driver JDBC) | 42.7.4 |
| org.json | 20240303 |
| JUnit | 5.10.2 |
| API externa | OpenWeatherMap (Geocoding + Current Weather) |

## Arquitectura hexagonal

```
           ┌──────────────────────── INFRAESTRUCTURA ────────────────────────┐
           │                                                                 │
 Usuario → │  DronTecnologia (JavaFX) → DronAdaptador ──┐                    │
           │                                            ▼                    │
           │                ┌──────────── APLICACIÓN ─────────────┐          │
           │                │ Puertos de entrada (UseCase)        │          │
           │                │        ▼                            │          │
           │                │ Servicios ──► Puertos de salida ────┼──► Adaptadores de salida
           │                │        ▼                            │    (PostgreSQL, OpenWeatherMap,
           │                │   ┌──────── DOMINIO ────────┐       │     ruta.json, Haversine)
           │                │   │ modelo, reglas, patrones │       │          │
           │                │   └──────────────────────────┘       │          │
           │                └─────────────────────────────────────┘          │
           └─────────────────────────────────────────────────────────────────┘
```

| Capa | Paquete | Responsabilidad |
|---|---|---|
| Dominio | `co.edu.poli.sw2.dominio` | Entidades y reglas del negocio. No depende de JavaFX, de la base de datos ni de APIs. |
| Aplicación | `co.edu.poli.sw2.aplicacion` | Puertos de entrada (casos de uso), puertos de salida y servicios que los implementan. |
| Infraestructura | `co.edu.poli.sw2.infraestructura` | Adaptadores: vista JavaFX, persistencia PostgreSQL, clientes HTTP, lectura de JSON. |
| Arranque | `co.edu.poli.sw2.vista.App` → `infraestructura.ui.AppDron` | `App` lanza `AppDron`, la raíz de composición que crea los adaptadores y servicios y los inyecta en `DronAdaptador`. |

Las dependencias siempre apuntan hacia adentro: la infraestructura conoce a la aplicación y la aplicación conoce al dominio, nunca al revés.

## Patrones de diseño implementados

| Patrón | Paquetes principales | Qué resuelve |
|---|---|---|
| **Observer** | `dominio.weather`, `aplicacion.servicio.weather`, `aplicacion.observer` | `WeatherNotificationService` notifica el clima consultado a los observadores suscritos (consola, dashboard, log, estado de vuelo). |
| **Chain of Responsibility** | `dominio.chain` | Evalúa la viabilidad del vuelo pasando el clima por una cadena de reglas: temperatura → humedad → viento → presión → visibilidad. |
| **Strategy** | `dominio.strategy`, `aplicacion.servicio.strategy` | Calcula el consumo de batería de la ruta de `ruta.json` según el modo elegido: Ahorro, Normal o Alto Rendimiento. |
| **Command** | `dominio.command`, `aplicacion.servicio.mision` | Encola tareas de misión (fotografía, escaneo, temperatura) y las ejecuta o cancela con `ProgramadorTareas`. |
| **Mediator** | `aplicacion.mediator` | `MissionCoordinator` coordina navegación, sensores, batería y comunicación de una misión de vigilancia; los componentes no se comunican entre sí. |
| **State** | `dominio.state`, `aplicacion.servicio.state` | Controla el ciclo operativo del dron (APAGADO → EN ESPERA → VOLANDO → ATERRIZANDO). `DroneContext` envuelve al `Dron` sin modificarlo. |

Los diagramas de arquitectura y de dominio, junto con la evidencia de cada patrón, están en la **wiki** del repositorio.

## Estructura del proyecto

```
src/main/java/co/edu/poli/sw2/
├── dominio/
│   ├── modelo/          Dron, Agricultura, Vigilancia, Piloto, Sensor, Mision
│   ├── enumeracion/     TipoDron
│   ├── weather/         Weather, WeatherObserver                 (Observer)
│   ├── chain/           WeatherHandler y reglas de viabilidad    (Chain of Responsibility)
│   ├── strategy/        ConsumoStrategy y estrategias            (Strategy)
│   ├── command/         DroneCommand y comandos de misión        (Command)
│   └── state/           DroneContext, DroneState y estados       (State)
├── aplicacion/
│   ├── puerto/entrada/  Casos de uso (CRUD, weather, strategy, mision, mediator, state)
│   ├── puerto/salida/   DronRepository, WeatherPort, GeoLocationPort, RutaPort, DistanciaPort
│   ├── servicio/        Implementaciones de los casos de uso
│   ├── observer/        Observadores del clima
│   └── mediator/        DroneMediator, MissionCoordinator y componentes   (Mediator)
├── infraestructura/
│   ├── ui/              AppDron, DronAdaptador, DronTecnologia (JavaFX)
│   ├── persistencia/    ConexionBD, PostgresDronRepository
│   ├── weather/         Clientes y adaptadores de OpenWeatherMap
│   ├── loader/          JsonRutaLoader
│   └── util/            HaversineUtil
└── vista/               App (punto de entrada)

src/main/resources/co/edu/poli/sw2/vista/   DrownView.fxml, styles.css
db/                                         schema.sql, datos_prueba.sql
ruta.json                                   Origen y destino para el cálculo de consumo
```

## Configuración

### 1. Base de datos

Crear la base `dron_bd` en PostgreSQL y ejecutar los scripts de la carpeta `db/` (el detalle está en [`db/README.md`](db/README.md)):

```bash
psql -U postgres -c "CREATE DATABASE dron_bd;"
psql -U postgres -d dron_bd -f db/schema.sql
psql -U postgres -d dron_bd -f db/datos_prueba.sql
```

### 2. Archivo `.env`

Crear un archivo `.env` en la raíz del proyecto con las credenciales locales:

```properties
DB_URL=jdbc:postgresql://localhost:5432/dron_bd
DB_USER=postgres
DB_PASSWORD=<tu_contraseña>
OPENWEATHER_API_KEY=<tu_api_key_de_openweathermap>
```

> El archivo `.env` está en `.gitignore` y **nunca** debe subirse al repositorio.

## Ejecución

```bash
mvn clean javafx:run
```

## Pruebas

```bash
mvn clean test
```

Incluye pruebas del CRUD, del manejo de errores, de la conexión, de la cadena de viabilidad, del programador de tareas (Command) y de la misión coordinada (Mediator).

## Documentación (Javadoc)

```bash
mvn javadoc:javadoc
```

La documentación generada queda en `target/reports/apidocs/index.html` (o `target/site/apidocs/`, según la versión del plugin).

## Flujo de trabajo

Cada patrón se desarrolla en una rama `ft/<patrón>` con commits `ft:<patrón> ...` y se integra a `main` mediante Pull Request.
