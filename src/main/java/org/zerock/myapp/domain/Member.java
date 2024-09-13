package org.zerock.myapp.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.generator.EventType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.myapp.common.CommonEntityLifecycleListener;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


//Because of a super class (org.zerock.myapp.common.CommonEntityCallbacks)
//@EqualsAndHashCode(callSuper=false)
@Data

/**
 * -------------------------------
 * Hibernate Annotatios for Generating Dynamic SQLs
 * -------------------------------
 * NOT standard JPA.
 * Default JPA generates Prepared SQLs.
 * 	If using the below annotations, JPA generates Dynamic SQLs.
 * 
 * 	@DynamicInsert 					// Dynamic SQLs auto-generation with `only non-null` columns
 * 	@DynamicUpdate				// Dynamic SQLs auto-generation with `only changed` columns
 */

@EntityListeners({ CommonEntityLifecycleListener.class, AuditingEntityListener.class })

@Entity(name = "Member")
@Table(name = "member")
public class Member implements Serializable {
	@Serial private static final long serialVersionUID = 1L;

	// 1. Set PK, Auto-Generation.
	@Id
	
//	@SequenceGenerator(name = "seq_member", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name="member_id", updatable=false)
	private Long id;
	
	
	// 2. Additional Fields.
	@Basic(optional = false)				// Not Null Constraint
	private String name;
	
	@Basic(optional = false)				// Not Null Constraint
	private String password;
	
	@Basic(optional = false)				// Not Null Constraint
	private String role;
	
	
	// 3. JPA Auditing.
//	@CreatedDate										// Jpa Audit
//	@CreationTimestamp
	
	@CurrentTimestamp(event = EventType.INSERT)			// Auto-Generation
	@Column(nullable = false, updatable = false)		// Not Null Constraint, Not Updatable.
	private Date createDate;

	
//	@LastModifiedDate									// Jpa Audit
//	@UpdateTimestamp
	
	@CurrentTimestamp(event = EventType.UPDATE)			// Auto-Generation
	@Basic(optional = true)								// Null Constraint, Updatable.
	private Date updateDate;
	
	
/**
  * ----------------------------------
  * 4. Entity Lifecyle Annotations
  * ----------------------------------
  * *Important: Callback methods annotated on the bean class must (1) return void (2) take no arguments

	@PrePersist 	void prePersist() 	{ log.debug("(C-1) prePersist() invoked."); }
	@PostPersist	void postPersist() 	{ log.debug("(C-2) prePersist() invoked."); }
	@PostLoad 		void postLoad() 	{ log.debug("(R) postLoad() invoked."); }
	@PreUpdate		void preUpdate() 	{ log.debug("(U-1) preUpdate() invoked."); }
	@PostUpdate		void postUpdate() 	{ log.debug("(U-2) postUpdate() invoked."); }
	@PreRemove		void preRemove() 	{ log.debug("(D-1) preRemove() invoked."); }
	@PostRemove	void postRemove() { log.debug("(D-2) postRemove() invoked."); }
  */

	
} // end class


