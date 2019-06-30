package guru.springframework.sfgpetclinic.springjpadata;


import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.VetRepository;
import guru.springframework.sfgpetclinic.repositories.VisitReposotory;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdataprofile")
public class VetSDJpaService implements VetService {

    private final VetRepository vetRepository;
    private final OwnerRepository ownerRepository;
    private final VisitReposotory visitReposotory;

    public VetSDJpaService(VetRepository vetRepository, OwnerRepository ownerRepository, VisitReposotory visitReposotory) {
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.visitReposotory = visitReposotory;
    }

    @Override
    public Vet findById(Long aLong) {
        return vetRepository.findById(aLong).orElse(null);
    }

    @Override
    public Vet save(Vet object) {
        return vetRepository.save(object);
    }

    @Override
    public Set<Vet> findAll() {
        Set<Vet> vetList = new HashSet<>();
        vetRepository.findAll().forEach(vetList::add);
        return null;
    }

    @Override
    public void delete(Vet object) {
        vetRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        vetRepository.deleteById(aLong);
    }
}
