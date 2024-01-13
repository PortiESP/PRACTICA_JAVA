# Decisiones técnicas

----------------------------

> 📖 Este documento contiene las decisiones técnicas tomadas durante el desarrollo del proyecto. El objetivo de este documento es justificar las decisiones tomadas que difieren de la implementación propuesta en el enunciado.

> 📑 Este documento toma como referencia el enunciado de la [práctica](./enunciado.pdf) y recorre los puntos de dicho documento comentando aquellos aspectos que no se han implementado tal y como se muestra en el enunciado de la práctica.

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

