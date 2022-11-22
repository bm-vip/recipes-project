package maxima.europe.recipes.repository;

import maxima.europe.recipes.entity.IngredientEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends BaseRepository<IngredientEntity, Long> {
}