package martin.site;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import martin.site.handler.CursoHandler;
import martin.site.handler.EstudianteHandler;
import martin.site.handler.LoginHandler;
import martin.site.handler.MatriculaHandler;

@Configuration
public class RouteConfig {

	@Bean
	public RouterFunction<ServerResponse> rutaLogin(LoginHandler handler) {
		return RouterFunctions.route(RequestPredicates.POST("/login"), handler::login);
	}
	
	@Bean
	public RouterFunction<ServerResponse> rutaEstudiantes(EstudianteHandler handler) {
		return RouterFunctions.route(RequestPredicates.GET("/estudiantes"), handler::listar)
				.andRoute(RequestPredicates.GET("/estudiantes/listParalOrderDescEdad"), handler::listParalOrderDescEdad)
				.andRoute(RequestPredicates.GET("/estudiantes/listOrderDescEdad"), handler::listOrderDescEdad)
				.andRoute(RequestPredicates.GET("/estudiantes/{id}"), handler::listarPorId)
				.andRoute(RequestPredicates.POST("/estudiantes"), handler::registrar)
				.andRoute(RequestPredicates.PUT("/estudiantes"), handler::modificar)
				.andRoute(RequestPredicates.DELETE("/estudiantes/{id}"), handler::eliminar);
	}
	
	@Bean
	public RouterFunction<ServerResponse> rutaCursos(CursoHandler handler) {
		return RouterFunctions.route(RequestPredicates.GET("/cursos"), handler::listar)
				.andRoute(RequestPredicates.GET("/cursos/{id}"), handler::listarPorId)
				.andRoute(RequestPredicates.POST("/cursos"), handler::registrar)
				.andRoute(RequestPredicates.PUT("/cursos"), handler::modificar)
				.andRoute(RequestPredicates.DELETE("/cursos/{id}"), handler::eliminar);
	}
	
	@Bean
	public RouterFunction<ServerResponse> rutaMatriculas(MatriculaHandler handler) {
		return RouterFunctions.route(RequestPredicates.GET("/matriculas"), handler::listar)
				.andRoute(RequestPredicates.GET("/matriculas/{id}"), handler::listarPorId)
				.andRoute(RequestPredicates.POST("/matriculas"), handler::registrar)
				.andRoute(RequestPredicates.PUT("/matriculas"), handler::modificar)
				.andRoute(RequestPredicates.DELETE("/matriculas/{id}"), handler::eliminar);
	}
}
