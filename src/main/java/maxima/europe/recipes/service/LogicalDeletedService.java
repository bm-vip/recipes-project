package maxima.europe.recipes.service;

import maxima.europe.recipes.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import maxima.europe.recipes.entity.LogicalDeleted;

import java.io.Serializable;

public interface LogicalDeletedService<ID extends Serializable> {
    <E extends LogicalDeleted> JpaRepository<E, ID> getRepository();

    default void logicalDeleteById(ID id) {
        var entity = getRepository().findById(id).orElseThrow(() -> new NotFoundException("id: " + id));
        entity.setDeleted(true);
        getRepository().save(entity);
    }
}