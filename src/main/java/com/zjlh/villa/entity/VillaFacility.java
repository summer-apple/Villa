package com.zjlh.villa.entity;

// Generated 2015-8-5 20:46:39 by Hibernate Tools 4.3.1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * VillaFacility generated by hbm2java
 */
@Entity
@Table(name = "villa_facility", catalog = "villa")
public class VillaFacility implements java.io.Serializable {

	private VillaFacilityId id;

	public VillaFacility() {
	}

	public VillaFacility(VillaFacilityId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "villa", column = @Column(name = "villa", nullable = false)),
			@AttributeOverride(name = "facility", column = @Column(name = "facility", nullable = false)) })
	public VillaFacilityId getId() {
		return this.id;
	}

	public void setId(VillaFacilityId id) {
		this.id = id;
	}

}
