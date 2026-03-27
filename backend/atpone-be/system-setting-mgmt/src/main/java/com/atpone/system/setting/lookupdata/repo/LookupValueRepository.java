package com.atpone.system.setting.lookupdata.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atpone.system.setting.lookupdata.entity.LookupValue;

public interface LookupValueRepository extends JpaRepository<LookupValue, Integer>{

}
