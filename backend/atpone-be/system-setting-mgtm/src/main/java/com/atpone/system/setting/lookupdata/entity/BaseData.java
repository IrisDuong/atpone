package com.atpone.system.setting.lookupdata.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.atpone.system.setting.enums.ActiveStatus;
import com.atpone.system.setting.enums.CodeType;
import com.atpone.system.setting.enums.UsageStatus;
import com.atpone.utils.auditing.BaseAuditingEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public abstract class BaseData extends BaseAuditingEntity{

	protected String sysFeatureCode;

	@Enumerated(EnumType.STRING)
	protected CodeType codeType;
	
	@Enumerated(EnumType.STRING)
	protected UsageStatus usageStatus;
	
	@Enumerated(EnumType.STRING)
	protected ActiveStatus activeStatus;
	
	@Column(name = "localized_code_no", nullable = false)
	protected Integer localeCodeNo;
}
