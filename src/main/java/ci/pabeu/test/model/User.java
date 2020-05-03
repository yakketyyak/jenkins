package ci.pabeu.test.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "user")
@Indexed(index = "users")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "first_name")
	@Field(termVector = TermVector.YES)
	private String firstName;
	@Column(name = "last_name")
	@Field(termVector = TermVector.YES)
	private String lastName;
	@Column(name = "email")
	private String email;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id")
	private Account account;
	@Column(name = "birth_day")
	@Field(termVector = TermVector.YES)
	private Date birthDay;

	public User() {

	}


}
