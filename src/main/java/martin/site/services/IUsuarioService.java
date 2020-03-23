package martin.site.services;

import martin.site.documents.Usuario;
import martin.site.security.User;
import reactor.core.publisher.Mono;

public interface IUsuarioService extends ICRUD<Usuario, String>{

	Mono<User> buscarPorUsuario(String usuario);
}
