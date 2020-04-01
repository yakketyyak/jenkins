package ci.pabeu.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ci.pabeu.test.model.TypeOfAccount;

@Repository
public interface TypeOfAccountRepository extends JpaRepository<TypeOfAccount, Long> {

}
