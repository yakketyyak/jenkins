package ci.pabeu.test.rest;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ci.pabeu.test.model.Account;
import ci.pabeu.test.model.TypeOfAccount;
import ci.pabeu.test.repository.AccountRepository;
import ci.pabeu.test.repository.TypeOfAccountRepository;
import ci.pabeu.test.service.AccountServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {


	private final AccountRepository accountRepository;

	private final TypeOfAccountRepository typeOfAccountRepository;

	private final AccountServiceImpl accountServiceImpl;

	private static final Logger slf4jLogger = LoggerFactory.getLogger(AccountController.class);

	@GetMapping(value = "/type/get", produces = { "application/json" })
	public ResponseEntity<List<TypeOfAccount>> getType() {
		slf4jLogger.info("/type/get " + this.typeOfAccountRepository.findAll().size());

		return new ResponseEntity<>(this.typeOfAccountRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/get", produces = { "application/json" })
	public ResponseEntity<Account> get(@RequestParam(name = "accountNumber") Long accountNumber) {
		slf4jLogger.info("/get");

		return new ResponseEntity<>(this.accountRepository.findByAccountNumber(accountNumber), HttpStatus.CREATED);
	}

	@PostMapping(value = "/depot", consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<Account> depot(@RequestBody Account account) {
		slf4jLogger.info("/depot");
		account = this.accountServiceImpl.depot(account.getAccountNumber(), account.getAmount());

		return new ResponseEntity<>(account, HttpStatus.CREATED);
	}

	@PostMapping(value = "/retrait", consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<BigDecimal> retrait(@RequestBody Account account) {
		slf4jLogger.info("/retrait");
		BigDecimal amount = BigDecimal.ZERO;
		Account accountFound = this.accountRepository.findByAccountNumber(account.getAccountNumber());
		if (accountFound != null) {
			amount = this.accountServiceImpl.retrait(account.getAccountNumber(), account.getAmount());

		}


		return new ResponseEntity<>(amount, HttpStatus.CREATED);
	}

	@GetMapping(value = "/create/{userId}/{accountNumber}/{typeOfAccountId}", produces = { "application/json" })
	public ResponseEntity<Account> create(@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "accountNumber") Long accountNumber,
			@PathVariable(value = "typeOfAccountId") Long typeOfAccountId) {
		slf4jLogger.info("/create");


		return new ResponseEntity<>(this.accountServiceImpl.create(userId, accountNumber, typeOfAccountId),
				HttpStatus.CREATED);
	}

	@GetMapping(value = "/delete/{userId}/{accountNumber}", produces = { "application/json" })
	public void delete(@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "accountNumber") Long accountNumber) {
		slf4jLogger.info("/delete");
		this.accountServiceImpl.delete(userId, accountNumber);

	}

}
