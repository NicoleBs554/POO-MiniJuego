# POO-MiniJuego
# Study Break Runner

Un videojuego de carrera infinito donde controlas a un estudiante que debe evadir profesores y recolectar asignaciones durante su descanso de estudio.

## Características

### Mecánicas Principales:
- **Salto (Espacio/Flecha arriba)**: Evita profesores y alcanza asignaciones altas
- **Cambio de carril (Flechas izquierda/derecha)**: Esquiva obstáculos
- **Sistema de estamina**: Cada salto consume estamina
- **Combo multiplicador**: Recolectar asignaciones consecutivas aumenta el multiplicador
- **Puntos de beca**: Moneda del juego para desbloquear mejoras

### Tipos de Profesores:
- **Matemáticas**: Movimiento predecible
- **Historia**: Cambia de carril aleatoriamente
- **Ciencias**: Se mueve más rápido
- **Filosofía**: Más lento pero más ancho

### Tipos de Asignaciones:
- **Tarea**: +50 puntos
- **Ensayo**: +100 puntos
- **Proyecto**: +150 puntos
- **Crédito extra**: +200 puntos + regenera estamina

## Controles
- **ESPACIO / FLECHA ARRIBA**: Saltar
- **FLECHA IZQUIERDA**: Mover al carril izquierdo
- **FLECHA DERECHA**: Mover al carril derecho
- **P**: Pausar/Reanudar juego
- **R**: Reiniciar (en pantalla de Game Over)

## Instalación y Ejecución

### Requisitos:
- Java 11 o superior
- Maven (opcional)

### Ejecutar con Maven:
```bash
mvn compile
mvn exec:java -Dexec.mainClass="Main"