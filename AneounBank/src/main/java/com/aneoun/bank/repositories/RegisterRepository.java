
package com.aneoun.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aneoun.bank.domain.Register;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Integer> {

}
