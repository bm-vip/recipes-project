package maxima.europe.recipes.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import maxima.europe.recipes.entity.QUnitTypeEntity;
import maxima.europe.recipes.entity.UnitTypeEntity;
import maxima.europe.recipes.mapping.UnitTypeMapper;
import maxima.europe.recipes.model.UnitTypeModel;
import maxima.europe.recipes.repository.UnitTypeRepository;
import maxima.europe.recipes.service.UnitTypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static maxima.europe.recipes.util.MapperHelper.option;

@Service
public class UnitTypeServiceImpl extends BaseServiceImpl<UnitTypeModel, UnitTypeEntity, Long> implements UnitTypeService {

    private final UnitTypeRepository repository;

    public UnitTypeServiceImpl(UnitTypeRepository repository, UnitTypeMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public JpaRepository<UnitTypeEntity, Long> getRepository() {
        return repository;
    }

    @Override
    public Predicate queryBuilder(UnitTypeModel filter) {
        BooleanBuilder builder = new BooleanBuilder();
        QUnitTypeEntity path = QUnitTypeEntity.unitTypeEntity;

        option(() -> filter.getId())
                .ifPresent(id -> builder.and(path.id.eq(id)));
        if (StringUtils.hasText(filter.getName()))
            builder.and(path.name.contains(filter.getName()));

        return builder;
    }
}
