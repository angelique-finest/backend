package com.abelium.INATrace.api.errors;

import com.abelium.INATrace.api.ApiResponse;
import com.abelium.INATrace.api.ApiStatus;
import com.abelium.INATrace.api.errors.validation.ApiValidationErrorDetails;

/**
 * Default API error response body.
 */
public class ApiError extends ApiResponse<Void>
{
    /**
     * Only for deserialization.
     */
    protected ApiError() {
        super(null, null);
    }
    
    public ApiError(ApiStatus status, String errorMessage) {
        super(status, errorMessage);
    }

    public ApiError(ApiStatus status, String errorMessage, ApiValidationErrorDetails validationErrorDetails, String errorDetails) {
        super(status, errorMessage, validationErrorDetails, errorDetails);
    }
}
