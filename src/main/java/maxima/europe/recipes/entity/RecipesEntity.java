package maxima.europe.recipes.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Behrooz.Mohamadi on 20/11/2022.
 */
@Data
@Entity
@Table(name = "tbl_recipes",uniqueConstraints = { @UniqueConstraint(columnNames = { "food_id", "ingredient_id","count" }) })
public class RecipesEntity extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_recipes", sequenceName = "seq_recipes", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_recipes")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private FoodEntity food;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private IngredientEntity ingredient;

    @NotNull
    private int count;

    @Override
    public String getSelectTitle() {
        return String.format("%s: %d-%s %s", food.getName(), count, ingredient.getUnitType().getName(), ingredient.getName());
    }
}
