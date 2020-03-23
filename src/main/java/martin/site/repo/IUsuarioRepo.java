package martin.site.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import martin.site.documents.Usuario;
import reactor.core.publisher.Mono;

public interface IUsuarioRepo extends ReactiveMongoRepository<Usuario, String> {

	Mono<Usuario> findOneByUsuario(String usuario);
}
