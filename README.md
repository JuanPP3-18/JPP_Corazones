# Corazones 

---
## Alumno
- Penela Juan Pablo 
- 195189

Implementación en Java del clásico juego de cartas **Corazones**, con arquitectura MVC y patrón Observer, jugable por consola.

---

## Objetivo del juego

Corazones es un juego de **bazas** (manos) en el que el objetivo es **terminar la partida con la menor cantidad de puntos posible**. Cada jugador intenta evitar ganar cartas que otorgan puntos.

La partida termina cuando algún jugador alcanza o supera el puntaje límite (100 puntos). Gana el jugador con **menos** puntos.

---

## Jugadores y mazo

- Se juega con **4 jugadores** (puede adaptarse a 3 o 5 con ajustes de mazo).
- Se utiliza un **mazo francés de 52 cartas**.
- Palos: Picas ♠, Corazones ♥, Diamantes ♦, Tréboles ♣.
- Orden de las cartas de menor a mayor: 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, A.

---

## Valor de las cartas (puntos)

| Carta | Puntos |
|---|---|
| Cada carta de Corazones ♥ | 1 punto |
| Reina de Picas (Q♠) | 13 puntos |
| Todas las demás | 0 puntos |

En cada ronda se reparten **26 puntos** en total (13 de corazones + 13 de la Reina de Picas).

---

## Desarrollo de una ronda

### 1. Reparto
Se mezcla el mazo y se reparten todas las cartas por igual entre los jugadores (13 cartas cada uno con 4 jugadores).

### 2. Primera mano
El jugador que tiene el **2 de Tréboles** comienza la ronda y debe jugar esa carta.

### 3. Jugar una mano (baza)
- Cada jugador, en sentido horario, juega **una carta**.
- El primer jugador define el **palo de salida**.
- Los demás jugadores **deben seguir el palo** si tienen alguna carta de ese palo.
- Si no tienen cartas del palo de salida, pueden jugar **cualquier carta** (con las restricciones de abajo).

### 4. Ganador de la mano
Gana la mano quien jugó la **carta más alta del palo de salida**. Las cartas de otros palos nunca ganan, sin importar su valor.

El ganador:
- Se queda con todas las cartas de la mesa (que le pueden sumar puntos).
- **Comienza la siguiente mano.**

### 5. Fin de la ronda
La ronda termina cuando se jugaron las 13 manos. Cada jugador cuenta los puntos de las cartas que ganó y se suman a su puntaje total.

---

## Reglas especiales

- **Primera mano:** no se pueden jugar cartas de puntos (Corazones ni la Reina de Picas), salvo que el jugador no tenga otra opción (por ejemplo, que solo tenga cartas con puntos).
- **Romper corazones:** no se puede **salir** (abrir una mano) con una carta de Corazones hasta que algún jugador haya jugado un corazón previamente en la ronda, salvo que el jugador solo tenga corazones en la mano.
- **Seguir el palo:** es obligatorio siempre que se tenga una carta del palo de salida.

---

## Fin de la partida

- La partida finaliza cuando un jugador alcanza o supera **100 puntos** al terminar una ronda.
- **Gana** el jugador con el **menor puntaje total**.
- **Pierde** el jugador con el mayor puntaje total.

---

## Estrategia básica

- Intentá **descartarte de cartas altas** de Corazones y de la Reina de Picas cuando no tengas que seguir el palo.
- Evitá ganar manos en las que se hayan jugado cartas con puntos.
- Tené cuidado con las Picas altas (A♠, K♠): pueden obligarte a cargar con la Reina de Picas.

---

## Arquitectura del proyecto

El proyecto sigue el patrón **MVC** con **Observer**:

| Paquete | Contenido |
|---|---|
| `Modelo` | `JuegoCorazones`, `Jugador`, `Carta`, `Mazo`, `Mesa`, `Jugada`, `Puntaje`, `Palo`, `Valor`, `Evento` |
| `Vista` | `VistaConsola` |
| `Controlador` | `ControladorConsola` |
| `Observer` | `IObservable`, `IObservador` |

El modelo notifica los cambios (inicio de ronda, carta jugada, fin de mano, fin de ronda, juego terminado, etc.) a través de eventos, y el controlador reacciona actualizando la vista.

---

## Cómo ejecutar

Ejecutar la clase principal:

```
ar.unlu.poo.MainConsola
```

Seguir las instrucciones por consola: ingresar los nombres de los jugadores y elegir cartas por su índice cuando sea tu turno.
