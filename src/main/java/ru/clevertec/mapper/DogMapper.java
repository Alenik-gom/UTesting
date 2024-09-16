package ru.clevertec.mapper;

import ch.qos.logback.core.model.ComponentModel;
import ru.clevertec.domain.Dog;
import ru.clevertec.entity.DogEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DogMapper {
    List<Dog> toDomains(List<DogEntity> dogs);

    Dog toDomain(DogEntity dog);

    DogEntity toEntity(Dog dog);
}
