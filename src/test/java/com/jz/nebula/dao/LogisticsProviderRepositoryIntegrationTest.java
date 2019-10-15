package com.jz.nebula.dao;

import static org.junit.Assert.assertEquals;

import com.jz.nebula.entity.LogisticsProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LogisticsProviderRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LogisticsProviderRepository logisticsProviderRepository;

    // write test cases here

    @Test
    public void findShipperByName() {
        // given
        LogisticsProvider alex = new LogisticsProvider("alex");
        entityManager.persist(alex);
        entityManager.flush();

        // when
        LogisticsProvider found = logisticsProviderRepository.findByName(alex.getName());

        // then
        assertEquals(found.getName(), alex.getName());
    }
}
