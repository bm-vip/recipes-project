package maxima.europe.recipes.mapping;

import maxima.europe.recipes.entity.UnitTypeEntity;
import maxima.europe.recipes.model.UnitTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {FoodGroupMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UnitTypeMapper extends BaseMapper<UnitTypeModel, UnitTypeEntity> {

}
