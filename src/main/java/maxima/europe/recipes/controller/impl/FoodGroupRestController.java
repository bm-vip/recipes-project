package maxima.europe.recipes.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import maxima.europe.recipes.controller.LogicalDeletedRestController;
import maxima.europe.recipes.model.FoodGroupModel;
import maxima.europe.recipes.service.FoodGroupService;
import maxima.europe.recipes.service.LogicalDeletedService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Behrooz.Mohamadi on 20/11/2022.
 */
@RestController
@Tag(name = "Food Group Rest Service v1")
@RequestMapping(value = "/api/v1/food-group")
public class FoodGroupRestController extends BaseRestControllerImpl<FoodGroupModel, Long> implements LogicalDeletedRestController<Long> {

    private FoodGroupService service;

    public FoodGroupRestController(FoodGroupService service) {
        super(service, FoodGroupModel.class);
        this.service = service;
    }

    @Override
    public LogicalDeletedService<Long> getService() {
        return service;
    }
}
