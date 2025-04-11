# Simulación de Ciudad

Este proyecto es una simulación de una ciudad donde diferentes tipos de "personas" interactúan con elementos como semáforos, caminos y otros objetos dentro de un entorno urbano. La ciudad está representada mediante una cuadrícula en la que se encuentran elementos como personas, semáforos y otros objetos que interactúan entre sí según su lógica programada.

## Descripción del Proyecto

El proyecto simula el comportamiento de una ciudad dinámica con diferentes tipos de "personas" (como `Walker` y `Wallflower`) que se mueven por la ciudad y toman decisiones basadas en su vecindad y su entorno. Los semáforos (`TrafficLight`) también son parte de la simulación, con su color cambiando de rojo a verde a amarillo según el tiempo transcurrido. Los objetos y personas interactúan entre sí en una cuadrícula que simula el comportamiento urbano de una manera sencilla y eficiente.

## Objetivos

El objetivo de este proyecto es demostrar el uso de objetos y clases en Java para simular un sistema interactivo y dinámico. Los elementos claves incluyen:

- **Simulación de la ciudad**: Una ciudad representada por una cuadrícula de 25x25.
- **Tipos de personas**: `Walker` y `Wallflower` con comportamientos específicos.
- **Elementos interactivos**: Semáforos que cambian de color y afectan el comportamiento de las personas.
- **Interacciones dinámicas**: Las personas y objetos pueden moverse, tomar decisiones y cambiar de estado en función de su entorno.

## Estructura del Proyecto

El proyecto está compuesto por varias clases que representan a los distintos objetos y elementos de la ciudad. Estas son algunas de las clases principales:

- **`City`**: Representa la ciudad, donde se almacenan los elementos en una cuadrícula y se gestionan sus interacciones.
- **`Person`** (y sus subclases `Walker`, `Wallflower`): Representan a las personas en la ciudad, cada una con su comportamiento y lógica de movimiento.
- **`TrafficLight`**: Representa los semáforos de la ciudad, que cambian de color y afectan a las personas a su alrededor.
- **`Item`**: Interfaz que es implementada por las clases `Person` y `TrafficLight`, entre otras, para estandarizar los elementos de la ciudad.

## Contribuciones

Este proyecto fue desarrollado por:

- **Daniel Useche** 
- **Daniel Patiño**
- **Anonimo(No sabemos)**

## Instrucciones para Ejecutar

1. Clona el repositorio en tu máquina local.
   ```bash
   git clone <URL_DEL_REPOSITORIO>
