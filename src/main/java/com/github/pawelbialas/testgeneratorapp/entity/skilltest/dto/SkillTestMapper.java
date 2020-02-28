package com.github.pawelbialas.testgeneratorapp.entity.skilltest.dto;

import com.github.pawelbialas.testgeneratorapp.entity.result.dto.ResultDto;
import com.github.pawelbialas.testgeneratorapp.entity.result.model.Result;
import com.github.pawelbialas.testgeneratorapp.entity.skilltest.model.SkillTest;
import com.github.pawelbialas.testgeneratorapp.shared.DateMapper;
import com.github.pawelbialas.testgeneratorapp.shared.MapperBean;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface SkillTestMapper extends MapperBean {


    SkillTest dtoToObject (SkillTestDto skillTestDto);

    SkillTestDto objectToDto (SkillTest skillTest);
}
