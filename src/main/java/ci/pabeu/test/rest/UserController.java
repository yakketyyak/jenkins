package ci.pabeu.test.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ci.pabeu.test.dto.UserDto;
import ci.pabeu.test.model.User;
import ci.pabeu.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private UserRepository userRepository;

	private static final Logger slf4jLogger = LoggerFactory.getLogger(UserController.class);

	@PostMapping(value = "/create", consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<User> create(@RequestBody UserDto userDto) throws ParseException {
		slf4jLogger.info("create");

		User user = new User();
		user.setUserName(userDto.getUserName());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		if (!StringUtils.isEmpty(userDto.getBirthDay())) {
			user.setBirthDay(new SimpleDateFormat("yyyy-MM-dd").parse(userDto.getBirthDay()));
		}

		user = userRepository.save(user);

		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "/update/{id}", consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<User> update(@RequestBody UserDto userDto, @PathVariable(name = "id") Long id) throws ParseException {
		slf4jLogger.info("update");
		User founded = userRepository.getOne(id);
		founded.setEmail(userDto.getEmail());
		founded.setFirstName(userDto.getFirstName());
		founded.setLastName(userDto.getLastName());
		founded.setUserName(userDto.getUserName());
		
		if (!StringUtils.isEmpty(userDto.getBirthDay())) {
			founded.setBirthDay(new SimpleDateFormat("yyyy-MM-dd").parse(userDto.getBirthDay()));
		}
		founded = userRepository.save(founded);

		return new ResponseEntity<>(founded, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}", produces = { "application/json" })
	public void delete(@PathVariable(name = "id") Long id) {
		slf4jLogger.info("delete");
		User founded = userRepository.getOne(id);
		userRepository.delete(founded);

	}

	@GetMapping(value = "/getAll", produces = { "application/json" })
	public ResponseEntity<List<User>> getAll() {
		slf4jLogger.info("getAll");
		return new ResponseEntity<>(userRepository.getAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/get/{id}", produces = { "application/json" })
	public ResponseEntity<User> get(@PathVariable(name = "id") Long id) {
		User founded = userRepository.getOne(id);
		slf4jLogger.info("founded " + founded);
		return new ResponseEntity<>(founded, HttpStatus.OK);
	}
}
