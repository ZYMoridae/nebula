package com.jz.nebula.dto.edu;

import lombok.Data;

import java.util.List;

@Data
public class ToCartOrderParam {

    private List<Long> cartItemIds;

}
