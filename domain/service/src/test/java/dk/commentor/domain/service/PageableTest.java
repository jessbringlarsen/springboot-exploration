package dk.bringlarsen.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PageableTest {

    @Test
    void givenNoSortFieldsExpectDefaultSortField() {
        Pageable page = create();

        assertArrayEquals(new String[]{"id"}, page.getSortFields());
    }

    @Test
    void givenNoSortDirectionExpectDefaultSortDirection() {
        Pageable page = create();

        assertTrue(page.sortAscending());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2})
    void givenAnInvalidSizeExpectMaxSize(int size) {
        Pageable page = new Pageable(1, size);

        assertEquals(1, page.getSize());
    }

    @Test
    void givenMultipleSortFieldsExpectArrayOfFields() {
        Pageable page = create()
            .withSortFields("id,name");

        assertArrayEquals(new String[]{"id", "name"}, page.getSortFields());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void givenBlankSortFieldsExpectDefaultValues(String input) {
        Pageable page = create()
            .withSortFields(input);

        assertArrayEquals(new String[]{"id"}, page.getSortFields());
    }

    @Test
    void givenNoSortDirectionExpectDefault() {
        Pageable page = create();

        assertTrue(page.sortAscending());
    }

    private Pageable create() {
        return new Pageable(0, 1);
    }
}
