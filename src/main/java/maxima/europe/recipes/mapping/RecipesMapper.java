package maxima.europe.recipes.mapping;

import maxima.europe.recipes.entity.RecipesEntity;
import maxima.europe.recipes.model.RecipesModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {FoodMapper.class, IngredientMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecipesMapper extends BaseMapper<RecipesModel, RecipesEntity> {

}
