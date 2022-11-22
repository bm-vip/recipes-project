package maxima.europe.recipes.service;

import maxima.europe.recipes.model.IngredientModel;

public interface IngredientService extends BaseService<IngredientModel, Long> , LogicalDeletedService<Long> {
}
