package maxima.europe.recipes.controller;

import io.swagger.v3.oas.annotations.Operation;
import maxima.europe.recipes.validation.Save;
import maxima.europe.recipes.validation.Update;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Behrooz Mohamadi
 * @email behroooz.mohamadi@gmail.com
 * @date 20/11/2022 11:42 AM
 */
@Validated
public interface BaseRestController<M, ID extends Serializable> {

    @GetMapping(value = {"/{id}"})
    @Operation(summary = "${api.baseRest.findById}", description = "${api.baseRest.findById.desc}")
    ResponseEntity<M> findById(@PathVariable("id") ID id);

    @GetMapping
    @Operation(summary = "${api.baseRest.findAll}", description = "${api.baseRest.findAll.desc}")
    ResponseEntity<Page<M>> findAll(@RequestParam(value = "model", required = false) Optional<String> json
            , @PageableDefault Pageable pageable);

    @GetMapping(value = {"/count"})
    @Operation(summary = "${api.baseRest.countAll}", description = "${api.baseRest.countAll.desc}")
    ResponseEntity<Long> countAll(@RequestParam(value = "model", required = false) Optional<String> json);

    @DeleteMapping(value = {"/{id}"})
    @Operation(summary = "${api.baseRest.deleteById}", description = "${api.baseRest.deleteById.desc}")
    ResponseEntity<Void> deleteById(@PathVariable("id") ID id);

    @PostMapping
    @ResponseBody
    @Operation(summary = "${api.baseRest.save}", description = "${api.baseRest.save.desc}")
    ResponseEntity<M> save(@Validated(Save.class) @RequestBody M model);

    @PatchMapping
    @ResponseBody
    @Operation(summary = "${api.baseRest.update}", description = "${api.baseRest.update.desc}")
    ResponseEntity<M> update(@Validated(Update.class) @RequestBody M model);
}
