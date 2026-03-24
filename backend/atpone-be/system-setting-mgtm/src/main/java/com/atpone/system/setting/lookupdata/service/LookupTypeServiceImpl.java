package com.atpone.system.setting.lookupdata.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atpone.system.setting.enums.ActiveStatus;
import com.atpone.system.setting.enums.CodeType;
import com.atpone.system.setting.enums.UsageStatus;
import com.atpone.system.setting.lookupdata.dto.LocalizedDataValueDTO;
import com.atpone.system.setting.lookupdata.dto.LookupTypeRequestDTO;
import com.atpone.system.setting.lookupdata.dto.LookupTypeResponseDTO;
import com.atpone.system.setting.lookupdata.entity.LookupType;
import com.atpone.system.setting.lookupdata.repo.LookupTypeRepository;
import com.atpone.utils.exception.InternalServerErrorException;
import com.atpone.utils.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LookupTypeServiceImpl implements LookupTypeService{

	private final LookupTypeRepository lookupTypeRepository;
	private final LocalizedDataValueService localizedDataValueService;

	@Override
	@Transactional
	public Boolean create(LookupTypeRequestDTO dto) {
		try {

			// find max lcoale code and save list of new localized data value
			Integer maxLocaleCode = localizedDataValueService.findMaxLocaleCode();
			Integer newLocaleCode = new AtomicInteger(maxLocaleCode).incrementAndGet();
			dto.getLocalizedDataValueDTOs()
					.stream().forEach(item->item.setLocaleCode(newLocaleCode));
			localizedDataValueService.createByist(dto.getLocalizedDataValueDTOs());
			
			LookupType loookupType = LookupType.builder()
					.lookupTypeCode(dto.getLookupTypeCode())
					.codeType(CodeType.getByValue(dto.getCodeTypeValue()))
					.usageStatus(UsageStatus.USED)
					.activeStatus(ActiveStatus.ACTIVE)
					.localeCodeNo(newLocaleCode)
					.sysFeatureCode(dto.getSysFeatureCode())
					.build();
			lookupTypeRepository.save(loookupType);
			return true;
		} catch (DataIntegrityViolationException e) {
			throw new InternalServerErrorException(e.getMessage());
		}
		
	}

	@Override
	public LookupTypeResponseDTO getLookupTypeDetail(Integer id) {
		LookupType result = lookupTypeRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("This Lookup Type does not exist"));
		List<LocalizedDataValueDTO> listLocalizedDataValueDTOs = localizedDataValueService.findByLocaleCode(result.getLocaleCodeNo());
		LookupTypeResponseDTO lookupTypeResponseDTO = this.convertToDto(result);
		lookupTypeResponseDTO.setLocalizedDataValueDTOs(listLocalizedDataValueDTOs);
		return lookupTypeResponseDTO;
	}
}
