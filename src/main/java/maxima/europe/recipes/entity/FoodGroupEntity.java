package maxima.europe.recipes.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
* Created by Behrooz.Mohamadi on 20/11/2022.
 */
@Data
@Entity
@Table(name = "tbl_food_group")
public class FoodGroupEntity extends BaseEntity<Long> implements LogicalDeleted{

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_food_group", sequenceName = "seq_food_group", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_food_group")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    private boolean deleted;
    @Override
    public String getSelectTitle() {
        return name;
    }
}
