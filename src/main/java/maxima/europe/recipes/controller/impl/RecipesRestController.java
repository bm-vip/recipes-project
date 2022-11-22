package maxima.europe.recipes.controller.impl;

import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import maxima.europe.recipes.controller.LogicalDeletedRestController;
import maxima.europe.recipes.entity.RecipesEntity;
import maxima.europe.recipes.model.RecipesModel;
import maxima.europe.recipes.service.LogicalDeletedService;
import maxima.europe.recipes.service.RecipesService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Behrooz.Mohamadi on 20/11/2022.
 */
@RestController
@Tag(name = "Recipes Rest Service v1")
@RequestMapping(value = "/api/v1/recipes")
@Validated
public class RecipesRestController extends BaseRestControllerImpl<RecipesModel, Long> implements LogicalDeletedRestController<Long> {

    private RecipesService service;

    public RecipesRestController(RecipesService service) {
        super(service, RecipesModel.class);
        this.service = service;
    }

    @Override
    public LogicalDeletedService<Long> getService() {
        return service;
    }

    @GetMapping("/search")
    @Operation(summary = "${api.baseRest.predicate.search}", description = "${api.baseRest.predicate.example}")
    public ResponseEntity<Page<RecipesModel>> search(@QuerydslPredicate(root = RecipesEntity.class) Predicate predicate, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(service.search(predicate, pageable));
    }
}
