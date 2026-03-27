package com.atpone.system.setting.lookupdata.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atpone.system.setting.lookupdata.dto.LocalizedDataValueDTO;
import com.atpone.system.setting.lookupdata.entity.LocalizedDataValue;
import com.atpone.system.setting.lookupdata.repo.LocalizedDataValueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalizedDataValueServiceImpl implements LocalizedDataValueService{

	private final LocalizedDataValueRepository localizedDataValueRepository;

	@Override
	public List<LocalizedDataValueDTO> findByLocaleCode(Integer localeCode) {
		List<LocalizedDataValue> result = localizedDataValueRepository.findById_LocaleCode(localeCode);
		List<LocalizedDataValueDTO> localizedDataValueDTOs = result.stream()
				.map(this::convertToDto).toList();
		return localizedDataValueDTOs;
	}

	@Override
	public Integer findMaxLocaleCode() {
		return localizedDataValueRepository.findMaxLocaleCode();
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void createByList(List<LocalizedDataValueDTO> dtos) {
		try {
			List<LocalizedDataValue> paramsToSave = dtos.stream()
					.map(this::convertToEntity).toList();
			localizedDataValueRepository.saveAllAndFlush(paramsToSave);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public List<LocalizedDataValueDTO> findByListLocaleCodes(List<Integer> listLocaleCodes) {
		List<LocalizedDataValue> result = localizedDataValueRepository.findById_LocaleCodeIn(listLocaleCodes);
		List<LocalizedDataValueDTO> listLocalizedDataValueDTOs = result
				.stream().map(this::convertToDto).toList();
		return listLocalizedDataValueDTOs;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void deleteById(Integer localeCode) {
		localizedDataValueRepository.deleteByLocaleCode(localeCode);
	}

	
}
