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
@Table(name = "tbl_unit_type")
@Where(clause = "deleted=false")
public class UnitTypeEntity extends BaseEntity<Long> implements LogicalDeleted {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_unit_type", sequenceName = "seq_unit_type", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_unit_type")
    private Long id;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String name;

    private boolean deleted;
    @Override
    public String getSelectTitle() {
        return name;
    }
}
