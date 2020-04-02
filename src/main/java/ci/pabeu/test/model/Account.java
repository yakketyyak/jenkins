package ci.pabeu.test.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "account")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long accountNumber;
	@Column(name = "amount")
	private BigDecimal amount;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_of_account_id")
	private TypeOfAccount typeOfAccount;

	public Account() {

	}
	public Account(Long accountNumber, BigDecimal amount) {
		this.accountNumber = accountNumber;
		this.amount = amount;
	}

}
