package maxima.europe.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import maxima.europe.recipes.validation.Update;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class BaseModel<ID extends Serializable> implements Serializable {
    @NotNull(groups = Update.class)
    private ID id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Date modifiedDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Date createdDate;;
    protected int version;
    @JsonIgnore
    private String selectTitle;
}
