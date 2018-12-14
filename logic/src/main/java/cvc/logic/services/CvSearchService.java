package cvc.logic.services;

import client.entities.Dashboard;
import cvc.domain.Cv;
import cvc.domain.Users;
import cvc.logic.repositories.ICvRepository;
import cvc.logic.repositories.IUserRepository;
import cvc.logic.services.interfaces.ICvSearchService;
import cvc.logic.model.CvSearchCriteriaModel;
import cvc.logic.specifications.PersonalDetailsSpecifications;
import cvc.logic.specifications.UserSpecifications;
import enums.Genders;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CvSearchService implements ICvSearchService {

    private final ICvRepository repository;
    private final IUserRepository userRepository;

    public CvSearchService(ICvRepository repository, IUserRepository userRepository) {
        this.repository = repository;
        this.userRepository  = userRepository;
    }

    @Override
    public List<Cv> minAge(short ageMin) {
        Specification<Cv> specs = PersonalDetailsSpecifications.ageGreaterThan(ageMin);
        List<Cv> searchResults = getCvsWithSpecifications(specs);
        return searchResults;
    }

    @Override
    public List<Cv> maxAge(short ageMax) {
        Specification<Cv> specs = PersonalDetailsSpecifications.ageLessThan(ageMax);
        List<Cv> searchResults = getCvsWithSpecifications(specs);
        return searchResults;
    }

    @Override
    public Cv getById(long id) {
        OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        Cv cv = repository.findById(id).orElse(null);
        Users cvUser = cv.getUser();
        if (cv != null && cvUser != null && cvUser.getUsername().equals(authentication.getName())) {
            return cv;
        }

        return null;
    }

    @Override
    public List<Cv> withName(String name) {
        Specification<Cv> specs = PersonalDetailsSpecifications.nameStartsWith(name);
        List<Cv> searchResults = getCvsWithSpecifications(specs);
        return searchResults;
    }

    @Override
    public List<Cv> withGender(short gender) {
        Specification<Cv> specs = PersonalDetailsSpecifications.thisGenderOnly(Genders.fromId(gender));
        List<Cv> searchResults = getCvsWithSpecifications(specs);
        return searchResults;
    }

    @Override
    public List<Cv> findByFilter(CvSearchCriteriaModel criteriaModel) {
        if (criteriaModel == null) {
            return findUsersCv();
        }

        Specification<Cv> specs = criteriaModel.getSpecs();
        List<Cv> searchResults = getCvsWithSpecifications(specs);
        return searchResults;
    }

    @Override
    public List<Cv> findUsersCv() {
        OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        return this.repository.findAll(UserSpecifications.cvOfThisUser(authentication.getName()));
    }

    @Override
    public Dashboard getDashboardStats() {
        OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        final String username = authentication.getName();
        final LocalDate today = LocalDate.now();
        final LocalDate yesterday = today.minusDays(1);
        final LocalDate lastWeek = today.minusDays(7);

        long numCvs = repository.countByUser(username);
        long numCvsToday = repository.countByUserAndSubmittedTimeBetween(username, yesterday, today);
        long numCvsThisWeek = repository.countByUserAndSubmittedTimeBetween(username, lastWeek, today);

        return new Dashboard().setNumCvs(numCvs)
                .setSubmittedToday(numCvsToday)
                .setSubmittedThisWeek(numCvsThisWeek);
    }

    private List<Cv> getCvsWithSpecifications(Specification<Cv> specs) {
        OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        return this.repository.findAll(specs.and(UserSpecifications.cvOfThisUser(authentication.getName())));
    }
}
