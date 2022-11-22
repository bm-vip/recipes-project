package maxima.europe.recipes.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import maxima.europe.recipes.entity.FoodEntity;
import maxima.europe.recipes.entity.QFoodEntity;
import maxima.europe.recipes.mapping.FoodMapper;
import maxima.europe.recipes.model.FoodModel;
import maxima.europe.recipes.repository.FoodRepository;
import maxima.europe.recipes.service.FoodService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static maxima.europe.recipes.util.MapperHelper.option;

@Service
public class FoodServiceImpl extends BaseServiceImpl<FoodModel, FoodEntity, Long> implements FoodService {

    private final FoodRepository repository;

    public FoodServiceImpl(FoodRepository repository, FoodMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public JpaRepository<FoodEntity, Long> getRepository() {
        return repository;
    }

    @Override
    public Predicate queryBuilder(FoodModel filter) {
        BooleanBuilder builder = new BooleanBuilder();
        QFoodEntity path = QFoodEntity.foodEntity;

        option(() -> filter.getId()).ifPresent(id -> builder.and(path.id.eq(id)));
        option(() -> filter.getFoodGroup().getId()).ifPresent(foodGroupId -> builder.and(path.foodGroup.id.eq(foodGroupId)));
        option(() -> filter.getFoodGroup().getName()).ifPresent(foodGroupName -> builder.and(path.foodGroup.name.contains(foodGroupName)));
        option(() -> filter.getServings()).ifPresent(servings -> builder.and(path.servings.eq(servings)));
        option(() -> filter.getNeedOven()).ifPresent(needOven -> builder.and(path.needOven.eq(needOven)));
        if (StringUtils.hasText(filter.getName()))
            builder.and(path.name.contains(filter.getName()));

        return builder;
    }
}
