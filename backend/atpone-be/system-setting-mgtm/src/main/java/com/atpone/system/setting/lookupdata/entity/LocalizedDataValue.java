package com.atpone.system.setting.lookupdata.entity;

import com.atpone.utils.auditing.BaseAuditingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "SYA_LOCALIZED_DATA_VALUE")
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class LocalizedDataValue extends BaseAuditingEntity{

	@EmbeddedId
	private LocalizedDataValuePK id;
	
	@Column(name = "locale_name", columnDefinition = "NVARCHAR(255)")
	private String localeName;
}
