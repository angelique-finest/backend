package com.abelium.inatrace.components.facility.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.codebook.facility_type.api.ApiFacilityType;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.components.company.api.ApiCompanyBase;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ApiFacility extends ApiBaseEntity {

	@ApiModelProperty(value = "Facility name")
	private String name;

	@ApiModelProperty(value = "Is collection facility")
	private Boolean isCollectionFacility;

	@ApiModelProperty(value = "Is public facility")
	private Boolean isPublic;

	@ApiModelProperty(value = "Enable form control 'May involve collectors'")
	private Boolean displayMayInvolveCollectors;

	@ApiModelProperty(value = "Enable form control 'Organic'")
	private Boolean displayOrganic;

	@ApiModelProperty(value = "Enable form control 'Price deduction damage'")
	private Boolean displayPriceDeductionDamage;

	@ApiModelProperty(value = "Enable form control 'Tare'")
	private Boolean displayTare;

	@ApiModelProperty(value = "Enable form control 'Women only'")
	private Boolean displayWomenOnly;

	@ApiModelProperty(value = "Facility location")
	private ApiFacilityLocation facilityLocation;

	@ApiModelProperty(value = "Facility company")
	private ApiCompanyBase company;

	@ApiModelProperty(value = "Facility type")
	private ApiFacilityType facilityType;

	@ApiModelProperty(value = "List of semi product ID's for this facility")
	private List<ApiSemiProduct> facilitySemiProductList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsCollectionFacility() {
		return isCollectionFacility;
	}

	public void setIsCollectionFacility(Boolean isCollectionFacility) {
		this.isCollectionFacility = isCollectionFacility;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Boolean getDisplayMayInvolveCollectors() {
		return displayMayInvolveCollectors;
	}

	public void setDisplayMayInvolveCollectors(Boolean displayMayInvolveCollectors) {
		this.displayMayInvolveCollectors = displayMayInvolveCollectors;
	}

	public Boolean getDisplayOrganic() {
		return displayOrganic;
	}

	public void setDisplayOrganic(Boolean displayOrganic) {
		this.displayOrganic = displayOrganic;
	}

	public Boolean getDisplayPriceDeductionDamage() {
		return displayPriceDeductionDamage;
	}

	public void setDisplayPriceDeductionDamage(Boolean displayPriceDeductionDamage) {
		this.displayPriceDeductionDamage = displayPriceDeductionDamage;
	}

	public Boolean getDisplayTare() {
		return displayTare;
	}

	public void setDisplayTare(Boolean displayTare) {
		this.displayTare = displayTare;
	}

	public Boolean getDisplayWomenOnly() {
		return displayWomenOnly;
	}

	public void setDisplayWomenOnly(Boolean displayWomenOnly) {
		this.displayWomenOnly = displayWomenOnly;
	}

	public ApiFacilityLocation getFacilityLocation() {
		return facilityLocation;
	}

	public void setFacilityLocation(ApiFacilityLocation facilityLocation) {
		this.facilityLocation = facilityLocation;
	}

	public ApiCompanyBase getCompany() {
		return company;
	}

	public void setCompany(ApiCompanyBase company) {
		this.company = company;
	}

	public ApiFacilityType getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(ApiFacilityType facilityType) {
		this.facilityType = facilityType;
	}

	public List<ApiSemiProduct> getFacilitySemiProductList() {
		return facilitySemiProductList;
	}

	public void setFacilitySemiProductList(List<ApiSemiProduct> facilitySemiProductList) {
		this.facilitySemiProductList = facilitySemiProductList;
	}

	public ApiFacility() {
		super();
	}

	public ApiFacility(String name, Boolean isCollectionFacility, Boolean isPublic,
			ApiFacilityLocation facilityLocation, ApiCompanyBase company, ApiFacilityType facilityType) {
		super();
		this.name = name;
		this.isCollectionFacility = isCollectionFacility;
		this.isPublic = isPublic;
		this.facilityLocation = facilityLocation;
		this.company = company;
		this.facilityType = facilityType;
	}

}
