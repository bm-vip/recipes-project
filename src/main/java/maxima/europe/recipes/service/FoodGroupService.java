package maxima.europe.recipes.service;

import maxima.europe.recipes.model.FoodGroupModel;

public interface FoodGroupService extends BaseService<FoodGroupModel, Long> , LogicalDeletedService<Long> {
}
