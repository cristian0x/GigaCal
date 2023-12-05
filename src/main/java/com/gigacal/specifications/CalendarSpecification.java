package com.gigacal.specifications;

import com.gigacal.entity.CalendarEntity;
import org.springframework.data.jpa.domain.Specification;

public class CalendarSpecification {

    public static Specification<CalendarEntity> hasName(String name){
        return (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name)));
    }
}
