package com.atpone.system.setting.lookupdata.dto;

import java.util.List;

import com.atpone.system.setting.enums.CodeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LookupTypeRequestDTO {
	private Integer id;
	private String lookupTypeCode;
	private String lookupTypeName;
	private List<LocalizedDataValueDTO> localizedDataValueDTOs;
	private String sysFeatureCode;
	private Integer codeTypeValue;
	private Integer activeStatusValue;
	private Boolean usagedStatusValue;
}
