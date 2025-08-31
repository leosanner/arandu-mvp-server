package dev.leonardosanner.arandu_mvp_server.mapper;


import dev.leonardosanner.arandu_mvp_server.model.dto.UpdateEventDTO;
import dev.leonardosanner.arandu_mvp_server.model.entity.EventEntity;
import org.mapstruct.*;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventUpdateMapper {

//    @Mappings({
//            @Mapping(target = "name", conditionQualifiedByName = "notBlank"),
//            @Mapping(target = "description", conditionQualifiedByName = "notBlank"),
//
//    })ch
    void updateEvent(UpdateEventDTO updateEventDTO,
                     @MappingTarget EventEntity eventEntity);

    @Named("notBlank")
    default boolean notBlank(String fieldValue) {
        return !fieldValue.isBlank();
    }

    @AfterMapping
    default void trimStrings(@MappingTarget EventEntity eventEntity) {
        if(eventEntity.getName() != null) eventEntity.setName(eventEntity.getName().trim());
        if(eventEntity.getDescription() != null) eventEntity.setDescription(eventEntity.getDescription().trim());
    }
}
