package ci.pabeu.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ci.pabeu.test.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

	Account findByAccountNumber(String accountNumber);

}
