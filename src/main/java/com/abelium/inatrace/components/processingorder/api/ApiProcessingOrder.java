package com.abelium.inatrace.components.processingorder.api;

import com.abelium.inatrace.api.ApiBaseEntity;
import com.abelium.inatrace.components.processingaction.api.ApiProcessingAction;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.components.transaction.api.ApiTransaction;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApiProcessingOrder extends ApiBaseEntity {

    @ApiModelProperty(value = "Initiator user ID")
    public Long initiatorUserId;

    @ApiModelProperty(value = "Timestamp indicates when processing order have been created")
    private Instant creationTimestamp;

    @ApiModelProperty(value = "Processing action")
    public ApiProcessingAction processingAction;

    @ApiModelProperty(value = "Processing date")
    public LocalDate processingDate;

    @ApiModelProperty(value = "Input transactions")
    public List<ApiTransaction> inputTransactions;

    // Output transactions are not stored in DB,
    // is only mapped when needed, for ex. in aggregations - order history.
    // also outputTransactions[0].targetStockOrder is set, enabling link to next order
    @ApiModelProperty(value = "Output transactions")
    public List<ApiTransaction> outputTransactions;

    @ApiModelProperty(value = "Target stock orders")
    public List<ApiStockOrder> targetStockOrders;

    public Long getInitiatorUserId() {
        return initiatorUserId;
    }

    public void setInitiatorUserId(Long initiatorUserId) {
        this.initiatorUserId = initiatorUserId;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public ApiProcessingAction getProcessingAction() {
        return processingAction;
    }

    public void setProcessingAction(ApiProcessingAction processingAction) {
        this.processingAction = processingAction;
    }

    public LocalDate getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(LocalDate processingDate) {
        this.processingDate = processingDate;
    }

    public List<ApiTransaction> getInputTransactions() {
        if (inputTransactions == null) {
            inputTransactions = new ArrayList<>();
        }
        return inputTransactions;
    }

    public void setInputTransactions(List<ApiTransaction> inputTransactions) {
        this.inputTransactions = inputTransactions;
    }

    public List<ApiTransaction> getOutputTransactions() {
        if (outputTransactions == null) {
            outputTransactions = new ArrayList<>();
        }
        return outputTransactions;
    }

    public void setOutputTransactions(List<ApiTransaction> outputTransactions) {
        this.outputTransactions = outputTransactions;
    }

    public List<ApiStockOrder> getTargetStockOrders() {
        if (targetStockOrders == null) {
            targetStockOrders = new ArrayList<>();
        }
        return targetStockOrders;
    }

    public void setTargetStockOrders(List<ApiStockOrder> targetStockOrders) {
        this.targetStockOrders = targetStockOrders;
    }
}
