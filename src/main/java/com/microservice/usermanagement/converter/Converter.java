package com.microservice.usermanagement.converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface Converter<E, D> {

    E fromDto(D dto);

    D fromEntity(E entity);

    default List<E> fromDto(final List<D> dtos) {
        if (dtos == null) return Arrays.asList();
        return dtos.stream().map(this::fromDto).collect(Collectors.toList());
    }

    default List<D> fromEntity(final List<E> entities) {
        if (entities == null) return Arrays.asList();
        return entities.stream().map(this::fromEntity).collect(Collectors.toList());
    }
}
