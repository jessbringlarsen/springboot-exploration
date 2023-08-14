package dk.bringlarsen.application.usecase.customer;

import dk.bringlarsen.application.domain.model.Customer;
import dk.bringlarsen.domain.service.CustomerRepository;
import dk.bringlarsen.domain.service.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllCustomersUseCase {

    private final int maxPageSize;
    private final CustomerRepository repository;

    @Autowired
    public FindAllCustomersUseCase(@Value("${findAllCustomersUseCase.maxPageSize:100}") int maxPageSize,
                                   CustomerRepository repository) {
        this.maxPageSize = maxPageSize;
        this.repository = repository;
    }

    public List<Customer> execute(int pageNumber, int size, boolean sortDirectionAscending, String sortFields) {
        Pageable page = new Pageable(maxPageSize, size)
            .withPageNumber(pageNumber)
            .withSortDirectionAscending(sortDirectionAscending)
            .withSortFields(sortFields);
        return repository.findAll(page);
    }
}
