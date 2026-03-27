package com.atpone.system.setting.lookupdata.service;

import java.util.List;

import com.atpone.system.setting.lookupdata.dto.LookupTypeRequestDTO;
import com.atpone.system.setting.lookupdata.dto.LookupTypeResponseDTO;
import com.atpone.system.setting.lookupdata.entity.LookupType;
import com.atpone.utils.service.DataConverter;

public interface LookupTypeService extends DataConverter<LookupType, LookupTypeResponseDTO>{

	Boolean create(LookupTypeRequestDTO dto);
	LookupTypeResponseDTO getLookupTypeDetail(Integer id);
	List<LookupTypeResponseDTO> searchLookupTypes(LookupTypeRequestDTO dto);
	void deleteById(Integer lookupTypeId, Integer localeCode);
	
	@Override
	default LookupTypeResponseDTO convertToDto(LookupType e) {
		LookupTypeResponseDTO dto = LookupTypeResponseDTO.builder()
				.id(e.getId())
				.lookupTypeCode(e.getLookupTypeCode())
				.sysFeatureCode(e.getSysFeatureCode())
				.codeType(e.getCodeType())
				.activeStatus(e.getActiveStatus())
				.usagedStatus(e.getUsageStatus())
				.build();
		return dto;
	}
	@Override
	default LookupType convertToEntity(LookupTypeResponseDTO d) {
		return null;
	}
	
	
}
