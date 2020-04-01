package ci.pabeu.test.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "account")
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String accountNumber;
	@Column(name = "amount")
	private BigDecimal amount;

	public Account(String accountNumber, BigDecimal amount) {
		this.accountNumber = accountNumber;
		this.amount = amount;
	}

}
