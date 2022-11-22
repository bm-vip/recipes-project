package maxima.europe.recipes.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import maxima.europe.recipes.model.FoodModel;
import maxima.europe.recipes.service.FoodService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import maxima.europe.recipes.controller.LogicalDeletedRestController;
import maxima.europe.recipes.service.LogicalDeletedService;

/**
 * Created by Behrooz.Mohamadi on 20/11/2022.
 */
@RestController
@Tag(name = "Food Rest Service v1")
@RequestMapping(value = "/api/v1/food")
@Validated
public class FoodRestController extends BaseRestControllerImpl<FoodModel, Long> implements LogicalDeletedRestController<Long> {

    private FoodService service;

    public FoodRestController(FoodService service) {
        super(service, FoodModel.class);
        this.service = service;
    }

    @Override
    public LogicalDeletedService<Long> getService() {
        return service;
    }
}
