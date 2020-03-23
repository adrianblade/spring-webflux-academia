package martin.site.services.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import martin.site.security.User;
import org.springframework.stereotype.Service;

import martin.site.documents.Rol;
import martin.site.documents.Usuario;
import martin.site.repo.IUsuarioRepo;
import martin.site.services.IUsuarioService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioRepo repo;
	
	@Override
	public Mono<Usuario> registrar(Usuario t) {
		return repo.save(t);
	}

	@Override
	public Mono<Usuario> modificar(Usuario t) {
		return repo.save(t);
	}

	@Override
	public Flux<Usuario> listar() {
		return repo.findAll();
	}

	@Override
	public Mono<Usuario> listarPorId(String id) {
		return repo.findById(id);
	}

	@Override
	public Mono<Void> eliminar(String id) {
		return repo.deleteById(id);
	}

	@Override
	public Mono<User> buscarPorUsuario(String usuario) {
		Mono<Usuario> monoUsuario = repo.findOneByUsuario(usuario);

		List<String> roles = new ArrayList<String>();
				
		return monoUsuario.doOnNext(u -> {
			for (Rol role : u.getRoles()) {
				roles.add(role.getNombre());
			}
		}).flatMap(u -> {
			return Mono.just(new User(u.getUsuario(), u.getPassword(), u.getEstado(), roles));
		});
	}

}
