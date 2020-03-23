package martin.site.handler;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import martin.site.documents.Curso;
import martin.site.services.ICursoService;
import martin.site.validators.RequestValidator;
import reactor.core.publisher.Mono;

@Component
public class CursoHandler {

	@Autowired
	private ICursoService service;
	
	@Autowired
	private RequestValidator validatoGlobal;
	
	public Mono<ServerResponse> listar(ServerRequest req) {
		return service.listar()
				.collectList()
				.flatMap(lista -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.bodyValue(lista));
	}
	
	public Mono<ServerResponse> listarPorId(ServerRequest req) {
		String id = req.pathVariable("id");
		return service.listarPorId(id)
				.flatMap(c -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.bodyValue(c)
						)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> registrar(ServerRequest req) {
		Mono<Curso> curso = req.bodyToMono(Curso.class);
		return curso.flatMap(this.validatoGlobal::validar)
				.flatMap(c -> service.registrar(c))
				.flatMap(c -> ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(c.getId())))
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.bodyValue(c)
						);
	}
	
	public Mono<ServerResponse> modificar(ServerRequest req) {
		Mono<Curso> curso = req.bodyToMono(Curso.class);
		return curso.flatMap(this.validatoGlobal::validar)
				.flatMap(c -> service.listarPorId(c.getId())
						.flatMap(db -> {
							db.setNombre(c.getNombre());
							db.setSigla(c.getSigla());
							return service.modificar(db);
						}))
				.flatMap(c -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.bodyValue(c)
						)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> eliminar(ServerRequest req) {
		String id = req.pathVariable("id");
		return service.listarPorId(id)
				.flatMap(c -> service.eliminar(c.getId())
						.then(ServerResponse.noContent().build())
						)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
}
