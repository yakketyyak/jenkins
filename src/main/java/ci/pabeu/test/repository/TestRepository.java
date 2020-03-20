package ci.pabeu.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ci.pabeu.test.model.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

	Test findByFirstName(String firstName);

}
