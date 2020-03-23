package martin.site.handler;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import martin.site.documents.Matricula;
import martin.site.services.IMatriculaService;
import martin.site.validators.RequestValidator;
import reactor.core.publisher.Mono;

@Component
public class MatriculaHandler {

	@Autowired
	private IMatriculaService service;
	
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
				.flatMap(m -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.bodyValue(m)
						)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> registrar(ServerRequest req) {
		Mono<Matricula> matricula = req.bodyToMono(Matricula.class);
		return matricula.flatMap(this.validatoGlobal::validar)
				.flatMap(m -> {
					if(m.getFechaMatricula() == null) {
						m.setFechaMatricula(LocalDateTime.now());
					}
					return service.registrar(m);
					})
				.flatMap(m -> ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(m.getId())))
						.contentType(MediaType.APPLICATION_STREAM_JSON)
						.bodyValue(m)
						);
	}
	
	public Mono<ServerResponse> modificar(ServerRequest req) {
		Mono<Matricula> matricula = req.bodyToMono(Matricula.class);
		return matricula.flatMap(this.validatoGlobal::validar)
				.flatMap(c -> service.listarPorId(c.getId())
						.flatMap(db -> {
							db.setDescripcion(c.getDescripcion());
							db.setEstudiante(c.getEstudiante());
							db.setCursos(c.getCursos());
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
				.flatMap(m -> service.eliminar(m.getId())
						.then(ServerResponse.noContent().build())
						)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
}
