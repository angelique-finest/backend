package com.abelium.inatrace.components.codebook.semiproduct;

import com.abelium.inatrace.components.codebook.measure_unit_type.MeasureUnitTypeMapper;
import com.abelium.inatrace.components.codebook.semiproduct.api.ApiSemiProduct;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import com.abelium.inatrace.db.entities.codebook.SemiProductTranslation;
import com.abelium.inatrace.types.Language;

/**
 * Mapper for SemiProduct entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public final class SemiProductMapper {

	private SemiProductMapper() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Mapping ID and name to API entity
	 *
	 * @param entity DB entity
	 * @return API model entity with ID and name
	 */
	public static ApiSemiProduct toApiSemiProductIdName(SemiProduct entity) {

		ApiSemiProduct apiSemiProduct = new ApiSemiProduct();
		apiSemiProduct.setId(entity.getId());
		apiSemiProduct.setName(entity.getName());
		apiSemiProduct.setBuyable(entity.getBuyable());

		return apiSemiProduct;
	}

	/**
	 * Mapping the base entity attributes - no associations are included.
	 *
	 * @param entity DB entity.
	 * @return API model entity.
	 */
	public static ApiSemiProduct toApiSemiProductBase(SemiProduct entity, Language language) {
		if(entity == null) return null;

		SemiProductTranslation translation = entity.getSemiProductTranslations().stream().filter(semiProductTranslation -> semiProductTranslation.getLanguage().equals(language)).findFirst().orElse(new SemiProductTranslation());

		ApiSemiProduct apiSemiProduct = new ApiSemiProduct();
		apiSemiProduct.setId(entity.getId());
		apiSemiProduct.setName(translation.getName());
		apiSemiProduct.setDescription(translation.getDescription());

		return apiSemiProduct;
	}

	/**
	 * Mapping of the base attributes and all the associations.
	 *
	 * @param entity DB entity.
	 * @return API model entity.
	 */
	public static ApiSemiProduct toApiSemiProduct(SemiProduct entity, Language language) {
		if(entity == null) return null;

		ApiSemiProduct apiSemiProduct = SemiProductMapper.toApiSemiProductBase(entity, language);

		apiSemiProduct.setSKU(entity.getSKU());
		apiSemiProduct.setSKUEndCustomer(entity.getSKUEndCustomer());
		apiSemiProduct.setBuyable(entity.getBuyable());

		if (entity.getMeasurementUnitType() != null) {
			apiSemiProduct.setApiMeasureUnitType(
					MeasureUnitTypeMapper.toApiMeasureUnitType(entity.getMeasurementUnitType()));
		}

		return apiSemiProduct;
	}
}
