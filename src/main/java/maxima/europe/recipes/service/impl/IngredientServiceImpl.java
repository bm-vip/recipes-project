package maxima.europe.recipes.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import maxima.europe.recipes.entity.IngredientEntity;
import maxima.europe.recipes.entity.QIngredientEntity;
import maxima.europe.recipes.mapping.IngredientMapper;
import maxima.europe.recipes.model.IngredientModel;
import maxima.europe.recipes.repository.IngredientRepository;
import maxima.europe.recipes.service.IngredientService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static maxima.europe.recipes.util.MapperHelper.option;

@Service
public class IngredientServiceImpl extends BaseServiceImpl<IngredientModel, IngredientEntity, Long> implements IngredientService {

    private final IngredientRepository repository;

    public IngredientServiceImpl(IngredientRepository repository, IngredientMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public JpaRepository<IngredientEntity, Long> getRepository() {
        return repository;
    }

    @Override
    public Predicate queryBuilder(IngredientModel filter) {
        BooleanBuilder builder = new BooleanBuilder();
        QIngredientEntity path = QIngredientEntity.ingredientEntity;

        option(() -> filter.getId())
                .ifPresent(id -> builder.and(path.id.eq(id)));
        option(() -> filter.getUnitType().getId())
                .ifPresent(unitTypeId -> builder.and(path.unitType.id.eq(unitTypeId)));
        option(() -> filter.getUnitType().getName())
                .ifPresent(unitTypeName -> builder.and(path.unitType.name.contains(unitTypeName)));
        if (StringUtils.hasText(filter.getName()))
            builder.and(path.name.contains(filter.getName()));

        return builder;
    }
}
