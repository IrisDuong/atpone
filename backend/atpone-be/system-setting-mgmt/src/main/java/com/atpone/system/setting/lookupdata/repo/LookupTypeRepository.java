package com.atpone.system.setting.lookupdata.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.atpone.system.setting.lookupdata.entity.LookupType;

public interface LookupTypeRepository extends JpaRepository<LookupType, Integer>, JpaSpecificationExecutor<LookupType>{

}
