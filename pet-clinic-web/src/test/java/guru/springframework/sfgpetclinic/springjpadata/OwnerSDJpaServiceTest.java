package guru.springframework.sfgpetclinic.springjpadata;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.VisitReposotory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    VisitReposotory visitReposotory;

    //service instantiate and inject
    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    Owner owner;
    Long ID_OWNER = 1L;
    String LAST_NAME = "Smith";

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(ID_OWNER).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {

        when(ownerRepository.findByLastName(any())).thenReturn(owner);

        assertEquals(LAST_NAME, ownerSDJpaService.findByLastName(LAST_NAME).getLastName());

        verify(ownerRepository,times(1)).findByLastName(any());
    }

    @Test
    void findById() {

        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));

        assertNotNull(ownerSDJpaService.findById(ID_OWNER));
    }

    @Test
    void save() {

        when(ownerRepository.save(any())).thenReturn(owner);

        assertNotNull(ownerSDJpaService.save(owner));
    }

    @Test
    void findAll() {
        Set<Owner> list = new HashSet<>();
        Owner owner2 = Owner.builder().id(2L).lastName("Gilly").build();
        list.add(owner);
        list.add(owner2);

        when(ownerRepository.findAll()).thenReturn(list);

        assertEquals(2, ownerSDJpaService.findAll().size());
    }

    @Test
    void delete() {

        ownerSDJpaService.delete(ownerSDJpaService.findById(ID_OWNER));

        assertNull(ownerSDJpaService.findById(ID_OWNER));
    }

    @Test
    void deleteById() {

        ownerSDJpaService.deleteById(ID_OWNER);

        assertNull(ownerSDJpaService.findById(ID_OWNER));
    }
}