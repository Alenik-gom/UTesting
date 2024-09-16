package ru.clevertec.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.common.DogBreed;
import ru.clevertec.domain.Dog;
import ru.clevertec.entity.DogEntity;
import ru.clevertec.exception.DogNotFoundException;
import ru.clevertec.mapper.DogMapper;
import ru.clevertec.repository.DogRepository;
import ru.clevertec.util.TestData;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DogServiceTest {
    @Mock
    private DogRepository dogRepository;

    @Mock
    private DogMapper dogMapper;

    @InjectMocks
    private DogService dogService;

    @Test
    void shouldGetDogs() {
        //given
        List<DogEntity> dogEntities = List.of(TestData.genertedDogEntity());
        List<Dog> dogs = List.of(TestData.genertedDog());
        when(dogRepository.getDogs())
                .thenReturn(dogEntities);
        when(dogMapper.toDomains(dogEntities))
                .thenReturn(dogs);
        //when
        List<Dog> actualDogs = dogService.getDogs();
        //then
        assertFalse(actualDogs.isEmpty());
    }

    @Test
    void shouldGetDogById() {
        //given
        UUID dogId = UUID.randomUUID();
        DogEntity dogEntity = new DogEntity();
        Dog dog = new Dog();
        when(dogRepository.getDogById(dogId))
                .thenReturn(Optional.of(dogEntity));
        when(dogMapper.toDomain(dogEntity))
                .thenReturn(dog);
        //when
        Dog actualDog = dogService.getDogById(dogId);
        //then
        assertEquals(dog.getId(), actualDog.getId());
    }

    @Test
    void shouldThrowDogNotFoundException_whenDogNotFound() {
        //given
        UUID dogId = UUID.randomUUID();

        when(dogRepository.getDogById(dogId))
                .thenReturn(Optional.empty());
        //when, then
       assertThrows(DogNotFoundException.class,
               () -> dogService.getDogById(dogId));
     verifyNoInteractions(dogMapper);
    }

    @Test
    void shouldCreateDog() {
        //given
        Dog dog = TestData.genertedDog();
        DogEntity dogEntity = TestData.genertedDogEntity();

        when(dogMapper.toEntity(dog)).thenReturn(dogEntity);
        when(dogRepository.createDog(dogEntity)).thenReturn(dogEntity);
        when(dogMapper.toDomain(dogEntity)).thenReturn(dog);

        //when
        Dog createdDog = dogService.createDog(dog);

        //then
        assertEquals(dog.getId(), createdDog.getId());
        verify(dogRepository).createDog(dogEntity);
        verify(dogMapper).toEntity(dog);
        verify(dogMapper).toDomain(dogEntity);
    }

    @Test
    void shouldUpdateDog() {
        UUID dogId = UUID.randomUUID();
        Dog updatedInfoDog = new Dog(dogId, "Updated Name", DogBreed.DACHSHUND, LocalDate.of(2017, 01, 01));
        DogEntity existingDogEntity = new DogEntity(dogId, "Old Name", DogBreed.HUSKY, LocalDate.of(2017, 02, 01));
        DogEntity updatedDogEntity = new DogEntity(dogId, "Updated Name", DogBreed.DACHSHUND, LocalDate.of(2017, 01, 01));


        when(dogMapper.toEntity(updatedInfoDog)).thenReturn(updatedDogEntity);
        when(dogRepository.updateDog(dogId,updatedDogEntity)).thenReturn(updatedDogEntity);
        when(dogMapper.toDomain(updatedDogEntity)).thenReturn(updatedInfoDog);

        //when
        Dog actualUpdatedDog = dogService.updateDog(dogId, updatedInfoDog);

        //then
        assertEquals(updatedInfoDog.getName(), actualUpdatedDog.getName());
        assertEquals(updatedInfoDog.getDogBreed(), actualUpdatedDog.getDogBreed());
        assertEquals(updatedInfoDog.getBirthDate(), actualUpdatedDog.getBirthDate());
        verify(dogRepository).updateDog(dogId, updatedDogEntity);
        verify(dogMapper).toEntity(updatedInfoDog);
        verify(dogMapper).toDomain(updatedDogEntity);
    }
    @ParameterizedTest
    @EnumSource(DogBreed.class)  // Используем все породы, определенные в enum
    void shouldGetDogsByBreed_WhenDogsAreFound(DogBreed breed) {
        //given
        List<DogEntity> dogEntities = List.of(TestData.newDogEntity());
        List<Dog> expectedDogs = List.of(TestData.newDog());

        when(dogRepository.getDogsByBreed(breed)).thenReturn(Optional.of(dogEntities));
        when(dogMapper.toDomains(dogEntities)).thenReturn(expectedDogs);

        //when
        List<Dog> actualDogs = dogService.getDogsByBreed(breed);

        //then
        assertFalse(actualDogs.isEmpty());
        assertEquals(expectedDogs.size(), actualDogs.size());
        assertEquals(expectedDogs.get(0).getDogBreed(), actualDogs.get(0).getDogBreed());
    }
    @ParameterizedTest
    @EnumSource(value = DogBreed.class, names = {"HUSKY"})  // Тестируем только выбранные породы
    void shouldThrowDogNotFoundException_WhenDogsByBreedNotFound(DogBreed breed) {
        //given
        when(dogRepository.getDogsByBreed(breed)).thenReturn(Optional.empty());

        //when, then
        assertThrows(DogNotFoundException.class, () -> dogService.getDogsByBreed(breed));
    }
    @Test
    void shouldDeleteDog() {
        //given
        UUID dogId = UUID.randomUUID();
        //when
        dogService.deleteDog(dogId);
        //then
        verify(dogRepository).deleteDog(dogId);
    }
}