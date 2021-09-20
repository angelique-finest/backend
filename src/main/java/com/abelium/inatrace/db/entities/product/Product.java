package com.abelium.inatrace.db.entities.product;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.db.entities.common.UserCustomer;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.value_chain.ValueChain;
import com.abelium.inatrace.types.ProductStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = { @Index(columnList = "name") })
public class Product extends ProductContent {
	
	@Version
	private long entityVersion;
	
	/**
	 * product status
	 */
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = Lengths.ENUM)
    private ProductStatus status = ProductStatus.DISABLED;
	
	/**
	 * origin - farmer location - Use the pins on the map to mark locations of farmers or suppliers and 
	 * indicate the number of farmers or suppliers you work with directly or indirectly at each location. 
	 */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductLocation> originLocations = new ArrayList<>(0);
	
	/**
	 * company - owner
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;

	/**
	 * value chain
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private ValueChain valueChain;

	/**
	 * a list of other companies associated with this product (buyers, producers, ...) 
	 */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductCompany> associatedCompanies = new ArrayList<>();

	/**
	 * a list of "collectors" 
	 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private List<UserCustomer> collectors = new ArrayList<>();
    
	/**
	 * labels
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private List<ProductLabel> labels = new ArrayList<>();

	public List<ProductLocation> getOriginLocations() {
		return originLocations;
	}

	public void setOriginLocations(List<ProductLocation> originLocations) {
		this.originLocations = originLocations;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ValueChain getValueChain() {
		return valueChain;
	}

	public void setValueChain(ValueChain valueChain) {
		this.valueChain = valueChain;
	}

	public List<ProductCompany> getAssociatedCompanies() {
		return associatedCompanies;
	}

	public void setAssociatedCompanies(List<ProductCompany> associatedCompanies) {
		this.associatedCompanies = associatedCompanies;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	public List<ProductLabel> getLabels() {
		return labels;
	}

	public void setLabels(List<ProductLabel> labels) {
		this.labels = labels;
	}

	public List<UserCustomer> getCollectors() {
		return collectors;
	}

	public void setCollectors(List<UserCustomer> collectors) {
		this.collectors = collectors;
	}
}
