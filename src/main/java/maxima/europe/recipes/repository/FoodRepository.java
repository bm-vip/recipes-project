package maxima.europe.recipes.repository;

import maxima.europe.recipes.entity.FoodEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends BaseRepository<FoodEntity, Long> {
}