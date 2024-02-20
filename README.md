# Parcial1-AREP

## Desarrollado por Santiago Parra


### Como ejecutar
  dentro de la carpeta ejecutar:
  desde IDE solo preionar ejecutar a cada clase
  
  > mvn exec:java -Dexec.mainClass="parcial.sp.Compute"
  > mvn exec:java -Dexec.mainClass="parcial.sp.HttpServer"
>
### Como usar

iniciar ambos servidores

un servidor correra por:

> http://localhost:4200/computar?name=cos(1.2)



debes ingresar a localhost por el puerto 35000 para poder accerder, luego el query debe tener el formato:

> http://localhost:35000/computar?name=cos(1.2)

 este es completamente funcional, al ingresar el query y comando veras como calcula la funcion


