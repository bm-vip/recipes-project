package maxima.europe.recipes.repository;

import maxima.europe.recipes.entity.FoodEntity;
import maxima.europe.recipes.entity.IngredientEntity;
import maxima.europe.recipes.entity.RecipesEntity;
import maxima.europe.recipes.entity.UnitTypeEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecipesRepositoryTest {

    @Autowired
    private RecipesRepository repository;

    @Test
    @Order(1)
    @Commit
    public void save_shouldSaveRecipesEntityToDatabase() {
        RecipesEntity entity = new RecipesEntity();
        entity.setIngredient(new IngredientEntity(){{setId(15L);}});
        entity.setFood(new FoodEntity(){{setId(4L);}});
        entity.setCount(2);
        repository.save(entity);

        Assertions.assertThat(entity.getId()).isEqualTo(22L);
    }

    @Test
    @Order(2)
    @Commit
    public void deleteById_shouldDeleteByIdFromDatabase(){
        repository.deleteById(22L);
        Optional<RecipesEntity> optional = repository.findById(22L);

        Assertions.assertThat(optional).isEmpty();
    }

    @Test
    void save_shouldExceptionThrown_thenAssertionSucceeds() {
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(new RecipesEntity()));

        Assertions.assertThat(exception.getMessage()).contains("could not execute statement");
    }
    @Test
    public void update_shouldUpdateRecipesEntityToDatabase(){
        RecipesEntity entity = repository.findById(1L).get();
        entity.setCount(3);
        RecipesEntity entityUpdated = repository.save(entity);

        Assertions.assertThat(entityUpdated.getCount()).isEqualTo(3);
    }

    @Test
    public void findById_shouldReturnRecipesEntity() {
        Optional<RecipesEntity> optional = repository.findById(1L);

        Assertions.assertThat(optional).isPresent();
        Assertions.assertThat(optional.get().getIngredient().getName()).isEqualTo("Cheese");
    }

    @Test
    public void findAll_shouldReturnPageableRecipesEntities() {
        Page<RecipesEntity> page = repository.findAll(Pageable.ofSize(10));

        Assertions.assertThat(page).isNotEmpty().size().isEqualTo(10);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(21);
    }
}
