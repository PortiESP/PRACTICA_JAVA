Documentación de la práctica Monopoly
======================================

## Índice

- [Documentación de la práctica Monopoly](#documentación-de-la-práctica-monopoly)
  - [Índice](#índice)
  - [Introducción](#introducción)
  - [Resumen de las clases](#resumen-de-las-clases)
  - [Gestión de la entrada y salida de datos](#gestión-de-la-entrada-y-salida-de-datos)
    - [Introducción (`IOManager`)](#introducción-iomanager)
    - [Clase `languageManager`](#clase-languagemanager)
    - [Clase `MenuBuilder`](#clase-menubuilder)
  - [Gestión del sistema idiomas](#gestión-del-sistema-idiomas)
    - [Ficheros de idiomas](#ficheros-de-idiomas)

## Introducción

> 🎓 Práctica de la asignatura de **Programación Orientada a Objetos** de la **Universidad Rey Juan Carlos (URJC)**. La práctica consiste en la implementación de una versión del juego de mesa **Monopoly** en Java. 
> 
> En esta versión se pretende introducir un ordenado que se encarga de gestionar las transacciones entre los jugadores y el banco, así como de gestionar las propiedades del tablero. No se pretende simular el juego en sí, sino solo la parte de gestión de las propiedades y las transacciones. 
> 
> El juego se ejecuta en la terminal y se puede jugar en varios idiomas.

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

### Introducción (`IOManager`)

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

> *Esta clase solo tiene dos métodos:*
> 
> - `load(filename)` ~ Lee el fichero de idioma y carga el mapa de frases con el contenido de este archivo. Este método recibe como parámetro el nombre del fichero de idioma que se quiere cargar. Este fichero debe estar ubicado en la carpeta indicada en el archivo de **constantes** como la constante `LANGUAGES_PATH`.
> - `get(id)` ~ Devuelve el string correspondiente al identificador `id` en el idioma seleccionado por el usuario.

### Clase `MenuBuilder`

Esta clase se sirve para construir la CLI del juego. La clase implemente métodos para construir menus, alertas, formularios, documentos, leer entradas de datos, etc.

> *Esta clase tiene los siguientes métodos:*
>
> - `menu()` ~ Construye un menú con las opciones pasadas como parámetro. Este método devuelve el número de la opción seleccionada por el usuario.
> - `alert()` ~ Imprime una alerta con el mensaje pasado como parámetro. 
> - `askYesNo()` ~ Imprime una pregunta con las opciones *Sí* y *No*. Este método devuelve `true` si el usuario selecciona *Sí* y `false` si el usuario selecciona *No*.
> - `form()` ~ Construye un formulario con los campos pasados como parámetro. Este método devuelve un array con los valores introducidos por el usuario.
> - `doc()` ~ Construye un documento con el contenido pasado como parámetro.
> - `readInt()` ~ Lee un entero de la entrada estándar.
> - `readString()` ~ Lee una cadena de caracteres de la entrada estándar.
> - ...


## Gestión del sistema idiomas

Como ya he mencionado anteriormente, el sistema de idiomas se basa en un mapa que hace corresponder un identificador de un cierto mensaje con el mensaje en sí, en el idioma seleccionado por el usuario. 

El mapa se carga en la clase `LanguageManager` y se accede a él a través de la clase `IOManager`.

El mapa se carga a través del método `load(filename)` de la clase `LanguageManager`. Este método recibe como parámetro el nombre del fichero de idioma que se quiere cargar. 

### Ficheros de idiomas

El fichero de idioma es un fichero de texto plano que contiene las frases que luego la clase `LanguageManager` cargará en el mapa.


> 📁 Estos ficheros deben estar ubicados en la carpeta indicada en el archivo de **constantes** como la constante `LANGUAGES_PATH`.

> 🏷️ Estos ficheros deben tener llamarse con el formato `Idioma.txt` (*siendo `Idioma` el nombre del idioma en cuestión*).

> 📝 Formato que deben seguir de los ficheros de idiomas:
>
> - Cada frase se encuentra en una línea distinta.
> - Cada línea tiene el siguiente formato: `ID=FRASE` (*Siendo `ID` el identificador de dicha frase y `FRASE` el string de la frase en sí*)
> - Todos los archivos de idiomas deben tener los mismos identificadores definidos.
> - El orden de las líneas no importa (*cada línea se identifica por su identificador*).
> - Los nombres de los identificadores deben seguir la siguiente convención:
>   - No pueden contener espacios (*palabras separadas por `_`*)
>   - Deben estas en mayúsculas (*E.G. `WELCOME_1`*)
>   - Deben describir brevemente la frase o el contexto que identifican (*E.G. `PROMPT_PLAYER_NAME`*)
>   - Si la frase contiene una variable, esta debe aparecer en el string como `%s` (*como los placeholders de C de la función `printf()`*)
>   - Los identificadores no pueden contener el carácter `=`
>   - Está permitido usar espacios entre el separador `=` con el identificador y la frase (*E.G. `WELCOME_1 = Welcome to Monopoly!`*)

> **EJEMPLO**
> 
> En este caso, el identificador es `WELCOME_1` y la frase es `Welcome to Monopoly!`, que es la que se imprimirá cuando se llame a `WELCOME_1` en el código. En caso de que el idioma sea español, la frase que se imprimirá será `¡Bienvenido a Monopoly!` la cual se encuentra en el fichero `Español.txt`. También se muestran otras frases de ejemplo.
> 
> - *Fichero `English.txt`*
> ```txt
> WELCOME_1=Welcome to Monopoly!
> PROMPT_OPTION=Please, select an option:
> PROMPT_PLAYER_NAME=Please, enter your name:
> ```
> 
> - *Fichero `Español.txt`*
> 
> ```txt
> WELCOME_1=¡Bienvenido a Monopoly!
> PROMPT_OPTION=Por favor, seleccione una opción:
> PROMPT_PLAYER_NAME=Por favor, introduzca su nombre:
> ```
