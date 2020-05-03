package ci.pabeu.test.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.document.DateTools;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager em;

	private static final Logger slf4jLogger = LoggerFactory.getLogger(UserController.class);

	@PostMapping(value = "/create", consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<User> create(@RequestBody UserDto userDto) throws ParseException {
		slf4jLogger.info("create");

		User user = new User();
		user.setUserName(userDto.getUsername());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		if (!StringUtils.isEmpty(userDto.getBirthDay())) {
			user.setBirthDay(new SimpleDateFormat("yyyy-MM-dd").parse(userDto.getBirthDay()));
		}

		user = userRepository.save(user);

		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@PutMapping(value = "/{id}", consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<User> update(@RequestBody UserDto dto, @PathVariable(name = "id") Long id)
			throws ParseException {
		slf4jLogger.info("update");
		User founded = userRepository.getOne(id);

		if (!StringUtils.isEmpty(dto.getEmail())) {
			founded.setEmail(dto.getEmail());
		}
		if (!StringUtils.isEmpty(dto.getFirstName())) {
			founded.setFirstName(dto.getFirstName());
		}
		if (!StringUtils.isEmpty(dto.getLastName())) {
			founded.setLastName(dto.getLastName());
		}

		if (!StringUtils.isEmpty(dto.getBirthDay())) {
			founded.setBirthDay(new SimpleDateFormat("yyyy-MM-dd").parse(dto.getBirthDay()));
		}
		founded = userRepository.save(founded);

		return ResponseEntity.status(HttpStatus.OK).body(founded);
	}

	@DeleteMapping(value = "/{id}", produces = { "application/json" })
	public void delete(@PathVariable(name = "id") Long id) {
		slf4jLogger.info("delete");
		User founded = userRepository.getOne(id);
		userRepository.delete(founded);

	}

	@GetMapping(value = "/", produces = { "application/json" })
	public ResponseEntity<List<User>> getAll() {
		slf4jLogger.info("getAll");
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.getAll());
	}

	@GetMapping(value = "/{id}", produces = { "application/json" })
	public ResponseEntity<User> get(@PathVariable(name = "id") Long id) {
		User founded = userRepository.getOne(id);
		slf4jLogger.info("founded " + founded);
		return new ResponseEntity<>(founded, HttpStatus.OK);
	}

	@PostMapping(value = "/search", consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<List<User>> search(@RequestBody UserDto dto) throws ParseException, InterruptedException {
		slf4jLogger.info("search");

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
		fullTextEntityManager.createIndexer().startAndWait();

		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(User.class)
				.get();

		BooleanJunction<BooleanJunction> boolQuery = queryBuilder.bool();

		if (!StringUtils.isEmpty(dto.getLastName())) {
			boolQuery.should(queryBuilder.phrase().onField("lastName").sentence(dto.getLastName()).createQuery());
		}
		if (!StringUtils.isEmpty(dto.getFirstName())) {
			boolQuery.should(queryBuilder.phrase().onField("firstName").sentence(dto.getFirstName()).createQuery());
		}
		
		if (!StringUtils.isEmpty(dto.getBirthDay())) {
			boolQuery.should(
					queryBuilder
					.range()
            .onField( "startTS" ).ignoreFieldBridge()
							.from(DateTools.dateToString(new SimpleDateFormat("dd/MM/yyyy").parse(dto.getBirthDay()),
									DateTools.Resolution.MILLISECOND))
							.to(DateTools.dateToString(new SimpleDateFormat("dd/MM/yyyy").parse(dto.getBirthDay()),
									DateTools.Resolution.MILLISECOND))
							.excludeLimit()
							.createQuery());
		}

		if (dto.getAccountId() != null && dto.getAccountId() > 0) {
			boolQuery.must(queryBuilder.keyword().onField("account.id").matching(dto.getAccountId()).createQuery());
		}

		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager
				.createFullTextQuery(boolQuery.createQuery(), User.class);

		List<User> users = jpaQuery.getResultList();


		return ResponseEntity.status(HttpStatus.CREATED).body(users);
	}
}
