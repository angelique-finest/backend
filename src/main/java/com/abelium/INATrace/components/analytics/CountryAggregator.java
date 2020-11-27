package com.abelium.INATrace.components.analytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abelium.INATrace.components.analytics.AnalyticsQueries.AggregateUpdate;
import com.abelium.INATrace.db.entities.RequestLog;

public class CountryAggregator extends Aggregator {

	private Map<String, Map<String, Integer>> resultCountry = new HashMap<>();
	
	@Override
	public void update(RequestLog rl) {
		String country = "unknown";
		if (rl.getGeoLocation() != null && rl.getGeoLocation().getCountry() != null) {
			country = rl.getGeoLocation().getCountry();
		}
		if (!resultCountry.containsKey(rl.getLogKey())) {
			resultCountry.put(rl.getLogKey(), new HashMap<>());
		}
		resultCountry.get(rl.getLogKey()).compute(country, (k, v) -> v == null ? 1 : v + 1);

	}
	
	@Override
	public List<AggregateUpdate> getUpdaters(AnalyticsQueries analyticsQueries, String keyPrefix) {
		return List.of(analyticsQueries.updaterForList(keyPrefix + "Country", resultCountry));
	}	
}
