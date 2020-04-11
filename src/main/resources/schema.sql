
 CREATE TABLE IF NOT EXISTS test (
  id INT(11) AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  career VARCHAR(250) DEFAULT NULL
);

 
CREATE TABLE IF NOT EXISTS user (
  id INT(11) AUTO_INCREMENT  PRIMARY KEY,
  user_name VARCHAR(50) NOT NULL,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  email VARCHAR(250) DEFAULT NULL,
  account_id INT(11),
  birth_day DATE DEFAULT NULL
  
);


CREATE TABLE IF NOT EXISTS type_of_account (
  id INT(11) AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(50) NOT NULL
  
);

CREATE TABLE IF NOT EXISTS account (
  account_number INT(11) PRIMARY KEY,
  amount NUMERIC NOT NULL DEFAULT 0,
  type_of_account_id INT(11)
  
);



ALTER TABLE `user`
ADD CONSTRAINT `user_account`
FOREIGN KEY (`account_id`)
REFERENCES `account` (`account_number`)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE `account`
ADD CONSTRAINT `account_type`
FOREIGN KEY (`type_of_account_id`)
REFERENCES `type_of_account` (`id`)
ON DELETE NO ACTION
ON UPDATE NO ACTION;




