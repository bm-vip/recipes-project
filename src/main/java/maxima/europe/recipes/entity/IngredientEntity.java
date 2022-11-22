package maxima.europe.recipes.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* Created by Behrooz.Mohamadi on 20/11/2022.
 */
@Data
@Entity
@Table(name = "tbl_ingredient")
@Where(clause = "deleted=false")
public class IngredientEntity extends BaseEntity<Long> implements LogicalDeleted {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_ingredient", sequenceName = "seq_ingredient", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ingredient")
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "unit_type_id", nullable = false)
    private UnitTypeEntity unitType;

    private boolean deleted;

    @Override
    public String getSelectTitle() {
        return name;
    }
}
