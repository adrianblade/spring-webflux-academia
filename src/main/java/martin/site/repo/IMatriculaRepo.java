package martin.site.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import martin.site.documents.Matricula;

public interface IMatriculaRepo extends ReactiveMongoRepository<Matricula, String> {

}
