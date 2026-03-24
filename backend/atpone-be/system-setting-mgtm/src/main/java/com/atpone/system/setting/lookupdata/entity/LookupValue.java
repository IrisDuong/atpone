package com.atpone.system.setting.lookupdata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SYA_LOOKUP_VALUE"
	,uniqueConstraints = @UniqueConstraint(columnNames = {"lookup_type_code","lookup_value_code"})
)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LookupValue extends BaseData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "lookup_value_code", nullable = false)
	private String lookupValueCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lookup_type_code", nullable = false)
	private LookupType lookupType;
}
