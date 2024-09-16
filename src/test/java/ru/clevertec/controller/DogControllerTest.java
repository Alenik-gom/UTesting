package ru.clevertec.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.clevertec.common.DogBreed;
import ru.clevertec.domain.Dog;
import ru.clevertec.service.DogService;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DogController.class)
class DogControllerTest {

    @MockBean
    private DogService dogService;

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public  void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    @Test
    void shouldFindAll() throws Exception {
        //given
        when(dogService.getDogs())
                .thenReturn(List.of(new Dog()));
        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/dogs"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindDogById() throws Exception {
        //given
        Dog dog = TestData.newDog();
        UUID dogId = dog.getId();
        when(dogService.getDogById(dogId))
                .thenReturn(dog);
        //when,then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/dogs/{dogId}", dogId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    void shouldFindDogsByBreed() throws Exception {
        //given
        Dog dog = TestData.newDog();
        DogBreed breed = dog.getDogBreed();
        when(dogService.getDogsByBreed(breed))
                .thenReturn(List.of(dog));
        //when,then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/dogs/breed/{breed}", breed))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dogBreed").value(breed.toString()));
    }
    @Test
    void shouldCreateDog() throws Exception {
        //given
        Dog dog = TestData.newDog();
        //when, then
        when(dogService.createDog(any(Dog.class))).thenReturn(dog);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/dogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dog)))
                .andExpect(status().isOk());
    }
    @Test
    void shouldUpdateDog() throws Exception {
        //given
        Dog updatedDog = TestData.newDog();
        UUID dogId = updatedDog.getId();
        //when, then
        when(dogService.updateDog(eq(dogId), any(Dog.class))).thenReturn(updatedDog);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/dogs/{dogId}", dogId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDog)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteDog() throws Exception {
        //given
        UUID dogId = UUID.randomUUID();
        //when, then
        doNothing().when(dogService).deleteDog(dogId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/dogs/{dogId}", dogId))
                .andExpect(status().isNoContent());
    }
}