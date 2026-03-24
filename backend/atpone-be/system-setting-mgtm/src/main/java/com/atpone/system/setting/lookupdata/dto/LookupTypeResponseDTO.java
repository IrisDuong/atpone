package com.atpone.system.setting.lookupdata.dto;

import java.util.List;

import com.atpone.system.setting.enums.ActiveStatus;
import com.atpone.system.setting.enums.CodeType;
import com.atpone.system.setting.enums.UsageStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LookupTypeResponseDTO {
	private Integer id;
	private String lookupTypeCode;
	private List<LocalizedDataValueDTO> localizedDataValueDTOs;
	private String sysFeatureCode;
	private CodeType codeType;
	private ActiveStatus activeStatus;
	private UsageStatus usagedStatus;
}
