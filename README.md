# Trabajo Práctico Anual 2024 - Diseño de Sistemas

Este repositorio contiene la solución al Trabajo Práctico Anual (TPA) desarrollado durante la cursada de la materia **Diseño de Sistemas** de la carrera **Ingeniería en Sistemas de Información** de la **Universidad Tecnológica Nacional - Facultad Regional Buenos Aires (UTN-FRBA)**.  
El trabajo fue realizado a lo largo de los dos cuatrimestres del ciclo lectivo 2024. La consigna oficial puede encontrarse en el siguiente [enlace](https://docs.google.com/document/d/13niiEppxrm8LjyrxmH5Pskrc7VVuPKWSFRi3WvhsXns/edit?tab=t.0).


## Integrantes del grupo
- **Facundo Gauna Somá**  
- **Nehuen Balian Amaros**  
- **Rocío Ochoa Charlín**  
- **Marco Bravo Pichihua**  
- **Eitan Wajsberg**


## Contexto

El proyecto responde a la necesidad de una ONG que instaló **heladeras solidarias** en diversas ubicaciones de Argentina (restaurantes, estaciones de transporte público y otros establecimientos estratégicos) para **mejorar el acceso a alimentos** de personas en situación de vulnerabilidad alimentaria.  

Sin embargo, el funcionamiento de este sistema presentaba diversos problemas:  
- Algunas heladeras permanecían días sin alimentos disponibles.  
- En ciertos casos, los alimentos se echaban a perder debido a la falta de rotación.  
- Los colaboradores no contaban con información actualizada sobre qué heladeras necesitaban donaciones, dónde estaban ubicadas o su estado de funcionamiento.  
- Problemas técnicos en las heladeras no eran detectados ni resueltos a tiempo.  

Para abordar estas dificultades, se desarrolló un sistema integral que automatiza y optimiza la gestión de estas heladeras solidarias.  


## Descripción del sistema

El **Sistema para la Mejora del Acceso Alimentario en Contextos de Vulnerabilidad Socioeconómica (SMAACVS)** es una solución tecnológica diseñada para:  
- Garantizar que las heladeras tengan alimentos disponibles y evitar el desperdicio de los mismos.  
- Brindar a los colaboradores información precisa sobre las heladeras: ubicación, estado operativo y necesidades específicas.  
- Detectar fallas técnicas en las heladeras y gestionar su reparación de manera eficiente mediante la asignación de personal capacitado.  


## Tecnologías y arquitectura utilizadas

El sistema se desarrolló utilizando las siguientes tecnologías:  
- **Lenguaje de programación:** Java.  
- **Framework Web:** Javalin.  
- **Template Engine:** Handlebars.  
- **ORM:** Hibernate.  
- **Despliegue:** AWS (Amazon Web Services).  

Estas herramientas y enfoques arquitectónicos se seleccionaron para garantizar una solución escalable, robusta y de fácil mantenimiento.  


## Uso del proyecto

### Requisitos

* Java 17: Si bien el proyecto no lo limita explícitamente, el comando `mvn verify` no funcionará con versiones más antiguas de Java. 
* JUnit 5: La versión 5 de JUnit es la más nueva del framework y presenta algunas diferencias respecto a la versión "clásica" (JUnit 4). Para mayores detalles, ver: 
  *  [Apunte de herramientas](https://docs.google.com/document/d/1VYBey56M0UU6C0689hAClAvF9ILE6E7nKIuOqrRJnWQ/edit#heading=h.dnwhvummp994)
  *  [Entrada de Blog (en inglés)](https://www.baeldung.com/junit-5-migration) 
  *  [Entrada de Blog (en español)](https://www.paradigmadigital.com/dev/nos-espera-junit-5/)
* Maven 3.8.1 o superior

### Ejecutar tests

```
mvn test
```

### Validar el proyecto de forma exahustiva

```
mvn clean verify
```

Este comando hará lo siguiente:

 1. Ejecutará los tests
 2. Validará las convenciones de formato mediante checkstyle
 3. Detectará la presencia de (ciertos) code smells
 4. Validará la cobertura del proyecto

