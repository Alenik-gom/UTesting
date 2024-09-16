package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.common.DogBreed;
import ru.clevertec.domain.Dog;
import ru.clevertec.entity.DogEntity;
import ru.clevertec.exception.DogNotFoundException;
import lombok.AllArgsConstructor;
import ru.clevertec.mapper.DogMapper;
import ru.clevertec.repository.DogRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DogService {
    private final DogRepository dogRepository;
    private final DogMapper dogMapper;

    public List<Dog> getDogs() {
        List<DogEntity> dogs = dogRepository.getDogs();
        return dogMapper.toDomains(dogs);
    }

    public Dog getDogById(UUID dogId) {
        DogEntity dog = dogRepository.getDogById(dogId)
                .orElseThrow(() -> DogNotFoundException.dogById(dogId));
        return dogMapper.toDomain(dog);
    }
    public List<Dog> getDogsByBreed(DogBreed dogBreed) {
        List<DogEntity> dogs = dogRepository.getDogsByBreed(dogBreed)
                .orElseThrow(() -> DogNotFoundException.dogByBreed(dogBreed));
        return dogMapper.toDomains(dogs);
    }

    public Dog createDog(Dog dog) {
        DogEntity dogEntity = dogMapper.toEntity(dog);
        DogEntity createdEntity = dogRepository.createDog(dogEntity);
        return dogMapper.toDomain(createdEntity);
    }

    public Dog updateDog(UUID dogId, Dog dog) {
        DogEntity dogEntity = dogMapper.toEntity(dog);
        DogEntity updatedEntity = dogRepository.updateDog(dogId, dogEntity);
        return dogMapper.toDomain(updatedEntity);
    }

    public void deleteDog(UUID dogId) {
        dogRepository.deleteDog(dogId);
    }
}
