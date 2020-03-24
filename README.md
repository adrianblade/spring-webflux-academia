# Examen SPRING REACTIVO MITOCODE
## Fuentes
1. Descargar las fuentes:
   
   git clone https://github.com/marvicgit/spring-webflux-academia.git

2. Ejecutar el aplicativo con base de datos mongo:
   
   mvn spring-boot:run
   
   Cuando inicie el aplicativo cargara por defecto algunos estudiantes, cursos, roles y usuarios.

3. Ingresar desde el postman em la url de login: http://localhost:8080/login para 
   obtener el token de autorizacion

   Ingresar en body lo siguiente:

   {
    "username": "MITOCODE",
    "password": "123"
   }
    
3. Los metodos disponibles para estudiantes son:
    
    listar estudiantes:
     
     GET - http://localhost:8080/estudiantes
   
   listar estudiantes ordenados descendentes por edad:
     
     GET - http://localhost:8080/estudiantes/listOrderDescEdad

   listar estudiantes ordenados en paralelo descendentes por edad:
     
     GET - http://localhost:8080/estudiantes/listParalOrderDescEdad

   buscar estudiante por id:
     
     GET - http://localhost:8080/estudiantes/{id}

   registrar estudiante:
     
     POST - http://localhost:8080/estudiantes

   modificar estudiante:
     
     PUT - http://localhost:8080/estudiantes

   eliminar estudiante por id:
        
     DELETE - http://localhost:8080/estudiantes/{id}

5. Los metodos disponibles para cursos son:

    listar cursos:
     
     GET - http://localhost:8080/cursos

   buscar curso por id:
     
     GET - http://localhost:8080/cursos/{id}

   registrar curso:
     
     POST - http://localhost:8080/cursos

   modificar curso:
     
     PUT - http://localhost:8080/cursos

   eliminar curso por id:
     
     DELETE - http://localhost:8080/cursos/{id}

6. Los metodos disponibles para matriculas son:

    listar matriculas:
     
     GET - http://localhost:8080/matriculas

   buscar matricula por id:
     
     GET - http://localhost:8080/matriculas/{id}

   registrar matricula:
     
     POST - http://localhost:8080/matriculas

   modificar matricula:
     
     PUT - http://localhost:8080/matriculas

   eliminar matricula por id:
     
     DELETE - http://localhost:8080/matriculas/{id}

7. Para registrar una matricula utlize este formato json agregandole los ids. :

{
    
    "descripcion": "xxxxxxxxxx",

    "estudiante": {
        
        "id" : ""

        },
    "cursos": 
        [
            {
                "id" : ""
            },
            {
                "id" : ""
            }
        ],
    "estado": true
    
}

















