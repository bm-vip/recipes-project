package maxima.europe.recipes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import maxima.europe.recipes.model.FoodGroupModel;
import maxima.europe.recipes.model.FoodModel;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FoodRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @Commit
    void save_shouldSaveFoodModelToDatabase() throws Exception {
        FoodModel model = new FoodModel() {{
            setName("Doner kebab");
            setFoodGroup(new FoodGroupModel(){{setId(5L);}});
            setNeedOven(true);
            setServings(2);
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(post("/api/v1/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.name").value("Doner kebab"))
        ;
    }
    @Test
    @Order(2)
    @Commit
    void update_shouldUpdateFoodModelToDatabase() throws Exception {
        FoodModel model = new FoodModel() {{
            setId(7L);
            setName("update Doner kebab");
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(patch("/api/v1/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("update Doner kebab"))
        ;
    }

    @Test
    @Order(3)
    void deleteById_shouldDeleteByIdFromDatabase() throws Exception {
        mockMvc.perform(delete("/api/v1/food/{id}",7))
                .andExpect(status().isNoContent());
    }

    @Test
    void save_shouldThrowBadRequestError() throws Exception {
        mockMvc.perform(post("/api/v1/food").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void findById_shouldReturnFoodModel() throws Exception {
        mockMvc.perform(get("/api/v1/food/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza"));
    }

    @Test
    void findById_shouldReturn404NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/food/{id}", 44L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll_shouldReturnPageableFoodModels() throws Exception {
        String filter = objectMapper.writeValueAsString(new FoodModel(){{setName("p");}});

        mockMvc.perform(get("/api/v1/food").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect((jsonPath("$.content", Matchers.hasSize(1))))
        ;
    }

    @Test
    void countAll_shouldReturnTotalNumberOfFood() throws Exception {
        String filter = objectMapper.writeValueAsString(new FoodModel(){{setName("p");}});

        mockMvc.perform(get("/api/v1/food/count").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
