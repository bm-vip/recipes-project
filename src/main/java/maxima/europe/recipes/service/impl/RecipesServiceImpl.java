package maxima.europe.recipes.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import maxima.europe.recipes.entity.QRecipesEntity;
import maxima.europe.recipes.entity.RecipesEntity;
import maxima.europe.recipes.mapping.RecipesMapper;
import maxima.europe.recipes.model.RecipesModel;
import maxima.europe.recipes.repository.RecipesRepository;
import maxima.europe.recipes.service.RecipesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import static maxima.europe.recipes.util.MapperHelper.option;

@Service
public class RecipesServiceImpl extends BaseServiceImpl<RecipesModel, RecipesEntity, Long> implements RecipesService {

    private final RecipesRepository repository;
    private final RecipesMapper mapper;

    public RecipesServiceImpl(RecipesRepository repository, RecipesMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<RecipesEntity,Long> getRepository() {
        return repository;
    }

    @Override
    public Predicate queryBuilder(RecipesModel filter) {
        BooleanBuilder builder = new BooleanBuilder();
        QRecipesEntity path = QRecipesEntity.recipesEntity;

        option(() -> filter.getId()).ifPresent(id -> builder.and(path.id.eq(id)));
        option(() -> filter.getFood().getId()).ifPresent(foodId -> builder.and(path.food.id.eq(foodId)));
        option(() -> filter.getFood().getName()).ifPresent(foodName -> builder.and(path.food.name.contains(foodName)));
        option(() -> filter.getFood().getServings()).ifPresent(foodServing -> builder.and(path.food.servings.eq(foodServing)));
        option(() -> filter.getFood().getNeedOven()).ifPresent(foodNeedOven -> builder.and(path.food.needOven.eq(foodNeedOven)));
        option(() -> filter.getFood().getFoodGroup().getId()).ifPresent(foodGroupId -> builder.and(path.food.foodGroup.id.eq(foodGroupId)));
        option(() -> filter.getFood().getFoodGroup().getName()).ifPresent(foodGroupName -> builder.and(path.food.foodGroup.name.contains(foodGroupName)));
        option(() -> filter.getIngredient().getId()).ifPresent(ingredientId -> builder.and(path.ingredient.id.eq(ingredientId)));
        option(() -> filter.getIngredient().getUnitType().getId()).ifPresent(ingredientUnitTypeId -> builder.and(path.ingredient.unitType.id.eq(ingredientUnitTypeId)));
        option(() -> filter.getIngredient().getUnitType().getName()).ifPresent(ingredientUnitTypeName -> builder.and(path.ingredient.unitType.name.contains(ingredientUnitTypeName)));
        option(() -> filter.getIngredient().getName()).ifPresent(ingredientName -> builder.and(path.ingredient.name.contains(ingredientName)));
        option(() -> filter.getCount()).ifPresent(count -> builder.and(path.count.eq(count)));

        return builder;
    }

    @Override
    public Page<RecipesModel> search(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable).map(mapper::toModel);
    }
}
