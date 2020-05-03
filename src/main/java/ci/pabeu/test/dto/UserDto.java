package ci.pabeu.test.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private Integer accountId;
	private String birthDay;

	public UserDto() {

	}

}
