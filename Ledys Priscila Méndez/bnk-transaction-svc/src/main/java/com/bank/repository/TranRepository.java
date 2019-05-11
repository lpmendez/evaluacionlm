package com.bank.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bank.entity.BnkTraTransaction;

public interface TranRepository extends CrudRepository<BnkTraTransaction, Integer> {
	@Query("select tra from "
			+ "	BnkTraTransaction tra "
			+ "	where tra.traUsrcod = :user "
			+ "	and tra.traAccid = :accid "
			+ " and tra.traCreatedDate >= :start "
			+ " and tra.traCreatedDate <= :end ")
	List<BnkTraTransaction> findBy(@Param("user") String user,
									@Param("accid") String accid,
									@Param("start") Date start,
									@Param("end") Date end);
}
