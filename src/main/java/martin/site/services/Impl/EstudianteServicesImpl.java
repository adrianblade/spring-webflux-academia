package martin.site.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import martin.site.documents.Estudiante;
import martin.site.repo.IEstudianteRepo;
import martin.site.services.IEstudianteServices;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EstudianteServicesImpl implements IEstudianteServices {

	@Autowired
	private IEstudianteRepo repo;
	
	@Override
	public Mono<Estudiante> registrar(Estudiante t) {
		return repo.save(t);
	}

	@Override
	public Mono<Estudiante> modificar(Estudiante t) {
		return repo.save(t);
	}

	@Override
	public Flux<Estudiante> listar() {
		return repo.findAll();
	}

	@Override
	public Mono<Estudiante> listarPorId(String id) {
		return repo.findById(id);
	}

	@Override
	public Mono<Void> eliminar(String id) {
		return repo.deleteById(id);
	}

}
