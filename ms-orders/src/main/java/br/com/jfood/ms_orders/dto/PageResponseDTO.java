package br.com.jfood.ms_orders.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Generic DTO used to represent a paginated API response in a stable and controlled structure.
 *
 * <p>
 * By default, Spring Data returns an implementation of {@link Page},
 * such as {@link org.springframework.data.domain.PageImpl}. However, serializing these objects directly
 * can lead to unstable or unpredictable JSON structures, especially across different framework versions.
 * </p>
 *
 * <p>
 * The {@code PageResponseDTO<T>} solves this by extracting and exposing only the relevant pagination information
 * in a fixed, client-friendly format. This makes it ideal for use in RESTful APIs where consistency and clarity are key.
 * </p>
 *
 * <p>
 * The response includes:
 * </p>
 * <ul>
 *   <li>The content of the current page</li>
 *   <li>The current page number</li>
 *   <li>The size of the page</li>
 *   <li>The total number of elements</li>
 *   <li>The total number of pages</li>
 *   <li>Whether this is the last page</li>
 * </ul>
 *
 * @param <T> The type of the content contained in the paginated response.
 */
public class PageResponseDTO<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public PageResponseDTO() {
    }

    public PageResponseDTO(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
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

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
