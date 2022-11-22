package maxima.europe.recipes.repository;

import maxima.europe.recipes.entity.IngredientEntity;
import maxima.europe.recipes.entity.UnitTypeEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository repository;

    @Test
    @Order(1)
    @Commit
    public void save_shouldSaveIngredientEntityToDatabase() {
        IngredientEntity entity = new IngredientEntity();
        entity.setName("test ingredient");
        entity.setUnitType(new UnitTypeEntity(){{setId(2L);}});
        repository.save(entity);

        Assertions.assertThat(entity.getId()).isEqualTo(20L);
    }

    @Test
    @Order(2)
    @Commit
    public void deleteById_shouldDeleteByIdFromDatabase(){
        repository.deleteById(20L);
        Optional<IngredientEntity> optional = repository.findById(20L);

        Assertions.assertThat(optional).isEmpty();
    }

    @Test
    void save_shouldExceptionThrown_thenAssertionSucceeds() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(new IngredientEntity()));

        Assertions.assertThat(exception.getMessage()).contains("must not be null");
    }
    @Test
    public void update_shouldUpdateIngredientEntityToDatabase(){
        IngredientEntity entity = repository.findById(1L).get();
        entity.setName("new Ingredient test");
        IngredientEntity entityUpdated = repository.save(entity);

        Assertions.assertThat(entityUpdated.getName()).isEqualTo("new Ingredient test");
    }

    @Test
    public void findById_shouldReturnIngredientEntity() {
        Optional<IngredientEntity> optional = repository.findById(1L);

        Assertions.assertThat(optional).isPresent();
        Assertions.assertThat(optional.get().getName()).isEqualTo("Cheese");
    }

    @Test
    public void findAll_shouldReturnPageableIngredientEntities() {
        Page<IngredientEntity> page = repository.findAll(Pageable.ofSize(10));

        Assertions.assertThat(page).isNotEmpty().size().isEqualTo(10);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(19);
    }
}
