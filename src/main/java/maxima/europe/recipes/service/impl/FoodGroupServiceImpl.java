package maxima.europe.recipes.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import maxima.europe.recipes.entity.FoodGroupEntity;
import maxima.europe.recipes.entity.QFoodGroupEntity;
import maxima.europe.recipes.mapping.FoodGroupMapper;
import maxima.europe.recipes.model.FoodGroupModel;
import maxima.europe.recipes.repository.FoodGroupRepository;
import maxima.europe.recipes.service.FoodGroupService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static maxima.europe.recipes.util.MapperHelper.option;

@Service
public class FoodGroupServiceImpl extends BaseServiceImpl<FoodGroupModel, FoodGroupEntity, Long> implements FoodGroupService {
    private final FoodGroupRepository repository;

    public FoodGroupServiceImpl(FoodGroupRepository repository, FoodGroupMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public JpaRepository<FoodGroupEntity,Long> getRepository() {
        return repository;
    }

    @Override
    public Predicate queryBuilder(FoodGroupModel filter) {
        BooleanBuilder builder = new BooleanBuilder();
        QFoodGroupEntity path = QFoodGroupEntity.foodGroupEntity;

        option(() -> filter.getId())
                .ifPresent(id -> builder.and(path.id.eq(id)));
        if (StringUtils.hasText(filter.getName()))
            builder.and(path.name.contains(filter.getName()));

        return builder;
    }
}
