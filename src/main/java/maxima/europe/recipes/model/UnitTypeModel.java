package maxima.europe.recipes.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* Created by Behrooz.Mohamadi on 20/11/2022.
 */
@Data
public class UnitTypeModel extends BaseModel<Long> {

    @NotNull
    @NotBlank
    private String name;
}
