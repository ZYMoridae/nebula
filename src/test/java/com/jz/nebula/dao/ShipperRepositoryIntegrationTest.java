//package com.jz.nebula.dao;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.jz.nebula.entity.Shipper;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//public class ShipperRepositoryIntegrationTest {
//
//	@Autowired
//	private TestEntityManager entityManager;
//
//	@Autowired
//	private ShipperRepository shipperRepository;
//
//	// write test cases here
//
//	@Test
//	public void findShipperByName() {
//		// given
//		Shipper alex = new Shipper("alex");
//		entityManager.persist(alex);
//		entityManager.flush();
//
//		// when
//		Shipper found = shipperRepository.findByName(alex.getName());
//
//		// then
//		assertEquals(found.getName(), alex.getName());
//	}
//}
