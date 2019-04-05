
package com.erio.erioBank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erio.erioBank.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
