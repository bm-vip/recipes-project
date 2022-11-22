package maxima.europe.recipes.repository;

import maxima.europe.recipes.entity.FoodGroupEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FoodGroupRepositoryTest {

    @Autowired
    private FoodGroupRepository repository;

    @Test
    @Order(1)
    @Commit
    public void save_shouldSaveFoodGroupEntityToDatabase() {
        FoodGroupEntity entity = new FoodGroupEntity();
        entity.setName("test foodGroup");
        repository.save(entity);

        Assertions.assertThat(entity.getId()).isEqualTo(7);
    }

    @Test
    @Order(2)
    @Commit
    public void deleteById_shouldDeleteByIdFromDatabase() {
        repository.deleteById(7L);
        Optional<FoodGroupEntity> childOptional = repository.findById(7L);

        Assertions.assertThat(childOptional).isEmpty();
    }

    @Test
    void save_shouldExceptionThrown_thenAssertionSucceeds() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(new FoodGroupEntity()));

        Assertions.assertThat(exception.getMessage()).contains("must not be null");
    }

    @Test
    public void update_shouldUpdateFoodGroupEntityToDatabase() {
        FoodGroupEntity foodGroupEntity = repository.findById(3L).get();
        foodGroupEntity.setName("new foodGroup test");
        FoodGroupEntity foodGroupUpdated = repository.save(foodGroupEntity);

        Assertions.assertThat(foodGroupUpdated.getName()).isEqualTo("new foodGroup test");
    }

    @Test
    public void findById_shouldReturnFoodGroupEntity() {
        Optional<FoodGroupEntity> optional = repository.findById(1L);

        Assertions.assertThat(optional).isPresent();
        Assertions.assertThat(optional.get().getName()).isEqualTo("Fast food");
    }

    @Test
    public void findAll_shouldReturnPageableFoodGroupEntities() {
        Page<FoodGroupEntity> page = repository.findAll(PageRequest.ofSize(10));

        Assertions.assertThat(page).isNotEmpty().size().isEqualTo(6);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(6);
    }

    @Test
    public void countAll_shouldReturnTotalNumberOfCompanies() {
        long count = repository.count();

        Assertions.assertThat(count).isEqualTo(6);
    }
}
