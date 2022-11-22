package maxima.europe.recipes.service;

import maxima.europe.recipes.model.BaseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

public interface BaseService<M extends BaseModel<ID>, ID extends Serializable> {
    Page<M> findAll(M filter, Pageable pageable);
    Long countAll(M filter);
    M findById(ID id);
    M save(M model);
    void deleteById(ID id);
}