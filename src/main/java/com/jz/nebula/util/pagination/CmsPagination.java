package com.jz.nebula.util.pagination;

import java.util.List;

public class CmsPagination<T> {
    List contents;

    int totalPages;

    int pageNumber;

    int pageSize;

    String path;

    List pageNumberArray;

    int prevIndex;

    int nextIndex;

    List perPageOptions;

    public List getContents() {
        return contents;
    }

    public void setContents(List contents) {
        this.contents = contents;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List getPageNumberArray() {
        return pageNumberArray;
    }

    public void setPageNumberArray(List pageNumberArray) {
        this.pageNumberArray = pageNumberArray;
    }

    public int getPrevIndex() {
        return prevIndex;
    }

    public void setPrevIndex(int prevIndex) {
        this.prevIndex = prevIndex;
    }

    public int getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }

    public List getPerPageOptions() {
        return perPageOptions;
    }

    public void setPerPageOptions(List perPageOptions) {
        this.perPageOptions = perPageOptions;
    }
}
