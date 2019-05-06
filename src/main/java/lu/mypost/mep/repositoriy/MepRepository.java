package lu.mypost.mep.repositoriy;

import lu.mypost.mep.model.document.mep.Mep;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MepRepository extends MongoRepository<Mep, String> {
}
