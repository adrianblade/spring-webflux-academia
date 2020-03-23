package martin.site.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import martin.site.documents.Matricula;
import martin.site.repo.IMatriculaRepo;
import martin.site.services.IMatriculaService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MatriculaServicesImpl implements IMatriculaService {

	@Autowired
	private IMatriculaRepo repo;
	
	@Override
	public Mono<Matricula> registrar(Matricula t) {
		return repo.save(t);
	}

	@Override
	public Mono<Matricula> modificar(Matricula t) {
		return repo.save(t);
	}

	@Override
	public Flux<Matricula> listar() {
		return repo.findAll();
	}

	@Override
	public Mono<Matricula> listarPorId(String id) {
		return repo.findById(id);
	}

	@Override
	public Mono<Void> eliminar(String id) {
		return repo.deleteById(id);
	}

}
