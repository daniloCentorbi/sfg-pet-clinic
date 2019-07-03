package guru.springframework.sfgpetclinic.springjpadata;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.VisitReposotory;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdataprofile")
public class OwnerSDJpaService implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final VisitReposotory visitReposotory;

    public OwnerSDJpaService(OwnerRepository ownerRepository, PetRepository petRepository, VisitReposotory visitReposotory) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.visitReposotory = visitReposotory;
    }

    @Override
    public Owner findByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    public Owner findById(Long aLong) {

        return ownerRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<Owner> findAllByLastNameLike(String lastName) {
        Set<Owner> allOwner = new HashSet<>();
        ownerRepository.findAllByLastNameLike(lastName).forEach((allOwner::add));
        return allOwner;
    }

    @Override
    public Owner save(Owner object) {
        return ownerRepository.save(object);
    }

    @Override
    public Set<Owner> findAll() {
        Set<Owner> allOwner = new HashSet<>();
        ownerRepository.findAll().forEach(allOwner::add);
        return allOwner;
    }

    @Override
    public void delete(Owner object) {
        ownerRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        ownerRepository.deleteById(aLong);
    }
}
