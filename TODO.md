TO-DO
------------------------------------------

# Por hacer

- CLS (clear screen) 
- Mejorar el estilos de los menus
- Tests unitarios
- JavaDocs
- Clean the code
- COLORS with escape codes
- Price of the houses on the management menu  
- Test the liquidation menu
- Traducir al resto de idiomas (vasco, gallego, catalán)

# Aspectos a tener en cuenta antes de entregar el proyecto

- [ ] Comprobar que todos los strings comprueban con equals
- [ ] Comprobar que todos los strings están traducidos
- [ ] Poner en private o protected todo lo que sea posible
- [ ] Usar constantes para todo lo que sea posible
- [ ] Eliminar o comentar código que no se use (`DEBUG` y `StartDebug`)


# Cosas que justificar en el informe

- [ ] Mapa de `monopolyCodes` en vez de array
- [ ] Clase estática para el LanguageManager, MenuBuilder y IOManager
- [ ] El diccionario de idiomas no se serializa
- [ ] Por qué no he usado colores para identificar a los jugadores
- [ ] Por qué los newGame y loadGame están en Game
- [ ] Por qué no he usado el principio SOLID de responsabilidad única en el MenuBuilder a la hora de implementar los métodos de utilidades de los strings