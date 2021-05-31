package com.yash.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yash.shopping.entity.User;

/**
 * @author yash.ghawghawe
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	/**
	 * @param username
	 * @param password
	 * @return User
	 */
	User findByUsernameAndPassword(String username, String password);

}
