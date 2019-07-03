package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @InjectMocks
    OwnerController controller;

    @Mock
    OwnerService ownerService;

    @Mock
    Model model;

    MockMvc mockMVC;

    Set<Owner> listOwners;

    @BeforeEach
    void setUp() {
        listOwners = new HashSet<>();
        listOwners.add(Owner.builder().id(1L).lastName("White").build());
        listOwners.add(Owner.builder().id(2L).lastName("Smith").build());
        mockMVC = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listOwner() throws Exception {
        //mock interaction
        when(ownerService.findAll()).thenReturn(listOwners);

        mockMVC.perform(get("/owners"))
                .andExpect(status().is(200))
        .andExpect(view().name("owners/index"))
        .andExpect(model().attribute("owners" , hasSize(2)));
    }


    @Test
    void showOwner() {
    }

    @Test
    void initFindForm() {
    }

    @Test
    void processFindForm() {
    }
}