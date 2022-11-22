package maxima.europe.recipes.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import maxima.europe.recipes.controller.LogicalDeletedRestController;
import maxima.europe.recipes.model.IngredientModel;
import maxima.europe.recipes.service.IngredientService;
import maxima.europe.recipes.service.LogicalDeletedService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Behrooz.Mohamadi on 20/11/2022.
 */
@RestController
@Tag(name = "Ingredient Rest Service v1")
@RequestMapping(value = "/api/v1/ingredient")
@Validated
public class IngredientRestController extends BaseRestControllerImpl<IngredientModel, Long> implements LogicalDeletedRestController<Long> {

    private IngredientService service;

    public IngredientRestController(IngredientService service) {
        super(service, IngredientModel.class);
        this.service = service;
    }

    @Override
    public LogicalDeletedService<Long> getService() {
        return service;
    }
}
