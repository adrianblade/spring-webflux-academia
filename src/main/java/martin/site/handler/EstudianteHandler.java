package martin.site.handler;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import martin.site.documents.Estudiante;
import martin.site.services.IEstudianteServices;
import martin.site.validators.RequestValidator;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class EstudianteHandler {

	@Autowired
	private IEstudianteServices service;
	
	@Autowired
	private RequestValidator validatoGlobal;
	
	public Mono<ServerResponse> listar(ServerRequest req) {
		return service.listar()
				.collectList()
				.flatMap(lista -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.bodyValue(lista));
	}
	
	public Mono<ServerResponse> listOrderDescEdad(ServerRequest req) {
		return service.listar()
				.collectSortedList((e1, e2) -> (int)e2.getEdad() - (int)e1.getEdad())
				.flatMap(lista -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.bodyValue(lista));
	}
	
	public Mono<ServerResponse> listParalOrderDescEdad(ServerRequest req) {
		return service.listar()
				.parallel()
				.runOn(Schedulers.elastic())
				.flatMap(e -> service.listarPorId(e.getId()))
				.ordered((e1, e2) -> (int)e2.getEdad() - (int)e1.getEdad())
				.collectList()
				.flatMap(lista -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.bodyValue(lista));
	}
	
	public Mono<ServerResponse> listarPorId(ServerRequest req) {
		String id = req.pathVariable("id");
		return service.listarPorId(id)
				.flatMap(e -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.bodyValue(e)
						)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> registrar(ServerRequest req) {
		Mono<Estudiante> estudiante = req.bodyToMono(Estudiante.class);
		return estudiante.flatMap(this.validatoGlobal::validar)
				.flatMap(e -> service.registrar(e))
				.flatMap(e -> ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(e.getId())))
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.bodyValue(e)
						);
	}
	
	public Mono<ServerResponse> modificar(ServerRequest req) {
		Mono<Estudiante> estudiante = req.bodyToMono(Estudiante.class);
		return estudiante.flatMap(this.validatoGlobal::validar)
				.flatMap(e -> service.listarPorId(e.getId())
						.flatMap(db -> {
							db.setApellidos(e.getApellidos());
							db.setNombres(e.getNombres());
							db.setDni(e.getDni());
							db.setEdad(e.getEdad());
							return service.modificar(db);
						}))
				.flatMap(e -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.bodyValue(e)
						)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> eliminar(ServerRequest req) {
		String id = req.pathVariable("id");
		return service.listarPorId(id)
				.flatMap(e -> service.eliminar(e.getId())
						.then(ServerResponse.noContent().build())
						)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
}
