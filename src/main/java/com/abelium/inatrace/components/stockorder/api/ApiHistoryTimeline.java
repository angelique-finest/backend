package com.abelium.inatrace.components.stockorder.api;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * API model for QR tag history timeline (used in public B2C page). Holds only the public data for Stock order history.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public class ApiHistoryTimeline {

	@ApiModelProperty(value = "The items in the history timeline")
	private List<ApiHistoryTimelineItem> items;

	public List<ApiHistoryTimelineItem> getItems() {
		if (items == null) {
			items = new ArrayList<>();
		}
		return items;
	}

	public void setItems(List<ApiHistoryTimelineItem> items) {
		this.items = items;
	}

}
