package com.atpone.system.setting.lookupdata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atpone.system.setting.enums.ActiveStatus;
import com.atpone.system.setting.enums.CodeType;
import com.atpone.system.setting.enums.UsageStatus;
import com.atpone.system.setting.lookupdata.dto.LocalizedDataValueDTO;
import com.atpone.system.setting.lookupdata.dto.LookupTypeRequestDTO;
import com.atpone.system.setting.lookupdata.dto.LookupTypeResponseDTO;
import com.atpone.system.setting.lookupdata.entity.LocalizedDataValue;
import com.atpone.system.setting.lookupdata.entity.LookupType;
import com.atpone.system.setting.lookupdata.repo.LookupTypeRepository;
import com.atpone.utils.exception.InternalServerErrorException;
import com.atpone.utils.exception.NotFoundException;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
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
			localizedDataValueService.createByList(dto.getLocalizedDataValueDTOs());
			
			LookupType loookupType = LookupType.builder()
					.lookupTypeCode(dto.getLookupTypeCode())
					.codeType(CodeType.getByValue(dto.getCodeTypeValue()))
					.usageStatus(UsageStatus.USED)
					.activeStatus(ActiveStatus.ACTIVE)
					.localeCode(newLocaleCode)
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
		List<LocalizedDataValueDTO> listLocalizedDataValueDTOs = localizedDataValueService.findByLocaleCode(result.getLocaleCode());
		LookupTypeResponseDTO lookupTypeResponseDTO = this.convertToDto(result);
		lookupTypeResponseDTO.setLocalizedDataValueDTOs(listLocalizedDataValueDTOs);
		return lookupTypeResponseDTO;
	}

	@Override
	public List<LookupTypeResponseDTO> searchLookupTypes(LookupTypeRequestDTO dto) {
		Specification<LookupType> specification = (root,query,cb)->{
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			Subquery<Integer> sbLocalizedDataValue = query.subquery(Integer.class);
			Root<LocalizedDataValue> rootLocalizedDataValue = sbLocalizedDataValue.from(LocalizedDataValue.class);
			sbLocalizedDataValue.select(rootLocalizedDataValue.get("id").get("localeCode"))
			.where(cb.like(rootLocalizedDataValue.get("localeName"), "%"+dto.getLookupTypeName()+"%"));
			
			predicates.add(cb.or(
					cb.isNotNull(root.get("lookupTypeCode")),
					cb.equal(root.get("lookupTypeCode"), dto.getLookupTypeCode()))
			);
			predicates.add(
					cb.equal(root.get("codeType"), CodeType.getByValue(dto.getCodeTypeValue()))
			);
			predicates.add(
					cb.equal(root.get("usageStatus"), UsageStatus.getByValue(dto.getUsagedStatusValue()))
			);
			predicates.add(
					cb.equal(root.get("activeStatus"), ActiveStatus.getByValue(dto.getActiveStatusValue()))
			);
			predicates.add(root.get("localeCode").in(sbLocalizedDataValue));
			
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		List<LookupType> dataResult = lookupTypeRepository.findAll(specification);
		
		// find localeized data value
		List<Integer> listLocaleCode = dataResult.stream().map(LookupType::getLocaleCode).toList();
		List<LocalizedDataValueDTO> listLocalizedDataValueDTOs = localizedDataValueService.findByListLocaleCodes(listLocaleCode);
		
		// return final result
		List<LookupTypeResponseDTO> finalResult = dataResult.stream()
				.map(entity->{
					List<LocalizedDataValueDTO> ownlistLocalizedDataValueDTOs = listLocalizedDataValueDTOs.stream()
							.filter(item-> entity.getLocaleCode().equals(item.getLocaleCode()))
							.toList();
					LookupTypeResponseDTO response = this.convertToDto(entity);
					response.setLocalizedDataValueDTOs(ownlistLocalizedDataValueDTOs);
					return response;
				}).toList();
		return finalResult;
	}

	@Override
	public void deleteById(Integer id) {
		try {
			lookupTypeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundException("Lookup Type does not exist");
		} catch (Exception e) {
			throw new InternalServerErrorException("Delete Lookup Type failed");
		}
	}
}
