package ci.pabeu.test.service;

import java.math.BigDecimal;

public interface AccountService {

	public void depot(String accountNumber, BigDecimal deposit);

	public boolean retrait(String accountNumber, BigDecimal deposit);

}
