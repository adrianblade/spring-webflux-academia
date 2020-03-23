package martin.site;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import martin.site.documents.Curso;
import martin.site.documents.Estudiante;
import martin.site.documents.Rol;
import martin.site.documents.Usuario;
import martin.site.services.ICursoService;
import martin.site.services.IEstudianteServices;
import martin.site.services.IRolService;
import martin.site.services.IUsuarioService;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringWebfluxAcademiaApplication implements CommandLineRunner {

	@Autowired
	private IEstudianteServices serviceEstudiante;
	
	@Autowired
	private ICursoService serviceCurso;
	
	@Autowired
	private IRolService serviceRol;
	
	@Autowired
	private IUsuarioService serviceUsuario;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	@Autowired
	private BCryptPasswordEncoder bCryp;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringWebfluxAcademiaApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("cursos").subscribe();
		mongoTemplate.dropCollection("estudiantes").subscribe();
		mongoTemplate.dropCollection("usuarios").subscribe();
		mongoTemplate.dropCollection("roles").subscribe();
		
		// Insertar Estudiantes
		Flux.just(new Estudiante("Martin", "Carrillo Durand", "47408900", 27),
				  new Estudiante("Juan", "Martinez Peres", "45896321", 35),
				  new Estudiante("Lorena", "Maza Zapata", "48652341", 30),
				  new Estudiante("Marisol", "Granda Huarcaya", "78563214", 29),
				  new Estudiante("Evelyn", "Velasquez", "00685741", 37)
				)
		.flatMap(e -> serviceEstudiante.registrar(e))
		.subscribe();
		
		// Insertar Cursos
		Flux.just(new Curso("Spring Reactivo", "SR", true),
				  new Curso("Java Docker", "DOCKER", true),
				  new Curso("Java Full Stack", "JF", true),
				  new Curso("Angular", "A", true)
				)
		.flatMap(c -> serviceCurso.registrar(c))
		.subscribe();
		
		
		Rol rol1 = new Rol("ADMIN");
		Rol rol2 = new Rol("USER");

		// Inserta roles y usuarios
		Flux.just(rol1, rol2)
		.flatMap(serviceRol::registrar)
		.thenMany(
				Flux.just(new Usuario("MITOCODE", bCryp.encode("123"), true, new ArrayList<Rol>( Arrays.asList(rol1))), 
						  new Usuario("CODE", bCryp.encode("123"), true, new ArrayList<Rol>( Arrays.asList(rol2))))
						  )
				.flatMap(u -> serviceUsuario.registrar(u))
				.subscribe();
		
		
	}

}
