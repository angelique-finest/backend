package com.abelium.inatrace.components.product.api;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;

@Validated
public class ApiProcess {

	@Size(max = 2000)
	@ApiModelProperty(value = "production description - Briefly describe your production process", position = 1)
	public String production;
	

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

}
