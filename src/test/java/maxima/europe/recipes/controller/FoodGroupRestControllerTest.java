package maxima.europe.recipes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import maxima.europe.recipes.model.FoodGroupModel;
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
class FoodGroupRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @Commit
    void save_shouldSaveFoodGroupModelToDatabase() throws Exception {
        FoodGroupModel model = new FoodGroupModel() {{
            setName("test food group");
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(post("/api/v1/food-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.name").value("test food group"))
        ;
    }

    @Test
    @Order(2)
    @Commit
    void update_shouldUpdateFoodGroupModelToDatabase() throws Exception {
        FoodGroupModel model = new FoodGroupModel() {{
            setId(7L);
            setName("update food group");
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(patch("/api/v1/food-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("update food group"))
        ;
    }

    @Test
    @Order(3)
    void deleteById_shouldDeleteByIdFromDatabase() throws Exception {
        mockMvc.perform(delete("/api/v1/food-group/{id}",7L))
                .andExpect(status().isNoContent());
    }

    @Test
    void save_shouldThrowBadRequestError() throws Exception {
        mockMvc.perform(post("/api/v1/food-group").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void findById_shouldReturnFoodGroupModel() throws Exception {
        mockMvc.perform(get("/api/v1/food-group/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fast food"));
    }

    @Test
    void findById_shouldThrowNotFoundError() throws Exception {
        mockMvc.perform(get("/api/v1/food-group/{id}", 44L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll_shouldReturnPageableFoodGroupModels() throws Exception {
        String filter = objectMapper.writeValueAsString(new FoodGroupModel(){{setName("Fast");}});

        mockMvc.perform(get("/api/v1/food-group").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)));
    }

    @Test
    void countAll_shouldReturnTotalNumberOfFoodGroups() throws Exception {
        String filter = objectMapper.writeValueAsString(new FoodGroupModel(){{setName("Fast");}});

        mockMvc.perform(get("/api/v1/food-group/count").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
