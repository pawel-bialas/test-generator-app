package com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto;

import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.shared.CycleAvoidingMappingContext;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface SkillTestMapper {


    SkillTest dtoToObject (SkillTestDto skillTestDto);

    SkillTestDto objectToDto (SkillTest skillTest);
}
