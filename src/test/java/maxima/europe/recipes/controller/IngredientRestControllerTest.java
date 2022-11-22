package maxima.europe.recipes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import maxima.europe.recipes.model.IngredientModel;
import maxima.europe.recipes.model.UnitTypeModel;
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
class IngredientRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @Commit
    void save_shouldSaveIngredientModelToDatabase() throws Exception {
        IngredientModel model = new IngredientModel() {{
            setName("Potato");
            setUnitType(new UnitTypeModel(){{setId(2L);}});
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(post("/api/v1/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(20))
                .andExpect(jsonPath("$.name").value("Potato"))
        ;
    }
    @Test
    @Order(2)
    @Commit
    void update_shouldUpdateIngredientModelToDatabase() throws Exception {
        IngredientModel model = new IngredientModel() {{
            setId(7L);
            setName("update Potato");
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(patch("/api/v1/ingredient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("update Potato"))
        ;
    }

    @Test
    @Order(3)
    void deleteById_shouldDeleteByIdFromDatabase() throws Exception {
        mockMvc.perform(delete("/api/v1/ingredient/{id}",20))
                .andExpect(status().isNoContent());
    }

    @Test
    void save_shouldThrowBadRequestError() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void findById_shouldReturnIngredientModel() throws Exception {
        mockMvc.perform(get("/api/v1/ingredient/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cheese"));
    }

    @Test
    void findById_shouldReturn404NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/ingredient/{id}", 44L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll_shouldReturnPageableIngredientModels() throws Exception {
        String filter = objectMapper.writeValueAsString(new IngredientModel(){{setName("p");}});

        mockMvc.perform(get("/api/v1/ingredient").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect((jsonPath("$.content", Matchers.hasSize(3))))
        ;
    }

    @Test
    void countAll_shouldReturnTotalNumberOfIngredient() throws Exception {
        String filter = objectMapper.writeValueAsString(new IngredientModel(){{setName("p");}});

        mockMvc.perform(get("/api/v1/ingredient/count").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }
}
