package dk.bringlarsen.domain.service;

public class Pageable {

    private int page;
    private int size;
    private boolean sortAscending;
    private String[] sortFields;

    public Pageable(int maxPageSize, int size) {
        this.page = 0;
        this.sortAscending = true;
        this.withSortFields("id");
        this.size = size;
        if (size == 0 || size > maxPageSize) {
            this.size = maxPageSize;
        }
    }

    public Pageable withPageNumber(int page) {
        this.page = page;
        return this;
    }

    public Pageable withSortDirectionAscending(boolean sortAscending) {
        this.sortAscending = sortAscending;
        return this;
    }

    public Pageable withSortFields(String sortFields) {
        if (sortFields != null && !sortFields.isBlank()) {
            this.sortFields = sortFields.split(",");
        }
        return this;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public boolean sortAscending() {
        return sortAscending;
    }

    public String[] getSortFields() {
        return sortFields;
    }
}
