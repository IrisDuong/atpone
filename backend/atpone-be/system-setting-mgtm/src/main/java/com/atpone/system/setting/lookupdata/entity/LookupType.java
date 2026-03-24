package com.atpone.system.setting.lookupdata.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "SYA_LOOKUP_TYPE")
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class LookupType extends BaseData{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "lookup_type_code", unique = true, nullable = false)
	private String lookupTypeCode;
	
	@OneToMany(mappedBy = "lookupType", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LookupValue> lookupValues;
}
