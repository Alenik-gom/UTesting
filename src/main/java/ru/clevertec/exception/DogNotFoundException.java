package ru.clevertec.exception;

import ru.clevertec.common.DogBreed;

import java.util.UUID;

public class DogNotFoundException extends RuntimeException{
    private DogNotFoundException (String message){
        super(message);
    }
    public static DogNotFoundException dogById (UUID dogId){
        return new DogNotFoundException(
                String.format("Dog not found by id %s", dogId)
        );
    }
    public static DogNotFoundException dogByBreed (DogBreed dogBreed){
        return new DogNotFoundException(
                String.format("Dogs not found by breed %s", dogBreed)
        );
    }
}
