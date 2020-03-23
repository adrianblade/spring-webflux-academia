package martin.site.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import martin.site.documents.Curso;
import martin.site.repo.ICursoRepo;
import martin.site.services.ICursoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CursoServicesImpl implements ICursoService {

	@Autowired
	private ICursoRepo repo;
	
	@Override
	public Mono<Curso> registrar(Curso t) {
		return repo.save(t);
	}

	@Override
	public Mono<Curso> modificar(Curso t) {
		return repo.save(t);
	}

	@Override
	public Flux<Curso> listar() {
		return repo.findAll();
	}

	@Override
	public Mono<Curso> listarPorId(String id) {
		return repo.findById(id);
	}

	@Override
	public Mono<Void> eliminar(String id) {
		return repo.deleteById(id);
	}

}
