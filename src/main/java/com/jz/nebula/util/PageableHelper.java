package com.jz.nebula.util;

import io.swagger.models.auth.In;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PageableHelper {
    private final static Logger logger = LogManager.getLogger(PageableHelper.class);

    public static List getPageNumberArray(int totalPages, int pageNumber) {
        int displayLimit = 5;
        List<Integer> pageNumberList = new ArrayList<>();


        if (totalPages <= displayLimit) {
            pageNumberList = IntStream.rangeClosed(0, totalPages - 1)
                    .boxed().collect(Collectors.toList());
        } else {
            // Assume totalPages 10, displayLimit 5, pageNumber
            for (int i = pageNumber - 2; i < pageNumber + 3; i++) {
                pageNumberList.add(i);
            }

            List<Integer> _pageNumberList = new ArrayList<>();

            for (Integer i : pageNumberList
            ) {
                if (i >= 0 && i < totalPages) {
                    _pageNumberList.add(i);
                }
            }

            pageNumberList = _pageNumberList;
        }

        logger.debug("getPageNumberArray:: [{}]", Arrays.toString(pageNumberList.toArray()));

        return pageNumberList;
    }

    public static HashMap<String, Integer> getPrevAndNextIndex(List<Integer> pageNumbers, int pageNumber) {
        int prevPage = -1;
        int nextPage = -1;

        int pageIndex = pageNumbers.indexOf(pageNumber);

        if (pageIndex - 1 >= 0 && (pageIndex - 1) < pageNumbers.size()) {
            prevPage = pageNumbers.get(pageIndex - 1);
        }

        if (pageIndex + 1 >= 0 && (pageIndex + 1) < pageNumbers.size()) {
            nextPage = pageNumbers.get(pageIndex + 1);
        }

        HashMap<String, Integer> prevNextInfo = new HashMap<>();
        prevNextInfo.put("prev", prevPage);
        prevNextInfo.put("next", nextPage);

        return prevNextInfo;

    }
}
