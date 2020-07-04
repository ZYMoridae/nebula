package com.jz.nebula.dto.edu;

import lombok.Data;

@Data
public class ClazzParam {

    private String name;

    private String description;

    private String unit;

    private double unitPrice;

    private Long teacherId;

    private Long clazzCategoryId;

    private double rating;

    private int ratingCount;
}
