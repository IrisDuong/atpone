package com.atpone.system.setting.lookupdata.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LocalizedDataValuePK implements Serializable{

	@Column(name = "locale_code")
	private Integer localeCode;
	
	@Column(name = "lang_code")
	private String langCode;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocalizedDataValuePK other = (LocalizedDataValuePK) obj;
		return Objects.equals(langCode, other.langCode) && Objects.equals(localeCode, other.localeCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(langCode, localeCode);
	}
	
}
