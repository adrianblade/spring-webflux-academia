package martin.site.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import martin.site.documents.Rol;
import martin.site.repo.IRolRepo;
import martin.site.services.IRolService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RolServiceImpl implements IRolService {

	@Autowired
	private IRolRepo repo;
	
	@Override
	public Mono<Rol> registrar(Rol t) {
		return repo.save(t);
	}

	@Override
	public Mono<Rol> modificar(Rol t) {
		return repo.save(t);
	}

	@Override
	public Flux<Rol> listar() {
		return repo.findAll();
	}

	@Override
	public Mono<Rol> listarPorId(String id) {
		return repo.findById(id);
	}

	@Override
	public Mono<Void> eliminar(String id) {
		return repo.deleteById(id);
	}

}
