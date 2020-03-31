package ci.pabeu.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.pabeu.test.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByFirstName(String firstName);

	@Query(value = "select u from User u order by u.id desc")
	List<User> getAll();

}
