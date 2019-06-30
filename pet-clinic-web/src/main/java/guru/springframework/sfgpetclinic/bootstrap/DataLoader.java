package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.SpecialityService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;


    //interfaces injected into the constructor,
    //IoC Automatically autowire 'em becouse are annotated by @Services
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();

        if(count == 0){
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");
        specialityService.save(dentistry);
        specialityService.save(surgery);
        specialityService.save(radiology);

        Owner owner1 = new Owner();
        owner1.setFirstName("Micheal");
        owner1.setLastName("Kane");
        owner1.setAddress("44 Miami STreet");
        owner1.setCity("Santa Cruz");
        owner1.setTelephone("004947776");

        Pet pet1 = new Pet();
        pet1.setPetType(savedDogPetType);
        pet1.setName("Dino");
        pet1.setBirthDate(LocalDate.now());
        pet1.setOwner(owner1);

        Pet pet2 = new Pet();
        pet2.setPetType(savedCatPetType);
        pet2.setName("Drill");
        pet2.setBirthDate(LocalDate.now());
        pet2.setOwner(owner1);

        Set<Pet> listPet = new HashSet<>();
        listPet.add(pet1);
        listPet.add(pet2);
        listPet.forEach((v) -> System.out.println(" Id:" + v.getId() + " Name: " + v.getName()));
        owner1.setPets(listPet);

        ownerService.save(owner1);
        listPet.forEach((v) -> System.out.println(" Id:" + v.getId() + " Name: " + v.getName()));

        Owner owner2 = new Owner();
        owner2.setFirstName("Jerry");
        owner2.setLastName("Blade");
        owner2.setAddress("444 Miami STreet");
        owner2.setCity("Sanat Catalina");
        owner2.setTelephone("004947776");

        Pet pet3 = new Pet();
        pet3.setPetType(savedCatPetType);
        pet3.setName("Rango");
        pet3.setBirthDate(LocalDate.now());
        pet3.setOwner(owner2);
        owner2.getPets().add(pet3);

        owner2.getPets().forEach(pet -> {
            System.out.println(pet.getName() + " Born on " + pet.getBirthDate());
                });

        ownerService.save(owner2);

        Vet vet1 = new Vet();
        vet1.setFirstName("Berry");
        vet1.setLastName("Lindon");
        vet1.getSpecialities().add(radiology);
        vet1.getSpecialities().add(dentistry);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("John");
        vet2.setLastName("Strawn");


        vet2.getSpecialities().addAll(specialityService.findAll());

        vetService.save(vet2);
    }
}
