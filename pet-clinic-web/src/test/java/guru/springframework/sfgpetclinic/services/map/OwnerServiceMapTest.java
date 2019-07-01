package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;

    final Long idOwner = 1L;
    final Long idOwner2 = 2L;

    final String lastName = "Smith";

    @BeforeEach
    void setUp() {
        //Map implementation injected with no Mock/ctx needed
    ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());

    ownerServiceMap.save(Owner.builder().id(idOwner).lastName(lastName).build());
    ownerServiceMap.save(Owner.builder().id(idOwner2).lastName("Wilbourne").build());
    }

    @Test
    void findById() {
        assertEquals(idOwner, ownerServiceMap.findById(idOwner).getId());
    }

    @Test
    void saveExistingId() {
        Long ownerId = 3L;
        Owner owner = Owner.builder().id(ownerId).build();
        Owner savedOwner = ownerServiceMap.save(owner);
        assertEquals(ownerId, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner savedOwner = ownerServiceMap.save(Owner.builder().build());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void findAll() {
        assertEquals(2, ownerServiceMap.findAll().size());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(idOwner);
        assertEquals(1, ownerServiceMap.findAll().size());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(idOwner));
        assertEquals(1, ownerServiceMap.findAll().size());
    }

    @Test
    void findByLastNameFound() {
        assertNotNull(ownerServiceMap.findByLastName("Smith"));
    }

    @Test
    void findByLastNameNull() {
        assertNull(ownerServiceMap.findByLastName("asdf"));
    }
}