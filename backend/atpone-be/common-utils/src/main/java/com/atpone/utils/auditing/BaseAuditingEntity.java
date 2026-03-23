package com.atpone.utils.auditing;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class BaseAuditingEntity {

	@CreatedBy
	private String createdBy;
	
	@CreatedDate
	private Instant createdAt;
	
	@LastModifiedBy
	private String updatedBy;
	
	@LastModifiedDate
	private Instant updatedAt;
}
