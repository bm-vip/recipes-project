package maxima.europe.recipes.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import maxima.europe.recipes.controller.LogicalDeletedRestController;
import maxima.europe.recipes.model.UnitTypeModel;
import maxima.europe.recipes.service.LogicalDeletedService;
import maxima.europe.recipes.service.UnitTypeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Behrooz.Mohamadi on 20/11/2022.
 */
@RestController
@Tag(name = "Unit Type Rest Service v1")
@RequestMapping(value = "/api/v1/unit-type")
@Validated
public class UnitTypeRestController extends BaseRestControllerImpl<UnitTypeModel, Long> implements LogicalDeletedRestController<Long> {

    private UnitTypeService service;

    public UnitTypeRestController(UnitTypeService service) {
        super(service, UnitTypeModel.class);
        this.service = service;
    }

    @Override
    public LogicalDeletedService<Long> getService() {
        return service;
    }
}
