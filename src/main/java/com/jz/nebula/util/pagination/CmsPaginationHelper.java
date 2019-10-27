package com.jz.nebula.util.pagination;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CmsPaginationHelper<T> {
    private final static Logger logger = LogManager.getLogger(CmsPaginationHelper.class);

    private List perPageOptions;

    public CmsPaginationHelper() {
        perPageOptions = new ArrayList();
        perPageOptions.add(10);
        perPageOptions.add(30);
        perPageOptions.add(50);
    }

    /**
     * Get page number array
     *
     * @param totalPages
     * @param pageNumber
     * @return
     */
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

    /**
     * Get prev and next index
     *
     * @param pageNumbers
     * @param pageNumber
     * @return
     */
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

    /**
     * Get pagination
     *
     * @param pageable
     * @param pageData
     * @param resourcePath
     * @return
     */
    public CmsPagination getCmsPagination(Pageable pageable, Page<T> pageData, String resourcePath) {
        logger.debug("findAll:: pageNumber:[{}], pageSize: [{}]", pageable.getPageNumber(), pageable.getPageSize());
        List<T> dataList = new ArrayList<>();
        pageData.iterator().forEachRemaining(dataList::add);

        CmsPagination<T> cmsPagination = new CmsPagination<>();

        cmsPagination.setContents(dataList);
        cmsPagination.setTotalPages(pageData.getTotalPages());
        cmsPagination.setPageNumber(pageable.getPageNumber());
        cmsPagination.setPageSize(pageable.getPageSize());
        cmsPagination.setPath(resourcePath);

        List pageNumberArray = getPageNumberArray(pageData.getTotalPages(), pageable.getPageNumber());
        cmsPagination.setPageNumberArray(pageNumberArray);

        HashMap<String, Integer> prevNextInfo = getPrevAndNextIndex(pageNumberArray, pageable.getPageNumber());

        cmsPagination.setPrevIndex(prevNextInfo.get("prev"));
        cmsPagination.setNextIndex(prevNextInfo.get("next"));

        cmsPagination.setPerPageOptions(perPageOptions);

        return cmsPagination;
    }

    public List getPerPageOptions() {
        return perPageOptions;
    }

    public void setPerPageOptions(List perPageOptions) {
        this.perPageOptions = perPageOptions;
    }
}
