package lu.mypost.mep.repositoriy;

import lu.mypost.mep.model.document.template.MepTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends MongoRepository<MepTemplate, String> {
}
