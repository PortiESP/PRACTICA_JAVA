Documentación de la práctica Monopoly
======================================

## Índice

- [Documentación de la práctica Monopoly](#documentación-de-la-práctica-monopoly)
  - [Índice](#índice)
  - [Introducción](#introducción)
  - [Resumen de las clases](#resumen-de-las-clases)
  - [Gestión de la entrada y salida de datos](#gestión-de-la-entrada-y-salida-de-datos)
    - [Introducción](#introducción-1)
    - [Clase `languageManager`](#clase-languagemanager)
    - [Clase `MenuBuilder`](#clase-menubuilder)

## Introducción

Este documento contiene la documentación de la práctica así como una explicación de las decisiones de diseño tomadas durante el desarrollo de la misma con el fin de ayudar a entender el código. Se incluye también una explicación de aquellas partes que difieren del enunciado de la práctica.

## Resumen de las clases

> **⚠️NOTA⚠️:**
>  El funcionamiento interno de cada clase está definido de los JavaDocs de cada clases. En este documento solo se habla del funcionamiento superficial de las mismas.

> 🆕 **Clase inicial**
> - [**`Game`**](#game) ~ Esta clase contiene el método `main` y se encarga de inicializar el juego.

> 🛠️ **Clases auxiliares**
> - [**`IOManager`**](#iomanager) ~ Esta clase se encarga de gestionar la entrada y salida de datos del juego.
> - [**`LanguageManager`**](#languagemanager) ~ Esta clase se encarga de gestionar el idioma del juego.
> - [**`MenuBuilder`**](#menubuilder) ~ Esta clase se encarga de construir los menús del juego (*wrapper de la clase IOManager*).
> - [**`Const`**](#const) ~ Esta clase contiene constantes utilizadas en el código.

> ⚙️ **Clases principales**
> - [**`GameManager`**](#gamemanager) ~ Esta clase se encarga de gestionar la configuración inicial del juego (*idioma, crear partica o cargar una partida guardada*).
> - [**`Game`**](#game) ~ Esta clase se encarga de gestionar el flujo principal de la partida.

> 🕹️ **Clases del juego**
> - [**`Player`**](#player) ~ Esta clase se encarga de gestionar el dinero y las propiedades de cada jugador.
> - [**`MonopolyCode`**](#monopolycode) (*abstracta*) ~ Esta es una clase abstracta que representa la estructura que deberán tener el resto de clases que implementan los códigos de operación que el juego requiere.
>
> > 📟 Las clases mencionadas a continuación heredan de la clase `MonopolyCode`
> >
> > - [**`PaymentCard`**](#paymentcard) ~ Esta clase se encarga de gestionar las tarjetas de pago (*tarjetas de suerte o caja de comunidad*).
> > - [**`RepairsCard`**](#repairscard) ~ Esta clase se encarga de gestionar las tarjetas de suerte que exigen un pago en función del número de casas u hoteles.
> > - [**`Property`**](#property) ~ Esta clase se encarga de gestionar las propiedades del tablero.
> > > 🏦 La clase `Property` es a su vez una clase abstracta de la que heredan las clases `StreetCard` , `StationCard` y `ServiceCard`
> > > - [**`StreetCard`**](#streetcard) ~ Esta clase se encarga de gestionar las propiedades de tipo calle.
> > > - [**`StationCard`**](#stationcard) ~ Esta clase se encarga de gestionar las propiedades de tipo estación.
> > > - [**`ServiceCard`**](#servicecard) ~ Esta clase se encarga de gestionar las propiedades de tipo servicio (compañía de aguas y electricidad).

## Gestión de la entrada y salida de datos

### Introducción

La entrada y salida de datos se gestiona únicamente desde la clase `IOManager`. Esta clase funciona como un *wrapper* de la clase `Scanner` de Java y sirve como interfaz entre el código y la entrada y salida de datos.

> En esta clase, los métodos más importantes son:
> - `print()` ~ Imprime un string en la salida estándar. Este método no realiza ningún tipo de formateo o traducción.
> - `printMsg(id)` ~ Imprime un string en la salida estándar. Este método realiza un traducción del string que recibe como parámetro al idioma seleccionado por `setLanguage(lang)`.
> - `getMsg(id)` ~ Devuelve un string traducido al idioma seleccionado por `setLanguage(lang)`.
> `cls()` ~ Limpia la pantalla de la terminal.
> - `setLanguage(lang)` ~ Cambia el idioma del juego al idioma seleccionado por el usuario. Este método carga el mapa de frases del idioma seleccionado.
> - `readInt()` ~ Lee un entero de la entrada estándar.
> - `readString()` ~ Lee una cadena de caracteres de la entrada estándar.
> - ...

De esta clase surgen dos clases derivadas:

- `LanguageManager` ~ Esta clase se encarga de gestionar el idioma del juego.
- `MenuBuilder` ~ Esta clase se encarga de construir los menús del juego.

### Clase `languageManager`

Esta clase se encarga de gestionar el idioma del juego a través de un mapa que hace corresponder un identificador de un cierto mensaje con el mensaje en sí, en el idioma seleccionado por el usuario.

> Esta clase solo tiene dos métodos:
> 
> - `load(filename)` ~ Lee el fichero de idioma y carga el mapa de frases con el contenido de este archivo. Este método recibe como parámetro el nombre del fichero de idioma que se quiere cargar. Este fichero debe estar ubicado en la carpeta indicada en el archivo de **constantes** como la constante `LANGUAGES_PATH`.
> - `get(id)` ~ Devuelve el string correspondiente al identificador `id` en el idioma seleccionado por el usuario.

### Clase `MenuBuilder`

Esta clase se sirve para construir la CLI del juego. La clase implemente métodos para construir menus, alertas, formularios, documentos, leer entradas de datos, etc.

> Esta clase tiene los siguientes métodos:
>
> - `menu()`