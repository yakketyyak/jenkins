package ci.pabeu.test.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ci.pabeu.test.model.Account;
import ci.pabeu.test.model.TypeOfAccount;
import ci.pabeu.test.model.User;
import ci.pabeu.test.repository.AccountRepository;
import ci.pabeu.test.repository.TypeOfAccountRepository;
import ci.pabeu.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private static volatile BigDecimal amount;

	private static final Logger slf4jLogger = LoggerFactory.getLogger(AccountServiceImpl.class);

	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final TypeOfAccountRepository typeOfAccountRepository;

	@Override
	public Account depot(Long accountNumber, BigDecimal deposit) {
		// TODO Auto-generated method stub
		Account account = this.accountRepository.findByAccountNumber(accountNumber);
		if (account != null) {
			account.setAmount(account.getAmount().add(deposit));
			account = this.accountRepository.save(account);
		}

		return account;
	}

	@Override
	public BigDecimal retrait(Long accountNumber, BigDecimal deposit) {
		// TODO Auto-generated method stub
		Account account = this.accountRepository.findByAccountNumber(accountNumber);
		if (account != null) {
			amount = account.getAmount();
			synchronized (amount) {
				if (BigDecimal.ZERO.compareTo(deposit) == 0 || (deposit.compareTo(amount) > 0)) {
					return BigDecimal.ZERO;
				}
				
				account.setAmount(amount.subtract(deposit));
				account = this.accountRepository.save(account);
			}
		}

		return account.getAmount();

	}

	public Account create(Long userId, Long accountNumber, Long typeOfAccountId) {
		Account account = null;
		User user = this.userRepository.getOne(userId);
		TypeOfAccount typeOfAccount = typeOfAccountRepository.getOne(typeOfAccountId);
		if (user != null && typeOfAccount != null) {
			account = new Account(accountNumber, BigDecimal.ZERO);
			account.setTypeOfAccount(typeOfAccount);
			account = this.accountRepository.save(account);
			user.setAccount(account);
			this.userRepository.save(user);
		}

		return account;
	}

	public void delete(Long userId, Long accountNumber) {
		Account account = this.accountRepository.findByAccountNumber(accountNumber);
		User user = this.userRepository.getOne(userId);
		if (account != null && user != null) {
			user.setAccount(null);
			this.accountRepository.delete(account);
			this.userRepository.save(user);
		}

	}

}
