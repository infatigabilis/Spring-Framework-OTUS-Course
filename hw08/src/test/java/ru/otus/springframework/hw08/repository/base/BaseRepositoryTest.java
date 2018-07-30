package ru.otus.springframework.hw08.repository.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import ru.otus.springframework.hw08.BaseTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class BaseRepositoryTest extends BaseTest {
    @PersistenceContext protected EntityManager em;
    @Autowired protected TransactionTemplate transactionTemplate;
}
