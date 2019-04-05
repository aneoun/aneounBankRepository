
package com.erio.erioBank.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erio.erioBank.domain.Register;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Integer> {

	@Query("select r from Register r where r.id = ?1")
	Register findOne(int registerId);

	@Query("select r from Register r where r.status = 'INCOME'")
	List<Register> incommingRegiters();

	@Query("select r from Register r where r.status = 'OUTCOME'")
	List<Register> outcommingRegiters();

	@Query("select r from Register r where (r.moment => ?1) and (r.moment <= ?2)")
	List<Register> regitersBetweenDates(Date start, Date end);

}
