package ci.pabeu.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ci.pabeu.test.repository.TestRepository;

@SpringBootTest
@Transactional
class SpringTestApplicationTests {

	@Autowired
	private TestRepository testRepository;

	@Test
	void contextLoads() {
		assertEquals(true, Boolean.TRUE);
	}

	@Test
	public void test() {
		ci.pabeu.test.model.Test entity = testRepository.save(new ci.pabeu.test.model.Test("test", "pabeu", "Tech"));
		ci.pabeu.test.model.Test foundEntity = testRepository.getOne(entity.getId());

		assertNotNull(foundEntity);
		assertEquals(entity.getFirstName(), foundEntity.getFirstName());
		assertEquals(entity.getLastName(), "pabeu");
	}

	@Test
	public void test1() {

		List<ci.pabeu.test.model.Test> foundEntity = testRepository.findAll();

		assertNotNull(foundEntity);
		assertEquals(foundEntity.size(), 3);
	}

	@Test
	public void test2FindByFirstName() {

		ci.pabeu.test.model.Test foundEntity = testRepository.findByFirstName("Aliko");
		// ci.pabeu.test.model.Test foundEntityId =
		// testRepository.getOne(foundEntity.getId());

		assertNotNull(foundEntity);
		// assertSame(foundEntity, foundEntityId);
	}

}
