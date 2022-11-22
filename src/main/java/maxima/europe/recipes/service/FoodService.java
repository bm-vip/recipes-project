package maxima.europe.recipes.service;

import maxima.europe.recipes.model.FoodModel;

public interface FoodService extends BaseService<FoodModel, Long> , LogicalDeletedService<Long> {
}
