package martin.site.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import martin.site.documents.Curso;

public interface ICursoRepo extends ReactiveMongoRepository<Curso, String> {

}
