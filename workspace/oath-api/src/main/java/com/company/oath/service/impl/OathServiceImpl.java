package com.company.oath.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.oath.service.OathService;
import com.company.oath.service.model.entities.Oath;
import com.company.oath.service.repository.OathRepository;

/**
 *
 * @author bankaya
 */
@Service
public class OathServiceImpl implements OathService {
	@Autowired
	private OathRepository oathRepository;

	@Override
	public List<Oath> getAll() {
		List<Oath> oaths = oathRepository.findAll();

		return oaths;
	}


	@Override
	public Oath save(Oath oath) {
		oath = oathRepository.save(oath);

		return oath;

	}

	@Override
	public Oath update(Oath oath) {
		oath = oathRepository.save(oath);

		return oath;

	}

	@Override
	public Oath delete(Integer id) {
		Oath oath = getById(id);

		List<Oath > oathList = new ArrayList<>();

		oathList.add(oath);
		oathRepository.deleteInBatch(oathList);

		return oath;
	}
	@Override
	public Oath getById(Integer id) {
		Optional<Oath> domain = oathRepository.findById(id);
		if (domain.isPresent()) {
			return domain.get();
		}
		return null;
	}
}