package maxima.europe.recipes.service;

import com.querydsl.core.BooleanBuilder;
import maxima.europe.recipes.entity.*;
import maxima.europe.recipes.model.FoodGroupModel;
import maxima.europe.recipes.model.FoodModel;
import maxima.europe.recipes.model.IngredientModel;
import maxima.europe.recipes.model.RecipesModel;
import maxima.europe.recipes.repository.RecipesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RecipesServiceTest {

    @MockBean
    private RecipesRepository repository;
    @SpyBean
    private RecipesService service;

    @Test
    public void search_shouldReturnPageableRecipesWhenIngredientNameISNotSalmonAndFoodNeedOven() {
        //Given 
        List<RecipesEntity> entities = new ArrayList<>();
        entities.add(new RecipesEntity(){{setId(1L);setFood(new FoodEntity(){{setId(1L);setName("Pizza");}});setIngredient(new IngredientEntity(){{setId(1L);setName("Cheese");setUnitType(new UnitTypeEntity());}});}});
        entities.add(new RecipesEntity(){{setId(2L);setFood(new FoodEntity(){{setId(1L);setName("Pizza");}});setIngredient(new IngredientEntity(){{setId(2L);setName("Tomato sauce");setUnitType(new UnitTypeEntity());}});}});
        entities.add(new RecipesEntity(){{setId(3L);setFood(new FoodEntity(){{setId(1L);setName("Pizza");}});setIngredient(new IngredientEntity(){{setId(3L);setName("Semolina flour");setUnitType(new UnitTypeEntity());}});}});
        entities.add(new RecipesEntity(){{setId(4L);setFood(new FoodEntity(){{setId(1L);setName("Pizza");}});setIngredient(new IngredientEntity(){{setId(7L);setName("Mushroom");setUnitType(new UnitTypeEntity());}});}});
        entities.add(new RecipesEntity(){{setId(5L);setFood(new FoodEntity(){{setId(1L);setName("Pizza");}});setIngredient(new IngredientEntity(){{setId(10L);setName("Olive");setUnitType(new UnitTypeEntity());}});}});
        BooleanBuilder builder = new BooleanBuilder();
        QRecipesEntity path = QRecipesEntity.recipesEntity;
        builder.and(path.ingredient.name.ne("salmon"));
        builder.and(path.food.needOven.eq(true));
        //When
        when(repository.findAll(builder, PageRequest.of(0, 10))).thenReturn(new PageImpl<>(entities));

        //Then
        Page<RecipesModel> pageModels = service.search(builder, PageRequest.of(0, 10));
        assertThat(pageModels).isNotNull().isNotEmpty().size().isEqualTo(5);
        assertThat(pageModels.map(m->m.getIngredient().getName())).contains("Cheese","Tomato sauce","Semolina flour","Mushroom","Olive");
    }

    @Test
    public void findAll_shouldReturnPageableRecipesWhenFoodGroupISVegetarian() {
        //Given 
        List<RecipesEntity> entities = new ArrayList<>();
        entities.add(new RecipesEntity(){{setId(1L);setFood(new FoodEntity(){{setId(1L);setName("Pizza");}});setIngredient(new IngredientEntity(){{setId(1L);setName("Cheese");setUnitType(new UnitTypeEntity());}});}});
        entities.add(new RecipesEntity(){{setId(2L);setFood(new FoodEntity(){{setId(1L);setName("Pizza");}});setIngredient(new IngredientEntity(){{setId(2L);setName("Tomato sauce");setUnitType(new UnitTypeEntity());}});}});
        entities.add(new RecipesEntity(){{setId(3L);setFood(new FoodEntity(){{setId(1L);setName("Pizza");}});setIngredient(new IngredientEntity(){{setId(3L);setName("Semolina flour");setUnitType(new UnitTypeEntity());}});}});
        entities.add(new RecipesEntity(){{setId(4L);setFood(new FoodEntity(){{setId(1L);setName("Pizza");}});setIngredient(new IngredientEntity(){{setId(7L);setName("Mushroom");setUnitType(new UnitTypeEntity());}});}});
        entities.add(new RecipesEntity(){{setId(5L);setFood(new FoodEntity(){{setId(1L);setName("Pizza");}});setIngredient(new IngredientEntity(){{setId(10L);setName("Olive");setUnitType(new UnitTypeEntity());}});}});
        BooleanBuilder builder = new BooleanBuilder();
        QRecipesEntity path = QRecipesEntity.recipesEntity;
        builder.and(path.food.foodGroup.name.contains("Vegetarian"));
        //When
        when(repository.findAll(builder, PageRequest.of(0, 10))).thenReturn(new PageImpl<>(entities));

        //Then
        RecipesModel filter = new RecipesModel(){{setFood(new FoodModel(){{setFoodGroup(new FoodGroupModel(){{setName("Vegetarian");}});}});}};
        Page<RecipesModel> pageModels = service.findAll(filter, PageRequest.of(0, 10));
        assertThat(pageModels).isNotNull().isNotEmpty().size().isEqualTo(5);
        assertThat(pageModels.map(m->m.getIngredient().getName())).contains("Cheese","Tomato sauce","Semolina flour","Mushroom","Olive");
    }

    @Test
    public void findAll_shouldReturnPageableRecipesWhenFoodServingsIS1AndIngredientContainsTomato() {
        //Given
        List<RecipesEntity> entities = new ArrayList<>();
        entities.add(new RecipesEntity(){{setId(1L);setFood(new FoodEntity(){{setId(1L);setName("Pizza");}});setIngredient(new IngredientEntity(){{setId(2L);setName("Tomato sauce");setUnitType(new UnitTypeEntity());}});}});
        entities.add(new RecipesEntity(){{setId(2L);setFood(new FoodEntity(){{setId(1L);setName("Pasta");}});setIngredient(new IngredientEntity(){{setId(3L);setName("Tomato sauce");setUnitType(new UnitTypeEntity());}});}});
        entities.add(new RecipesEntity(){{setId(3L);setFood(new FoodEntity(){{setId(1L);setName("Omelette");}});setIngredient(new IngredientEntity(){{setId(7L);setName("Tomato sauce");setUnitType(new UnitTypeEntity());}});}});
        BooleanBuilder builder = new BooleanBuilder();
        QRecipesEntity path = QRecipesEntity.recipesEntity;
        builder.and(path.food.servings.eq(1));
        builder.and(path.ingredient.name.contains("Tomato"));
        //When
        when(repository.findAll(builder, PageRequest.of(0, 10))).thenReturn(new PageImpl<>(entities));

        //Then
        RecipesModel filter = new RecipesModel(){{setFood(new FoodModel(){{setServings(1);}});setIngredient(new IngredientModel(){{setName("Tomato");}});}};
        Page<RecipesModel> pageModels = service.findAll(filter, PageRequest.of(0, 10));
        assertThat(pageModels).isNotNull().isNotEmpty().size().isEqualTo(3);
        assertThat(pageModels.map(m->m.getFood().getName())).contains("Pizza","Pasta","Omelette");
    }

    @Test
    public void findAll_shouldReturnPageableRecipesWhenIngredientNameISCheese() {
        //Given 
        List<RecipesEntity> entities = new ArrayList<>();
        entities.add(new RecipesEntity(){{setId(1L);setFood(new FoodEntity(){{setId(1L);setName("Pizza");}});setIngredient(new IngredientEntity(){{setId(1L);setName("Cheese");setUnitType(new UnitTypeEntity());}});}});
        BooleanBuilder builder = new BooleanBuilder();
        QRecipesEntity path = QRecipesEntity.recipesEntity;
        builder.and(path.ingredient.name.contains("Cheese"));
        //When
        when(repository.findAll(builder, PageRequest.of(0, 10))).thenReturn(new PageImpl<>(entities));

        //Then 
        RecipesModel filter = new RecipesModel(){{setIngredient(new IngredientModel(){{setName("Cheese");}});}};
        Page<RecipesModel> pageModels = service.findAll(filter, PageRequest.of(0, 10));
        assertThat(pageModels).isNotNull().isNotEmpty().size().isEqualTo(1);
        assertThat(pageModels.map(m->m.getIngredient().getName())).contains("Cheese");
    }
}
