package dk.bringlarsen.application.resources.transaction.mapper;

import dk.bringlarsen.application.domain.model.Transaction;
import dk.bringlarsen.application.resources.transaction.model.TransactionDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionDTOMapperTest {

    private final TransactionDTOMapper mapper = new TransactionDTOMapperImpl();

    @Test
    void mapTest() {
        Transaction from = createTransaction();

        TransactionDTO to = mapper.map(from);

        verifyMapping(from, to);
    }

    @Test
    void mapListTest() {
        List<Transaction> from = List.of(createTransaction());

        List<TransactionDTO> to = mapper.map(from);

        assertEquals(1, to.size());
        verifyMapping(from.get(0), to.get(0));
    }

    private Transaction createTransaction() {
        return new Transaction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "account",
            BigDecimal.TEN,
            ZonedDateTime.now());
    }

    private void verifyMapping(Transaction from, TransactionDTO to) {
        assertAll(
            () -> assertEquals(from.id(), to.id()),
            () -> assertEquals(from.accountId(), to.accountId()),
            () -> assertEquals(from.text(), to.text()),
            () -> assertEquals(from.amount(), to.amount()),
            () -> assertEquals(from.transactionDate(), to.transactionDate()));
    }
}
