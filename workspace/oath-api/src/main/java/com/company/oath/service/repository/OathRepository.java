package com.company.oath.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.oath.service.model.entities.Oath;

/**
 *
 * @author bankaya
 *
 */
@Repository
public interface OathRepository  extends JpaRepository<Oath, Integer> {

}