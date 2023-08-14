package dk.bringlarsen.application.infrastructure.persistence;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseRepositoryTest {

    @Autowired
    protected EntityManager entityManager;

    protected void synchronizeWithDatabase() {
        entityManager.flush();
        entityManager.clear();
    }
}
