package com.atpone.system.setting.lookupdata.service;

import java.util.List;

import com.atpone.system.setting.lookupdata.dto.LocalizedDataValueDTO;
import com.atpone.system.setting.lookupdata.entity.LocalizedDataValue;
import com.atpone.system.setting.lookupdata.entity.LocalizedDataValuePK;
import com.atpone.utils.service.DataConverter;	

public interface LocalizedDataValueService extends DataConverter<LocalizedDataValue, LocalizedDataValueDTO>{

	void createByList(List<LocalizedDataValueDTO> dtos);
	List<LocalizedDataValueDTO> findByLocaleCode(Integer localeCode);
	List<LocalizedDataValueDTO> findByListLocaleCodes(List<Integer> listLocaleCodes);
	Integer findMaxLocaleCode();
	void deleteById(Integer localeCode);
	
	@Override
	default LocalizedDataValueDTO convertToDto(LocalizedDataValue e) {
		return LocalizedDataValueDTO.builder()
				.localeCode(e.getId().getLocaleCode())
				.langCode(e.getId().getLangCode())
				.localeName(e.getLocaleName())
				.build();
	}
	@Override
	default LocalizedDataValue convertToEntity(LocalizedDataValueDTO d) {
		return LocalizedDataValue.builder()
				.id(LocalizedDataValuePK.builder()
						.langCode(d.getLangCode())
						.localeCode(d.getLocaleCode())
						.build()
				)
				.localeName(d.getLocaleName())
				.build();
	}
	
	
}
