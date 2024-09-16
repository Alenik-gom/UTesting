package ru.clevertec.entity;

import ru.clevertec.common.DogBreed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DogEntity {
    private UUID id;
    private String name;
    private DogBreed dogBreed;
    private LocalDate birthDate;
}
