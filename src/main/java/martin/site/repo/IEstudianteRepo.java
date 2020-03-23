package martin.site.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import martin.site.documents.Estudiante;

public interface IEstudianteRepo extends ReactiveMongoRepository<Estudiante, String> {

}
