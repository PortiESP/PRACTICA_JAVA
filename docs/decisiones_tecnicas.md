# Decisiones técnicas

----------------------------

> 📖 Este documento contiene las decisiones técnicas tomadas durante el desarrollo del proyecto. El objetivo de este documento es justificar las decisiones tomadas que difieren de la implementación propuesta en el enunciado.

> 📑 Este documento toma como referencia el enunciado de la [práctica](./enunciado.pdf) y recorre los puntos de dicho documento comentando aquellos aspectos que no se han implementado tal y como se muestra en el enunciado de la práctica.

## Índice

- [Decisiones técnicas](#decisiones-técnicas)
  - [Índice](#índice)
  - [1. Introduction](#1-introduction)
  - [2. Clases propuestas](#2-clases-propuestas)
  - [3. Operativa del juego](#3-operativa-del-juego)

## 1. Introduction

> #### **1️⃣** ➡ **Secuencia inicial** (*Diagrama de actividad: Figura 1*)
> 
> En el diagrama de actividad se muestra como lo primero que se hace es preguntar al usuario si quiere cargar una partida o empezar una nueva. Si embargo en la implementación se ha decidido que primero se pregunte el idioma.
>
> > 🧠 Esto se ha hecho así porque si para que no haya que elegir un idioma por defecto que sea mostrado en multiples menus antes de preguntar al usuario por el idioma deseado.

## 2. Clases propuestas

> #### **1️⃣** ➡ **Renombrado a IOManager y estática**  (*Diagrama de clases principal: Figura 2*)
> 
> La diferencia más significativa entre el diagrama de clases propuesto y el implementado es que en el diagrama de clases propuesto se muestra la clase `Terminal` que se encarga de la entrada y salida de datos. Sin embargo en la implementación se ha decidido llamar a dicha clase `IOManager` y que esta sea una clase estática.
>
> En la implementación, la clase `IOManager` no tiene la responsabilidad de gestionar los idiomas. Esta responsabilidad se ha delegado a la clase `LanguageManager` que también es una clase estática por las mismas razones.
>
> > 🧠 Esto se ha hecho así por las siguientes razones:
> > - No tiene sentido crear una instancia de la clase `IOManager` ya que esta no tiene un estado interno que la diferencie de otras instancias.
> > - La sintaxis para llamar a los métodos de la clase `IOManager` es más sencilla ya que no es necesario crear una instancia de la clase para poder llamar a sus métodos.
> > - Para evitar tener que reinicializar cada instancia de la clase `IOManager` cada vez que se cambia de idioma.
> > - Para evitar tener que pasar como parámetro la instancia de la clase `IOManager` a cada clase que la necesite.
> > - Para simplificar el hecho de habilitar o deshabilitar el modo debug mediante un atributo. Esto nos permite poder habilitar el modo debug de forma *global*
>
> En definitiva, se ha decidido que la clase `IOManager` sea estática para no introducir complejidad innecesaria en el código.

> #### **2️⃣** ➡ **Mapa de códigos** (*Diagrama de clases: Figura 2*)
>
> En el diagrama de clases propuesto se muestra como la clase `Game` tiene un atributo llamado `monopolyCodeArray` que es un array de `MonopolyCode`. Sin embargo en la implementación se ha decidido que dicho atributo sea un `HashMap` de `MonopolyCode` donde la clave es el código (*`String`*) del `MonopolyCode`.
>
> > 🧠 Esto se ha hecho así para poder acceder a los `MonopolyCode` de forma más sencilla y rápida. Además, al ser un `HashMap` podemos acceder a los `MonopolyCode` mediante su código (*`String`*) y no mediante su posición en el array por lo que si en un futuro se añaden nuevos `MonopolyCode` o se introducen en un orden diferente, esto no afectará al funcionamiento del programa.

> #### **3️⃣** ➡ **Nombres de las clases derivadas de `MonopolyCode`** (*Diagrama de clases: Figura 3*)
>
> En el diagrama de clases propuesto se muestran un serie de clases. Se ha decidido que los nombres de las clases derivadas de `MonopolyCode` sean los siguientes:
> - `PaymentCharge` ➡ `PaymentCard`
> - `RepairsCard` (*No cambia*)
> - `Property` (*No cambia*)
> - `Transport` ➡ `StationCard`
> - `Service` ➡ `ServiceCard`
> - `Street` ➡ `StreetCard`
>
> > 🧠 Esto se ha hecho así para que los nombres de las clases sean más descriptivos y claros. A las clases que representan las distintas tarjetas se les ha añadido el sufijo `Card`.

> #### **4️⃣** ➡ **Colores de los jugadores** (*Diagrama de clases: Figura 3*)
>
> En el diagrama de clases propuesto se muestra como la clase `Player` tiene un atributo llamado `color` que es un enumerado de `Color`. Sin embargo en la implementación se ha decidido que dicho atributo sea un `String` que representa el identificador del jugador. Siendo este identificador el nombre del jugador, aunque se puede usar cualquier `String` para identificar al jugador como por ejemplo un color, un numero, figura, etc.
>
> > 🧠 Esto se ha decidido así basándonos en los siguientes criterios:
> > - Mantener la simplicidad del código (*KISS: Keep It Simple, Stupid!*)
> > - En el juego original los jugadores no tienen un color asociado, sino una figura.
> > - Flexibilidad a la hora de identificar a los jugadores según la temática del juego.

> #### **5️⃣** ➡ **Clases de idiomas `TranslatorManager`** (*Diagrama de clases: Figura 4*)
>
> En la implementación se ha decidido que no implementar la clase `TranslatorManager` y fusionarla con la clase `LanguageManager`.
>
> > 🧠 Esto se ha hecho así para simplificar el código dado que la única responsabilidad de esta clase era la de almacenar el idioma actual y servir como *wrapper* para la clase `Translator`.
> > 
> > Adicionalmente, se ha decidido que no se almacene el idioma seleccionado ya que no es necesario dado que cuando se selecciona un idioma, este se carga en un mapa y este es ajeno al idioma que está almacenado como claves. Por lo cual, la clase `TranslatorManager` no aporta ningún valor añadido.

> #### **6️⃣** ➡ **Clases de idiomas `Translator`** (*Diagrama de clases: Figura 4*)
>
> Esta clase se corresponde con la clase `LanguageManager` en la implementación.
> 
> Como ya hemos mencionado anteriormente, en la implementación se ha decidido que la clase `LanguageManager` sea una clase estática (*[ver punto 2.1](#1️⃣-➡-renombrado-a-iomanager-y-estática-diagrama-de-clases-principal-figura-2)*). 
>
> Esta clase es la encargada de cargar los idiomas desde los ficheros de idiomas y de devolver los mensajes solicitados en el idioma seleccionado.
>
> > 🧠 Se ha decidido llamar a esta clase `LanguageManager` en vez de `Translator` por los siguientes motivos:
> > - Como tal esta clase no hace ninguna traducción, los mensajes ya están traducidos en los ficheros de idiomas.
> > - Los *inputs* o *IDs* no son el mensaje en sí en un idioma concreto, sino que son una clave a modo de identificador del mensaje en el idioma seleccionado. Dichas claves se usan como identificador y no suponen un lenguaje por defecto.
> > - Esta clase es ajena al idioma de los mensajes que hay cargados en su mapa, ya que solo se encarga de cargar los idiomas y devolver los mensajes solicitados según el ID proporcionado.

> #### **7️⃣** ➡ **Clases de idiomas `TextTerminal`** (*Diagrama de clases: Figura 4*)
>
> Se ha decidido que esta clase sea absorbida por la clase `IOManager` dado que no tiene sentido esta clase si luego la clase `Terminal` va a implementar los métodos de la clase `TextTerminal` a modo de *wrapper*. 
> 
> > 🧠 En todo caso se podría haber hecho que la clase `Terminal` instanciará un objeto de la clase `TextTerminal`.
> > 
> > Para simplificar el esquema, se ha decidido omitir esta clase.

> #### **8️⃣** ➡ **Clases de idiomas alto y bajo nivel** (*Diagrama de clases: Figura 4*)
>
> Aunque como ya hemos mencionado anteriormente, se ha decidido omitir la clase `TextTerminal` ya que carece de sentido e introduce una complejidad innecesaria, se entiende que el sentido de la clase `TextTerminal` es el de separar la lógica de alto nivel de la lógica de bajo nivel. Encapsulando la lógica y la gestión de los dispositivos de E/S de bajo nivel en la clase `TextTerminal` y proporcionando una interfaz a de alto nivel en la clase `Terminal`.
> 
> > 🧠 Como ya hemos visto, la responsabilidad de la clase `TextTerminal` ha sido absorbida por la clase `IOManager` como la clase que maneja el *stdin/stdout* (**bajo nivel**) y la clase `MenuBuilder` como la clase que se encarga de construir los menús, proporcionando así una interfaz de **alto nivel** al programador.

## 3. Operativa del juego

> #### **1️⃣** ➡ **Flujo de inicio del programa** (*Diagrama de secuencia: Figura 6*)
>
> El diagrama de secuencia implementado difiere del propuesto en los siguientes puntos
>
> 1. Como ya he mencionado antes, las clases `IOManager`, `LanguageManager` y `MenuBuilder` son estáticas por lo que no es necesario instanciarlas al principio del programa.
>
> 2. Los métodos que se usar para imprimir y leer datos son aquellos proporcionados por la clase `MenuBuilder`, que en ultima instancia estos métodos llaman a los métodos de la clase `IOManager`.
>
> 3. La clase `Game` se instancia siempre, aunque se vaya a cargar una partida guardada. Es esta clase la que implementa los métodos necesarios para cargar una partida guardada o empezar una nueva. Cuando se desea cargar una partida guardada, se llama al método `loadGame()` de la clase `Game` que leerá el fichero de la partida guardada y reemplazará los datos de la partida actual por los datos de la partida guardada. En caso de querer empezar una nueva partida, se llama al método `newGame()` de la clase `Game` que se encarga de inicializar los datos de la partida a los valores por defecto.
> > 🧠 Esto se ha hecho así dado que la clase `GameManager` solo se debe encargar de llamar a los métodos necesarios para arrancar el juego desde las distintas opciones del menú principal. La clase `GameManager` no sabe cual es la estructura interna de la clase `Game` por lo que la responsabilidad de crear, cargar o guardar los datos de la partida recae en la clase `Game`.
>
> 4. Relacionado con el punto anterior, desde la clase `GameManager` no se llaman a todos los métodos necesarios para arrancar el juego (*`createPlayers`*, *`createMonopolyCodes`*, *etc...*), sino que se llama a uno de los dos métodos mencionados anteriormente (*`loadGame`* o *`newGame`*) que se encargan de llamar a los métodos necesarios para arrancar el juego. Finalmente, se llama al método `play()` de la clase `Game` que se encarga de arrancar el juego. 
> ⚠️ **Nota:** *Si se llama a `play()` sin haber llamado antes a `loadGame()` o `newGame()` se creará una partida nueva por defecto.*


> #### **2️⃣** ➡ **Carga de códigos de las tarjetas** (*Diagrama de secuencia: Figura 7*)
>
> Las operaciones de lectura del archivo de códigos de las tarjetas (*`MonopolyCodes.txt`*) se ejecutan desde el método `newGame()` de la clase `Game`. Este método se encarga de llamar al método `resetCards()` el cual leerá el archivo de códigos de las tarjetas y creará los objetos de las tarjetas correspondientes.

> #### **3️⃣** ➡ **Operativa del traductor** (*Diagrama de secuencia: Figura 8*)
>
> En el punto 3.2 del documento del enunciado se menciona que el traductor (`TranslatorManager`) habrá cargado todos los diccionarios de idiomas desde los archivos correspondientes y que luego se selecciona uno de estos idiomas al cual se traducirán los mensajes.
>
> En la implementación, el traductor (`LanguageManager`) no almacena todos los idiomas posibles, sino que solo almacena el idioma seleccionado.
>
> Cuando se desea cambiar el idioma, simplemente se reemplaza en el mapa de mensajes los mensajes anteriores con los mensajes en el nuevo idioma seleccionado.
>
> > 🧠 Esto se ha hecho para evitar tener que cargar todos los idiomas posibles en el traductor, ahorrando así memoria. Además, al no tener que cargar todos los idiomas posibles, el tiempo de carga del programa es menor.
>
> También hay que recordar que el mapa de mensajes no hace una *traducción* del Español a otro idioma, como dice el enunciado, sino que en la implementación, el mapa utiliza como claves los *IDs* de los mensajes
>
> > 🧠 Al utilizar *IDs* en vez de los mensajes en sí en un idioma concreto, se obtienen los siguientes beneficios:
> > - En el código no aparecen mensajes en un idioma concreto *por defecto*
> > - Si se desea cambiar un mensaje, solo se debe cambiar en el fichero de idioma correspondiente y no en el código, por lo que se evita tener que buscar en el código todas las ocurrencias del mensaje que se desea cambiar.
> > - Se evita tener que recopilar el código cada vez que se cambia un mensaje del idioma por defecto.
> > - Si se desea cambiar mensajes en el idioma por defecto, se evitan discrepancias entre el código y el fichero de idioma ya que los IDs son solo breves descripciones de los mensajes que identifican pero no contienen el mensaje en sí.
> > - Si decidimos utilizar otro idioma por defecto, solo se debe cambiar el valor de una constante.

> #### **4️⃣** ➡ **Funcionalidad del `doOperation`** (*Diagrama de secuencia: Figura 9*)
>
> Este método funciona esencialmente igual que en el diagrama de secuencia propuesto. La única diferencia es que en la implementación no se imprime un resumen de la operación realizada al final de la ejecución del método ya que ya se incluye suficiente información en los mensajes de la operación proporcionados por la clase `MenuBuilder`.

> #### **5️⃣** ➡ **Funcionalidad del `doOperation` de una propiedad** (*Diagrama de secuencia: Figura 10*)
>
> La secuencia que sigue el método `doOperation` es la siguiente:
> - **Si la propiedad tiene dueño**
>   - **Si el dueño NO es el jugador actual**
>     - **Si la propiedad NO está hipotecada** ➡ Se paga el alquiler
>     - **Si la propiedad está hipotecada** ➡ No se paga el alquiler
>   - **Si el dueño es el jugador actual** ➡ Preguntar al jugador si desea abrir el menú de gestión de la propiedad (*comprar casas, venta de casas, comprar hotel, vender hotel, hipotecar, deshipotecar, etc...*)
> - **Si la propiedad NO tiene dueño** ➡ Preguntar al jugador si desea comprar la propiedad
>
> > 🧠 No se preguntará al jugador si desea confirmar la operación ya que la navegación por los menús es muy rápida y acabaría siendo molesto para el usuario tener que confirmar cada operación.	Para compensar esto, se han añadido algunos mensajes de confirmación antes de entrar a ciertos menús, como por ejemplo el *menú de gestión de la propiedad*.

> #### **6️⃣** ➡ **Funcionalidad del `doOperation` de una propiedad de tipo `StreetCard`** (*Diagrama de secuencia: Figura 11*)
>
> El método `getPaymentForRent` es llamado `calculateAmountToPay` en la implementación.
>
> El método `showPaymentSummary` se muestra a través de llamadas al método `alert` de la clase `MenuBuilder` que se encarga de imprimir mensajes a modo de popup y que se mantienen en pantalla hasta que el usuario pulsa la tecla *Enter*.
>
> La clase `StreetCard` no se encarga de manejar el proceso de pago, sino que se have una llamada al método de la clase `Player` llamado `pay` el cual se encarga de restar el dinero al jugador actual y sumarlo al jugador que recibe el pago. Si el jugador no tiene suficiente dinero para hacer frente al pago, se le ofrecerá la posibilidad de *liquidar sus activos* para poder hacer frente al pago. Si aun así el jugador no puede hacer frente al pago, se le declarará en bancarrota y todo su dinero restante se le dará al jugador que recibe el pago. Al finalizar el turno se comprueba si algún jugador ha quedado en bancarrota y se le elimina del juego.