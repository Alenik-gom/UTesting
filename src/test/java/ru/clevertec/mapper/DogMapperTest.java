package ru.clevertec.mapper;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import ru.clevertec.domain.Dog;
import ru.clevertec.entity.DogEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DogMapperTest {
    private final DogMapper mapper = new DogMapperImpl();
    private final EasyRandom generator = new EasyRandom();

    @Test
    void toDomains() {
        List<DogEntity> dogEntities = generator.objects(DogEntity.class, 5).toList();

        List<Dog> dogs = mapper.toDomains(dogEntities);

        // Проверяем, что каждый элемент преобразован корректно
        assertEquals(dogEntities.size(), dogs.size());
        for (int i = 0; i < dogEntities.size(); i++) {
            DogEntity entity = dogEntities.get(i);
            Dog dog = dogs.get(i);
            assertAll(
                    () -> assertEquals(entity.getId(), dog.getId()),
                    () -> assertEquals(entity.getName(), dog.getName()),
                    () -> assertEquals(entity.getBirthDate(), dog.getBirthDate()),
                    () -> assertEquals(entity.getDogBreed(), dog.getDogBreed())
            );
        }
    }

    @Test
    void toDomain() {
        DogEntity dogEntity = generator.nextObject(DogEntity.class);

        Dog dog = mapper.toDomain(dogEntity);

        assertNotNull(dog);
        assertAll(
                () -> assertEquals(dogEntity.getId(), dog.getId()),
                () -> assertEquals(dogEntity.getName(), dog.getName()),
                () -> assertEquals(dogEntity.getBirthDate(), dog.getBirthDate()),
                () -> assertEquals(dogEntity.getDogBreed(), dog.getDogBreed())
        );
    }

    @Test
    void toEntity() {
        Dog dog = generator.nextObject(Dog.class);

        DogEntity dogEntity = mapper.toEntity(dog);

        assertNotNull(dogEntity);
        assertAll(
                () -> assertEquals(dog.getId(), dogEntity.getId()),
                () -> assertEquals(dog.getName(), dogEntity.getName()),
                () -> assertEquals(dog.getDogBreed(), dogEntity.getDogBreed()),
                () -> assertEquals(dog.getBirthDate(), dogEntity.getBirthDate())
        );
    }
}