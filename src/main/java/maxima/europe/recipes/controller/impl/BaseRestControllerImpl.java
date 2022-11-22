package maxima.europe.recipes.controller.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import maxima.europe.recipes.controller.BaseRestController;
import maxima.europe.recipes.exception.BadRequestException;
import maxima.europe.recipes.model.BaseModel;
import maxima.europe.recipes.service.BaseService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Behrooz Mohamadi
 * @email behroooz.mohamadi@gmail.com
 * @date 3/27/2018 11:42 AM
 */
@Slf4j
@PropertySource("classpath:messages.properties")
public abstract class BaseRestControllerImpl<M extends BaseModel<ID>, ID extends Serializable> implements BaseRestController<M, ID> {
    protected BaseService<M, ID> service;
    protected Class<M> clazz;
    protected ObjectMapper objectMapper;

    public BaseRestControllerImpl(BaseService<M, ID> service, Class<M> clazz) {
        this.clazz = clazz;
        this.service = service;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ResponseEntity<M> findById(ID id) {
        log.debug("call BaseRestImpl.findById {}, for class {}", id, clazz.getName());
        return ResponseEntity.ok(service.findById(id));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<Page<M>> findAll(Optional<String> json, Pageable pageable) {
        log.debug("call BaseRestImpl.findAll {}, for class {}", json, clazz.getName());
        M model = objectMapper.readValue(json.orElse("{}"), clazz);
        return ResponseEntity.ok(service.findAll(model, pageable));
    }

    @SneakyThrows
    @Override
    public ResponseEntity<Long> countAll(Optional<String> json) {
        log.debug("call BaseRestImpl.countAll {}, for class {}", json, clazz.getName());
        M model = objectMapper.readValue(json.orElse("{}"), clazz);
        return ResponseEntity.ok(service.countAll(model));
    }

    @Override
    public ResponseEntity<Void> deleteById(ID id) {
        log.debug("call BaseRestImpl.deleteById {}, for class {}", id, clazz.getName());
        service.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @Override
    public ResponseEntity<M> save(M model) {
        log.debug("call BaseRestImpl.save for {}");
        if (model.getId() != null) {
            throw new BadRequestException("The id must not be provided when creating a new record");
        }
        return ResponseEntity.ok(service.save(model));
    }

    @Override
    public ResponseEntity<M> update(M model) {
        log.debug("call BaseRestImpl.update for {}", model, clazz.getName());
        return ResponseEntity.ok(service.save(model));
    }

}
