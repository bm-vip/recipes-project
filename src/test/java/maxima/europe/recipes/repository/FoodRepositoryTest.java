package maxima.europe.recipes.repository;

import maxima.europe.recipes.entity.FoodEntity;
import maxima.europe.recipes.entity.FoodGroupEntity;
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
public class FoodRepositoryTest {

    @Autowired
    private FoodRepository repository;

    @Test
    @Order(1)
    @Commit
    public void save_shouldSaveFoodEntityToDatabase() {
        FoodEntity entity = new FoodEntity();
        entity.setName("test food");
        entity.setFoodGroup(new FoodGroupEntity(){{setId(1L);}});
        entity.setNeedOven(false);
        entity.setServings(1);
        repository.save(entity);

        Assertions.assertThat(entity.getId()).isEqualTo(7L);
    }

    @Test
    @Order(2)
    @Commit
    public void deleteById_shouldDeleteByIdFromDatabase(){
        repository.deleteById(7L);
        Optional<FoodEntity> optional = repository.findById(7L);

        Assertions.assertThat(optional).isEmpty();
    }

    @Test
    void save_shouldExceptionThrown_thenAssertionSucceeds() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(new FoodEntity()));

        Assertions.assertThat(exception.getMessage()).contains("must not be null");
    }
    @Test
    public void update_shouldUpdateFoodEntityToDatabase(){
        FoodEntity entity = repository.findById(1L).get();
        entity.setName("new Food test");
        FoodEntity entityUpdated = repository.save(entity);

        Assertions.assertThat(entityUpdated.getName()).isEqualTo("new Food test");
    }

    @Test
    public void findById_shouldReturnFoodEntity() {
        Optional<FoodEntity> optional = repository.findById(1L);

        Assertions.assertThat(optional).isPresent();
        Assertions.assertThat(optional.get().getName()).isEqualTo("Pizza");
    }

    @Test
    public void findAll_shouldReturnPageableFoodEntities() {
        Page<FoodEntity> page = repository.findAll(Pageable.ofSize(10));

        Assertions.assertThat(page).isNotEmpty().size().isEqualTo(6);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(6);
    }
}
