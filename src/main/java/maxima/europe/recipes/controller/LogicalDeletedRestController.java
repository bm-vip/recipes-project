package maxima.europe.recipes.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import maxima.europe.recipes.service.LogicalDeletedService;

import java.io.Serializable;

/**
 * @author Behrooz Mohamadi
 * @email behroooz.mohamadi@gmail.com
 * @date 3/27/2018 11:42 AM
 */
public interface LogicalDeletedRestController<ID extends Serializable> {

    LogicalDeletedService<ID> getService();

    @DeleteMapping(value = {"/logical-delete/{id}"})
    @Operation(summary = "${api.baseRest.logicalDeleteById}", description = "${api.baseRest.logicalDeleteById.desc}")
    default ResponseEntity<Void> logicalDeleteById(@PathVariable("id") ID id) {
        getService().logicalDeleteById(id);
        return ResponseEntity.noContent().build();
    }
}
