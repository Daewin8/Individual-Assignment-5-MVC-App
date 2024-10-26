package com.csc340.animaldemo.animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AnimalController.java.
 * Includes all REST API endpoint mappings for the Animal object.
 */
//@RestController
@Controller
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private AnimalService service;

    /**
     * Get a list of all Animals in the database.
     * http://localhost:8080/animals/all
     *
     * @return a list of Animal objects.
     */
    @GetMapping("/all")
    //public List<Animal> getAllAnimals() {return service.getAllAnimals();}
    public String getAllAnimals(Model model) {
        model.addAttribute("animalList", service.getAllAnimals());
        model.addAttribute("title", "All Animals");
        return "animal-list";
    }

    /**
     * Get a specific Animal by Id.
     * http://localhost:8080/animals/2
     *
     * @param animalId the unique Id for an Animal.
     * @return One Animal object.
     */
    @GetMapping("/{animalId}")
    //public Animal getOneAnimal(@PathVariable int animalId) {return service.getAnimalById(animalId);}
    public String getOneAnimal(@PathVariable int animalId, Model model) {
        model.addAttribute("animal", service.getAnimalById(animalId));
        model.addAttribute("title", "Animal Details: " + animalId);
        return "animal-details";
    }

    @GetMapping ("/createForm")
    public String showCreateForm(){
        return "animal-create";
    }

    /**
     * Add a new Animal entry.
     * http://localhost:8080/animals/new --data '{ "name": "Blue Jay", "scientificName": "Cyanocitta cristata", "species": "Bird", "habitat": "Forest", "description": "A blue bird with a loud call." }'
     *
     * @param animal the new Animal object.
     * @return the updated list of Animals.
     */
    @GetMapping("/new")
    //public List<Animal> addNewAnimal(@RequestBody Animal animal) {service.addNewAnimal(animal);return service.getAllAnimals();}
    public String addNewAnimal(Animal animal) {
        service.addNewAnimal(animal);
        return "redirect:/animals/all";
    }

    /**
     * Update an existing Animal object.
     * http://localhost:8080/animals/update/2 --data '{ "name": "Updated Animal", "scientificName": "Updated Name", "species": "Mammal", "habitat": "Desert", "description": "Updated description." }'
     *
     * @param animalId the unique Animal Id.
     * @param model   the new update Animal details.
     * @return the updated Animal object.
     */
    @GetMapping("/updateForm/{animalId}")
    //public Animal updateAnimal(@PathVariable int animalId, @RequestBody Animal animal) {service.updateAnimal(animalId, animal);return service.getAnimalById(animalId);}
    public String showUpdateForm(@PathVariable int animalId, Model model) {
        model.addAttribute("animal", service.getAnimalById(animalId));
        return "animal-update";
    }

    /**
     * Perform the update of an existing Animal.
     *
     * @param animal the updated Animal object.
     * @return redirect to the updated Animal details page.
     */
    @PostMapping("/update")
    public String updateAnimal(Animal animal) {
        service.updateAnimal(animal);
        return "redirect:/animals/" + animal.getAnimalId();
    }

    /**
     * Delete an Animal object.
     * http://localhost:8080/animals/delete/2
     *
     * @param animalId the unique Animal Id.
     * @return the updated list of Animals.
     */
    @GetMapping("/delete/{animalId}")
    //public List<Animal> deleteAnimalById(@PathVariable int animalId) {service.deleteAnimalById(animalId);return service.getAllAnimals();}
    public String deleteAnimalById(@PathVariable int animalId) {
        service.deleteAnimalById(animalId);
        return "redirect:/animals/all";
    }

    /**
     * Get all animals of a given class.
     * http://localhost:8080/animals/class?species=Bird
     *
     * @param species the search key.
     * @return a list of Animal objects matching the search key.
     */
    @GetMapping("/class")
    //public List<Animal> getAnimalsBySpecies(@RequestParam(name = "species") String species) {return service.getAnimalsBySpecies(species);}
    public String getAnimalsBySpecies(@RequestParam(name = "species") String species, Model model) {
        model.addAttribute("animalList", service.getAnimalsBySpecies(species));
        model.addAttribute("title", "Animals of Species: " + species);
        return "animal-list";
    }

    /**
     * Get animals whose name contains a string.
     * http://localhost:8080/animals/search?name=blue
     *
     * @param name the search key.
     * @return a list of Animal objects whose name contains the search key.
     */
    @GetMapping("/search")
    //public List<Animal> getAnimalsByName(@RequestParam(name = "name") String name) {return service.getAnimalsByName(name);}
    public String getAnimalsByName(@RequestParam(name = "name") String name, Model model) {
        model.addAttribute("animalList", service.getAnimalsByName(name));
        model.addAttribute("title", "Search Results for Name: " + name);
        return "animal-list";
    }
}
