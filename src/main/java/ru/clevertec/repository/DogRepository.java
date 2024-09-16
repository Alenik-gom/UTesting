package ru.clevertec.repository;

import org.springframework.stereotype.Repository;
import ru.clevertec.common.DogBreed;
import ru.clevertec.entity.DogEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DogRepository {
    private static final List<DogEntity> db = List.of(
            new DogEntity(UUID.randomUUID(), "Afina", DogBreed.SHEPARD, LocalDate.of(2017, 01, 01)),
            new DogEntity(UUID.randomUUID(), "Leon", DogBreed.HUSKY, LocalDate.of(2018, 01, 01)),
            new DogEntity(UUID.randomUUID(), "Buddy", DogBreed.POODLE, LocalDate.of(2020, 02, 01)));

    public List<DogEntity> getDogs() {
        return db;
    }
    public Optional<DogEntity> getDogById(UUID dogId) {
        return Optional.of(db.get(0));
    }
    public Optional<List<DogEntity>> getDogsByBreed(DogBreed dogBreed) {
        return Optional.of(db.stream()
                .filter(dogEntity -> dogEntity.getDogBreed().equals(dogBreed)).toList());
    }

    public DogEntity createDog(DogEntity dogEntity) {
        return dogEntity;
    }
    public DogEntity updateDog(UUID dogId, DogEntity newDogEntity) {
        return newDogEntity.setId(dogId);
    }
    public void deleteDog(UUID dogId) {
        // without body
    }
}
