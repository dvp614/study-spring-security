package org.zerock.myapp.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.generator.EventType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.myapp.common.CommonEntityLifecycleListener;

import jakarta.persistence.Basic;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;


@Data

@EntityListeners({ CommonEntityLifecycleListener.class, AuditingEntityListener.class })

@MappedSuperclass
public abstract class JpaAudit implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	
	// -----------------------------
	// JPA Auditing Fields
	// -----------------------------

//	@CreatedDate
//	@CreationTimestamp
	
	@CurrentTimestamp(event = EventType.INSERT)
	@Basic(optional = false)
	protected Date createDate;

//	@LastModifiedDate
//	@UpdateTimestamp
	
	@CurrentTimestamp(event = EventType.UPDATE)
	@Basic(optional = true)
	protected Date updateDate;
	
	
} // end class

