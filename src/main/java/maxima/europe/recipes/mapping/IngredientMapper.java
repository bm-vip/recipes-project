package maxima.europe.recipes.mapping;

import org.mapstruct.*;
import maxima.europe.recipes.entity.IngredientEntity;
import maxima.europe.recipes.model.IngredientModel;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IngredientMapper extends BaseMapper<IngredientModel, IngredientEntity> {

}
