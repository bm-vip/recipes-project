package maxima.europe.recipes.service;

import com.querydsl.core.types.Predicate;
import maxima.europe.recipes.model.RecipesModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipesService extends BaseService<RecipesModel, Long> , LogicalDeletedService<Long> {
    Page<RecipesModel> search(Predicate predicate, Pageable pageable);
}
