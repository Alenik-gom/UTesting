package ru.clevertec.util;

import ru.clevertec.common.DogBreed;
import ru.clevertec.domain.Dog;
import ru.clevertec.entity.DogEntity;

import java.time.LocalDate;
import java.util.UUID;

public class TestData {

    public static DogEntity genertedDogEntity() {
        return new DogEntity();
    }

    public static Dog genertedDog() {
        return new Dog();
    }
    public static Dog newDog(){
        UUID dogId = UUID.randomUUID();
        return new Dog(dogId, "Buddy", DogBreed.HUSKY, LocalDate.of(2017, 01, 01));
    }
    public static DogEntity newDogEntity(){
        UUID dogId = UUID.randomUUID();
        return new DogEntity(dogId, "Buddy", DogBreed.HUSKY, LocalDate.of(2017, 01, 01));
    }
}
