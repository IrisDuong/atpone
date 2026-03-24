package com.atpone.system.setting.lookupdata.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.atpone.system.setting.lookupdata.entity.LocalizedDataValue;
import com.atpone.system.setting.lookupdata.entity.LocalizedDataValuePK;

import jakarta.persistence.LockModeType;

public interface LocalizedDataValueRepository extends JpaRepository<LocalizedDataValue, LocalizedDataValuePK>{

	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("SELECT COALESCE(MAX(L.id.localeCode),0) FROM LocalizedDataValue L")
	Integer findMaxLocaleCode();
	
	List<LocalizedDataValue> findById_LocaleCode(Integer localeCode);
	List<LocalizedDataValue> findById_LocaleCodeIn(List<Integer> localeCodes);
}
