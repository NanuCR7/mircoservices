package com.easzybites.accounts.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter @Setter @ToString	
public class BaseEntity {
	
	@Column(updatable = false)
	private LocalDateTime createdAt;
	@Column(updatable = false)
	private String createdBt;
	@Column(insertable = false)
	private LocalDateTime updatedAt;
	@Column(insertable = false)
	private String updatedby;

}
