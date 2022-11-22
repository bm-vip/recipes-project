package maxima.europe.recipes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
class UnitTypeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @Commit
    void save_shouldSaveUnitTypeModelToDatabase() throws Exception {
        UnitTypeModel model = new UnitTypeModel() {{
            setName("pound");
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(post("/api/v1/unit-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.name").value("pound"))
        ;
    }
    @Test
    @Order(2)
    @Commit
    void update_shouldUpdateUnitTypeModelToDatabase() throws Exception {
        UnitTypeModel model = new UnitTypeModel() {{
            setId(8L);
            setName("update pound");
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(patch("/api/v1/unit-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("update pound"))
        ;
    }

    @Test
    @Order(3)
    void deleteById_shouldDeleteByIdFromDatabase() throws Exception {
        mockMvc.perform(delete("/api/v1/unit-type/{id}",8))
                .andExpect(status().isNoContent());
    }

    @Test
    void save_shouldThrowBadRequestError() throws Exception {
        mockMvc.perform(post("/api/v1/unit-type").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void findById_shouldReturnUnitTypeModel() throws Exception {
        mockMvc.perform(get("/api/v1/unit-type/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("piece"));
    }

    @Test
    void findById_shouldReturn404NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/unit-type/{id}", 44L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll_shouldReturnPageableUnitTypeModels() throws Exception {
        String filter = objectMapper.writeValueAsString(new UnitTypeModel(){{setName("p");}});

        mockMvc.perform(get("/api/v1/unit-type").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(4))
                .andExpect((jsonPath("$.content", Matchers.hasSize(4))))
        ;
    }

    @Test
    void countAll_shouldReturnTotalNumberOfUnitType() throws Exception {
        String filter = objectMapper.writeValueAsString(new UnitTypeModel(){{setName("p");}});

        mockMvc.perform(get("/api/v1/unit-type/count").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(content().string("4"));
    }
}
