package com.jz.nebula.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = false)
public class JobCategoryParam {

    @Schema(description = "Name of job category",
            example = "Name", required = true)
    @NotEmpty(message = "Name can't be empty")
    private String name;

    @Schema(description = "Code of job category",
            example = "Code", required = true)
    @NotEmpty(message = "Code can't be empty")
    private String code;
}
