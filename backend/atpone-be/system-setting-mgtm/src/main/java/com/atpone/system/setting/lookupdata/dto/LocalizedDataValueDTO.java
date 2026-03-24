package com.atpone.system.setting.lookupdata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocalizedDataValueDTO {

	private Integer localeCode;
	private String langCode;
	private String localeName;
}
