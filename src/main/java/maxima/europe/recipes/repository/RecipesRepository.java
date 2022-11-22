package maxima.europe.recipes.repository;

import maxima.europe.recipes.entity.QRecipesEntity;
import maxima.europe.recipes.entity.RecipesEntity;
import org.bitbucket.gt_tech.spring.data.querydsl.value.operators.ExpressionProviderFactory;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipesRepository extends BaseRepository<RecipesEntity, Long>, QuerydslBinderCustomizer<QRecipesEntity> {

    @Override
    default void customize(QuerydslBindings bindings, QRecipesEntity root) {
        bindings.bind(root.id).all((path, values) -> ExpressionProviderFactory.getPredicate(path, values));
        bindings.bind(root.food.id).all((path, values) -> ExpressionProviderFactory.getPredicate(path, values));
        bindings.bind(root.food.name).all((path, values) -> ExpressionProviderFactory.getPredicate(path, values));
        bindings.bind(root.food.needOven).firstOptional((path, optionalValue) -> optionalValue.map(v -> path.eq(v)));
        bindings.bind(root.food.servings).all((path, values) -> ExpressionProviderFactory.getPredicate(path, values));
        bindings.bind(root.food.foodGroup.id).all((path, values) -> ExpressionProviderFactory.getPredicate(path, values));
        bindings.bind(root.food.foodGroup.name).all((path, values) -> ExpressionProviderFactory.getPredicate(path, values));
        bindings.bind(root.ingredient.id).all((path, values) -> ExpressionProviderFactory.getPredicate(path, values));
        bindings.bind(root.ingredient.name).all((path, values) -> ExpressionProviderFactory.getPredicate(path, values));
        bindings.bind(root.ingredient.unitType.id).all((path, values) -> ExpressionProviderFactory.getPredicate(path, values));
        bindings.bind(root.ingredient.unitType.name).all((path, values) -> ExpressionProviderFactory.getPredicate(path, values));
    }
}