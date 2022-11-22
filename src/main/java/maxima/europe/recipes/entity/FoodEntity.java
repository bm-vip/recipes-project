package maxima.europe.recipes.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
* Created by Behrooz.Mohamadi on 20/11/2022.
 */
@Data
@Entity
@Table(name = "tbl_food")
public class FoodEntity extends BaseEntity<Long> implements LogicalDeleted {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_food", sequenceName = "seq_food", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_food")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private boolean needOven;

    @NotNull
    private int servings;

    @ManyToOne
    @JoinColumn(name = "food_group_id", nullable = false)
    private FoodGroupEntity foodGroup;

    private boolean deleted;
    @Override
    public String getSelectTitle() {
        return name;
    }
}
