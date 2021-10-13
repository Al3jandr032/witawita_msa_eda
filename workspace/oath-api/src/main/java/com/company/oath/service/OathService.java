package com.company.oath.service;

import java.util.List;

import com.company.oath.service.model.entities.Oath;

/**
 *
 * @author bankaya
 *
 */
public interface OathService {
	List<Oath> getAll();

	Oath save(Oath oath);

	Oath update(Oath oath);

	Oath delete(Integer id);

	Oath getById(Integer id);
}