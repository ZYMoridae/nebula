package com.jz.nebula.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class JobParam {
    @Schema(description = "Description of job",
            example = "This is description", required = true)
    @NotEmpty(message = "Description can't be empty")
    private String description;

    @Schema(description = "Title of job",
            example = "This is title", required = true)
    @NotEmpty(message = "Title can't be empty")
    private String title;

    @Schema(description = "User id",
            example = "1")
    @NotEmpty(message = "User id can't be empty")
    private Long userId;

    @Schema(description = "Contact", example = "0324567987")
    private String contact;

    @Schema(description = "Job category ids",
            example = "[1, 2, 3]")
    private List<Long> jobCategoryIds;
}
