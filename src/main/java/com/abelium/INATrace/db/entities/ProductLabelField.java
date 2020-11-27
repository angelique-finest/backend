package com.abelium.INATrace.db.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.abelium.INATrace.api.types.Lengths;

@Embeddable
public class ProductLabelField {
	
	/**
	 * Field name -- path to Product (sub)field
	 */
	@Column(length = Lengths.DEFAULT)
	private String name;
	
	/**
	 * Visible on FE
	 */
	@Column
	private Boolean visible;
	
	/**
	 * Section on FE
	 */
	@Column(length = Lengths.DEFAULT)
	private String section;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
}
