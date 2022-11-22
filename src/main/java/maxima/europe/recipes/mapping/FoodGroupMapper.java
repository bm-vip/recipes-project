package maxima.europe.recipes.mapping;

import maxima.europe.recipes.entity.FoodGroupEntity;
import org.mapstruct.*;
import maxima.europe.recipes.model.FoodGroupModel;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FoodGroupMapper extends BaseMapper<FoodGroupModel, FoodGroupEntity> {
}
