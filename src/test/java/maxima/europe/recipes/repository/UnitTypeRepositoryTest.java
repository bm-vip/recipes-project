package maxima.europe.recipes.repository;

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
public class UnitTypeRepositoryTest {

    @Autowired
    private UnitTypeRepository repository;

    @Test
    @Order(1)
    @Commit
    public void save_shouldSaveUnitTypeEntityToDatabase() {
        UnitTypeEntity entity = new UnitTypeEntity();
        entity.setName("pounds");
        repository.save(entity);

        Assertions.assertThat(entity.getId()).isEqualTo(8L);
    }

    @Test
    @Order(2)
    @Commit
    public void deleteById_shouldDeleteByIdFromDatabase(){
        repository.deleteById(8L);
        Optional<UnitTypeEntity> optional = repository.findById(8L);

        Assertions.assertThat(optional).isEmpty();
    }

    @Test
    void save_shouldExceptionThrown_thenAssertionSucceeds() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(new UnitTypeEntity()));

        Assertions.assertThat(exception.getMessage()).contains("must not be null");
    }
    @Test
    public void update_shouldUpdateUnitTypeEntityToDatabase(){
        UnitTypeEntity entity = repository.findById(1L).get();
        entity.setName("new UnitType test");
        UnitTypeEntity entityUpdated = repository.save(entity);

        Assertions.assertThat(entityUpdated.getName()).isEqualTo("new UnitType test");
    }

    @Test
    public void findById_shouldReturnUnitTypeEntity() {
        Optional<UnitTypeEntity> optional = repository.findById(1L);

        Assertions.assertThat(optional).isPresent();
        Assertions.assertThat(optional.get().getName()).isEqualTo("piece");
    }

    @Test
    public void findAll_shouldReturnPageableUnitTypeEntities() {
        Page<UnitTypeEntity> page = repository.findAll(Pageable.ofSize(10));

        Assertions.assertThat(page).isNotEmpty().size().isEqualTo(7);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(7);
    }
}
