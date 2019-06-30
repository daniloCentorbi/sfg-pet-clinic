package guru.springframework.sfgpetclinic.springjpadata;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitReposotory;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdataprofile")
public class VisitSDJpaService implements VisitService {

    private final VisitReposotory visitReposotory;

    public VisitSDJpaService(VisitReposotory visitReposotory) {
        this.visitReposotory = visitReposotory;
    }

    @Override
    public Visit findById(Long aLong) {
        return visitReposotory.findById(aLong).orElse(null);
    }

    @Override
    public Visit save(Visit object) {
        return visitReposotory.save(object);
    }

    @Override
    public Set<Visit> findAll() {
        Set<Visit> visities = new HashSet<>();
        visitReposotory.findAll().forEach(visities::add);
        return visities;
    }

    @Override
    public void delete(Visit object) {
        visitReposotory.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        visitReposotory.deleteById(aLong);
    }
}
