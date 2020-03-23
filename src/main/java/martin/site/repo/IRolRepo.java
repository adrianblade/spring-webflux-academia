package martin.site.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import martin.site.documents.Rol;

public interface IRolRepo extends ReactiveMongoRepository<Rol, String> {

}
