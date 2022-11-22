package maxima.europe.recipes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import maxima.europe.recipes.model.*;
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
class RecipesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @Commit
    void save_shouldSaveRecipesModelToDatabase() throws Exception {
        RecipesModel model = new RecipesModel(){{
            setFood(new FoodModel(){{setId(4L);}});
            setIngredient(new IngredientModel(){{setId(15L);setUnitType(new UnitTypeModel(){{setId(2L);}});}});
            setCount(2);
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(22))
                .andExpect(jsonPath("$.food.id").value(4))
        ;
    }
    @Test
    @Order(2)
    @Commit
    void update_shouldUpdateRecipesModelToDatabase() throws Exception {
        RecipesModel model = new RecipesModel() {{
            setId(21L);
            setCount(3);
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(patch("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(3))
        ;
    }

    @Test
    @Order(3)
    void deleteById_shouldDeleteByIdFromDatabase() throws Exception {
        mockMvc.perform(delete("/api/v1/recipes/{id}",22))
                .andExpect(status().isNoContent());
    }

    @Test
    void save_shouldThrowBadRequestError() throws Exception {
        mockMvc.perform(post("/api/v1/recipes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void findById_shouldReturnRecipesModel() throws Exception {
        mockMvc.perform(get("/api/v1/recipes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.food.name").value("Pizza"));
    }

    @Test
    void findById_shouldReturn404NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/recipes/{id}", 44L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll_shouldReturnPageableRecipesWhenFoodGroupISVegetarian() throws Exception {
        String filter = objectMapper.writeValueAsString(new RecipesModel(){{setFood(new FoodModel(){{setFoodGroup(new FoodGroupModel(){{setName("Vegetarian");}});}});}});

        mockMvc.perform(get("/api/v1/recipes").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(4))
                .andExpect((jsonPath("$.content", Matchers.hasSize(4))))
        ;
    }
    @Test
    void findAll_shouldReturnPageableRecipesWhenFoodServingsIS1AndIngredientContainsTomato() throws Exception {
        String filter = objectMapper.writeValueAsString(new RecipesModel(){{setIngredient(new IngredientModel(){{setName("Tomato");}});setFood(new FoodModel(){{setServings(1);}});}});

        mockMvc.perform(get("/api/v1/recipes").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect((jsonPath("$.content", Matchers.hasSize(3))))
        ;
    }
    @Test
    void findAll_shouldReturnPageableRecipesWhenIngredientNameISCheese() throws Exception {
        String filter = objectMapper.writeValueAsString(new RecipesModel(){{setIngredient(new IngredientModel(){{setName("Cheese");}});}});

        mockMvc.perform(get("/api/v1/recipes").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect((jsonPath("$.content", Matchers.hasSize(1))))
        ;
    }

    @Test
    void search_shouldReturnPageableRecipesWhenIngredientNameISNotSalmonAndFoodNeedOven() throws Exception {

        mockMvc.perform(get("/api/v1/recipes")
                        .param("ingredient.name","ne(salmon)")
                        .param("food.needOven","true")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(21))
                .andExpect((jsonPath("$.content", Matchers.hasSize(10))))
        ;
    }

    @Test
    void countAll_shouldReturnTotalNumberOfRecipes() throws Exception {
        String filter = objectMapper.writeValueAsString(new RecipesModel(){{setFood(new FoodModel(){{setName("Pizza");}});}});

        mockMvc.perform(get("/api/v1/recipes/count").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}
