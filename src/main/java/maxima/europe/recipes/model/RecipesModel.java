package maxima.europe.recipes.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* Created by Behrooz.Mohamadi on 20/11/2022.
 */
@Data
public class RecipesModel extends BaseModel<Long> {
    @NotNull
    private FoodModel food;
    @NotNull
    private IngredientModel ingredient;

    @NotNull
    private Integer count;
}
