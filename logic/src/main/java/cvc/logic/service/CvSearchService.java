package cvc.logic.service;

import cvc.domain.Cv;
import cvc.domain.PersonalDetails;
import cvc.logic.interfaces.ICvRepository;
import cvc.logic.interfaces.service.ICvSearchService;
import cvc.logic.specifications.CvSpecifications;
import cvc.logic.specifications.PersonalDetailsSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CvSearchService implements ICvSearchService {

    private final ICvRepository repository;

    public CvSearchService(ICvRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Cv> minAge(short ageMin) {
        Specification<Cv> specs = CvSpecifications.ageGreaterThan(ageMin);
        List<Cv> searchResults = repository.findAll(specs);
        return searchResults;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Cv> maxAge(short ageMax) {
        Specification<Cv> specs = CvSpecifications.ageLessThan(ageMax);
        List<Cv> searchResults = repository.findAll(specs);
        return searchResults;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Cv> nameStartsWith(String startsWith) {
        Specification<Cv> specs = CvSpecifications.nameStartsWith(startsWith);
        List<Cv> searchResults = repository.findAll(specs);
        return searchResults;
    }
}
