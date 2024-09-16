package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.common.DogBreed;
import ru.clevertec.domain.Dog;
import ru.clevertec.service.DogService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dogs")
@RequiredArgsConstructor
public class DogController {
    private final DogService dogService;

    @GetMapping
    public ResponseEntity<List<Dog>> findAll(){
        List<Dog> dogs = dogService.getDogs();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dogs);

    }
    @GetMapping("/{dogId}")
    public ResponseEntity<Dog> getDogById(@PathVariable("dogId") UUID dogId) {
        Dog dogById = dogService.getDogById(dogId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dogById);
    }
    @GetMapping("/breed/{dogBreed}")
    public ResponseEntity<List<Dog>> getDogsByBreed(@PathVariable("dogBreed") DogBreed dogBreed) {
        List<Dog> dogsByBreed = dogService.getDogsByBreed(dogBreed);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dogsByBreed);
    }
    @PostMapping
    public ResponseEntity<Dog> createDog(@RequestBody Dog dog) {
        Dog dogCreated = dogService.createDog(dog);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dogCreated);
    }
    @PutMapping("/{dogId}")
    public ResponseEntity<Dog> updateDog(@PathVariable("dogId") UUID dogId, @RequestBody Dog dog) {
        Dog dogUpdated = dogService.updateDog(dogId, dog);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dogUpdated);
    }
    @DeleteMapping("/{dogId}")
    public ResponseEntity<Void> deleteDog(@PathVariable("dogId") UUID dogId) {
        dogService.deleteDog(dogId);
        return ResponseEntity.noContent().build();
    }
}
