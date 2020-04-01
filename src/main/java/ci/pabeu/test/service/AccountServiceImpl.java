package ci.pabeu.test.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import ci.pabeu.test.model.Account;
import ci.pabeu.test.model.User;
import ci.pabeu.test.repository.AccountRepository;
import ci.pabeu.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private static volatile BigDecimal amount;

	private AccountRepository accountRepository;
	private UserRepository userRepository;

	@Override
	public void depot(String accountNumber, BigDecimal deposit) {
		// TODO Auto-generated method stub
		Account account = this.accountRepository.findByAccountNumber(accountNumber);
		if (account != null) {
			account.setAmount(account.getAmount().add(deposit));
		}
	}

	@Override
	public boolean retrait(String accountNumber, BigDecimal deposit) {
		// TODO Auto-generated method stub
		Account account = this.accountRepository.findByAccountNumber(accountNumber);
		if (account != null) {
			amount = account.getAmount();
			synchronized (amount) {
				if (BigDecimal.ZERO.compareTo(deposit) == 0 || (deposit.compareTo(amount) > 0)) {
					return false;
				}
				
				account.setAmount(amount.subtract(deposit));
			}
		}

		return true;

	}

	public Account create(Long userId, String accountNumber) {
		Account account = null;
		User user = this.userRepository.getOne(userId);
		if (user != null) {
			account = new Account(accountNumber, BigDecimal.ZERO);
			account = this.accountRepository.save(account);
			user.setAccount(account);
			this.userRepository.save(user);
		}

		return account;
	}

	public void delete(Long userId, String accountNumber) {
		Account account = this.accountRepository.findByAccountNumber(accountNumber);
		User user = this.userRepository.getOne(userId);
		if (account != null && user != null) {
			user.setAccount(null);
			this.accountRepository.delete(account);
			this.userRepository.save(user);
		}

	}

}
