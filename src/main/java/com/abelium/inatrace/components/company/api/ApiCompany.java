package com.abelium.inatrace.components.company.api;

import com.abelium.inatrace.components.codebook.currencies.api.ApiCurrencyType;
import com.abelium.inatrace.components.common.api.ApiCertification;
import com.abelium.inatrace.components.common.api.ApiDocument;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public class ApiCompany extends ApiCompanyBase {

	@ApiModelProperty(value = "high-resolution logo of the company (jpeg, jpg, png)", position = 3)
	public ApiDocument logo;
	
	@Length(max = 2000)
	@ApiModelProperty(value = "interview", position = 11)
	public String interview;

	@ApiModelProperty(value = "company documents", position = 12)
	@Valid
	public List<ApiCompanyDocument> documents;	

	@ApiModelProperty(value = "company certifications", position = 13)
	@Valid
	public List<ApiCertification> certifications;

	@ApiModelProperty(value = "Preferred currency of the company")
	@Valid
	public ApiCurrencyType currency;
	
	public ApiDocument getLogo() {
		return logo;
	}

	public void setLogo(ApiDocument logo) {
		this.logo = logo;
	}

	public String getInterview() {
		return interview;
	}

	public void setInterview(String interview) {
		this.interview = interview;
	}

	public List<ApiCompanyDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<ApiCompanyDocument> documents) {
		this.documents = documents;
	}

	public List<ApiCertification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<ApiCertification> certifications) {
		this.certifications = certifications;
	}

	public ApiCurrencyType getCurrency() {
		return currency;
	}

	public void setCurrency(ApiCurrencyType currency) {
		this.currency = currency;
	}
}
