package ci.pabeu.test.service;

import java.math.BigDecimal;

import ci.pabeu.test.model.Account;

public interface AccountService {

	public Account depot(Long accountNumber, BigDecimal deposit);

	public BigDecimal retrait(Long accountNumber, BigDecimal deposit);

}
