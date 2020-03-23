package martin.site.handler;

import java.net.URI;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import martin.site.dto.LoginDTO;
import martin.site.security.AuthResponse;
import martin.site.security.ErrorLogin;
import martin.site.security.JWTUtil;
import martin.site.services.IUsuarioService;
import reactor.core.publisher.Mono;

@Component
public class LoginHandler {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private IUsuarioService service;
	
	public Mono<ServerResponse> login(ServerRequest req) {
		var login = req.bodyToMono(LoginDTO.class);
		
		return login
				.flatMap(l -> service.buscarPorUsuario(l.getUsername())
						.flatMap((userDetails) -> {
							String token = jwtUtil.generateToken(userDetails);
							Date expiracion = jwtUtil.getExpirationDateFromToken(token);
							
							if (BCrypt.checkpw(l.getPassword(), userDetails.getPassword())) {
								return ServerResponse.ok()
										.contentType(MediaType.APPLICATION_STREAM_JSON)
										.bodyValue(new AuthResponse(token, expiracion));
							} else {
								return ServerResponse.status(HttpStatus.UNAUTHORIZED)
										.contentType(MediaType.APPLICATION_STREAM_JSON)
										.bodyValue(new ErrorLogin("credenciales incorrectas", new Date()));
									
							}
						})).switchIfEmpty(ServerResponse.notFound().build());

	}
}
