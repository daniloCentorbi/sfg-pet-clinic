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

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
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
    void showOwner() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        mockMVC.perform(get("/owners/12"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner" , hasProperty("id" , is(1L))));

        verify(ownerService,times(1)).findById(any());
    }

    @Test
    void initFindForm() throws Exception {
        mockMVC.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));
        verifyZeroInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception{
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(listOwners);

        mockMVC.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
        verify(ownerService,times(1)).findAllByLastNameLike(any());
    }

    @Test
    void processFindFormReturnOne() throws Exception{
        listOwners.clear();
        listOwners.add(Owner.builder().id(1L).build());
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(listOwners);

        mockMVC.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
        verify(ownerService,times(1)).findAllByLastNameLike(any());
    }

    @Test
    void processFindFormReturnZero() throws Exception{
        mockMVC.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
        verify(ownerService,times(1)).findAllByLastNameLike(any());
    }
}