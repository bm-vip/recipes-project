package maxima.europe.recipes.repository;

import maxima.europe.recipes.entity.FoodGroupEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodGroupRepository extends BaseRepository<FoodGroupEntity, Long> {
}