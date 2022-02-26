# Por si el putito no jala

A ver, esto te lo dejo por si tienes alguna duda después de que te explique qpdo con el código y namas no jala el putito.
Este archivo lit es de emergencia y solo si te paniqueas, pero lo hago para que te guies visualmente por si algo pasa.

## Nombres de los dos motores en el robot
El putito solo tiene 2 motores que se controlan de manera separada, `motor_brazo` que es el motor que sube/baja el brazo y `motorB` que es el encargado de agarrar o soltar 
los objetivos del intake.

## Principales funciones de movimiento del robot
Para mover el robot para adelante o atrás se debe de usar la función `RobotMove(forward,turn)` donde _forward_ es la potencia para adelante y _turn_ es la potencia de giro.

Para mover los motores del intake y del brazo se debe de usar el nombre del motor y combinarlo con la terminación `.setPower(potencia)` donde los valores de _potencia_ van de -1 a 1.

### Ejemplos
Si se desea que el robot se mueva para adelante (a máxima potencia), la línea de la función se vería algo así
```java
RobotMove(1,0);
```

Si se desea que el robot gire hacia la izquierda (a máxima potencia), la línea de la función se vería algo así:

**Dentro de AutoPos1 y AutoPos3**
```java
RobotMove(0,-1);
```

**Dentro de AutoPos2 y AutoPos4**
```java
RobotMove(0,1);
```
Pueden combinarse, pero no resulta tan efectivo como si se separara.

Para el movimiento del brazo (usando valores de máxima potencia), las líneas de código se verían así:
```java
// Subir el brazo
motor_brazo.setPower(1);

//Bajar el brazo
motor_brazo.setPower(-1);
```

Para sacar o meter objetivos en el intake (usando valores de máxima potencia), las líneas de código se verían así:

```java
// Sacar
motorB.setPower(1);

// Meter
motorB.setPower(-1);
```

**Alch verifica esta mamada primero porque no me acuerdo si era así o al revés xd**


## Cómo formar un nuevo proceso (aka. Hacer un while)

El while básicamente consiste en repetir el mismo código mientras la condicional que se le de sea verdadera.
Si lo divides por partes, el while del robot tiene que repetir la misma instrucción mientras el tiempo que ha pasado desde el
último reset de runtime sea menor al que tu le establezcas y que opMode esté activado.

La condicional se tiene que ver algo como `(runtime.time() < tiempo && opModeIsActive())`, donde lo único que vas a cambiar es _tiempo_ y serán valores en segundos.

Las instrucciones dentro del while se van a repetir mientras no pase el tiempo que le pusiste, pero una vez pase tienes que _cancelar_ cualquier valor que pueda quedar volando
después del while. Con esto me refiero a que si tu mueves el robot para adelante por 1 segundo tienes que obligatoriamente poner debajo del while su respectivo `RobotMove(0,0);`
al igual que poner `runtime.reset();` porque así aseguras que tu robot comience a contar de nuevo desde 0 para la siguiente instrucción.

### Ejemplo
Si se requiere que el robot se mueva por 3 segundos continuos a máxima potencia pero justo después se ejecutará otra instrucción el código se vería algo así:

```java
while (runtime.time() < 3 && opModeIsActive()){
  RobotMove(1,0);
}
RobotMove(0,0);
runtime.reset();
```

Si se requiere que el robot se mueva para adelante y suba el brazo al mismo tiempo por 1.2 segundos el código se vería algo así:

```java
while (runtime.time() < 1.2 && opModeIsActive()){
  RobotMove(1,0);
  motor_brazo.setPower(0.15);
}
RobotMove(0,0);
motor_brazo.setPower(0);
runtime.reset();
```

**NOTA:** AutoPos3 y AutoPos4 son espejo pero no funcionaban en las prácticas porque las medidas 
taban culeras. AutoPos4 tiene comentado el código que AutoPos3 tiene al final.
