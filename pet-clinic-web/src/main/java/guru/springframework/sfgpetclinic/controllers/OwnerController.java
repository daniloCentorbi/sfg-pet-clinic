package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.exceptions.NotFoundException;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    //make id unmodificable
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/{ownerId}")
    public String showOwner(Model model, @PathVariable("ownerId") Long ownerId) {
        if(ownerService.findById(ownerId)==null){
            throw new NotFoundException("Owner Not Found");
        }
        model.addAttribute("owner", ownerService.findById(ownerId));
        return "owners/ownerDetails";
    }

    @GetMapping("/find")
    public String initFindForm(Map<String, Object> model) {
        model.put("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping({"","/", "/index", "/index.html"})
    public String processFindForm(Owner owner, BindingResult result, Model model) {

        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Set<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners found
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/createUpdateOwnerForm";
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return "owners/createUpdateOwnerForm";
        } else {
            ownerService.save(owner);
            owner.setId(owner.getId());
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable("ownerId") Long ownerId, Model model) {
        Owner owner = ownerService.findById(ownerId);
        model.addAttribute(owner);
        return "owners/createUpdateOwnerForm";
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") Long ownerId) {
        if (result.hasErrors()) {
            return "owners/createUpdateOwnerForm";
        } else {
            owner.setId(ownerId);
            ownerService.save(owner);
            return "redirect:/owners/" + owner.getId();
        }
    }

}
