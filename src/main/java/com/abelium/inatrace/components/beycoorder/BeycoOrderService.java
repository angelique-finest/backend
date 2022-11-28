package com.abelium.inatrace.components.beycoorder;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.beycoorder.api.*;
import com.abelium.inatrace.components.common.BaseService;
import com.abelium.inatrace.components.company.CompanyQueries;
import com.abelium.inatrace.components.stockorder.StockOrderService;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.company.CompanyCertification;
import com.abelium.inatrace.db.entities.stockorder.StockOrder;
import com.abelium.inatrace.db.entities.stockorder.StockOrderPEFieldValue;
import com.abelium.inatrace.db.entities.stockorder.Transaction;
import com.abelium.inatrace.db.entities.stockorder.enums.TransactionStatus;
import com.abelium.inatrace.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Lazy
@Service
public class BeycoOrderService extends BaseService {

    @Value("${beyco.oauth2.clientId}")
    private String clientId;

    @Value("${beyco.oauth2.clientSecret}")
    private String clientSecret;

    @Value("${beyco.oauth2.url}")
    private String authUrl;

    @Value("${beyco.integration.url}")
    private String beycoUrl;

    private static final String GRADE_FIELD_NAME = "GRADE";
    private static final String SCREEN_SIZE_FIELD_NAME = "SCREEN_SIZE";
    private static final String CUPPING_SCORE_FIELD_NAME = "CUPPING_SCORE";
    private static final String BEYCO_ORDER_ENDPOINT = "/api/Offers";

    private final StockOrderService stockOrderService;
    private final CompanyQueries companyQueries;

    @Autowired
    public BeycoOrderService(
            StockOrderService stockOrderService,
            CompanyQueries companyQueries
    ) {
        this.stockOrderService = stockOrderService;
        this.companyQueries = companyQueries;
    }

    public ApiBeycoTokenResponse getBeycoAuthToken(String authCode, Long companyId) throws ApiException {
        Company company = companyQueries.fetchCompany(companyId);
        if (company.getAllowBeycoIntegration() == null || !company.getAllowBeycoIntegration()) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Company is not allowed to use Beyco platform");
        }

        RestTemplate restTemplate = new RestTemplate();
        ApiBeycoTokenRequest tokenRequest = new ApiBeycoTokenRequest();
        tokenRequest.setCode(authCode);
        tokenRequest.setClientSecret(this.clientSecret);
        tokenRequest.setClientId(this.clientId);
        tokenRequest.setGrantType(TokenGrantType.AuthorizationCode);

        ResponseEntity<ApiBeycoTokenResponse> response = restTemplate.exchange(this.authUrl, HttpMethod.POST,
                new HttpEntity<>(tokenRequest), ApiBeycoTokenResponse.class);
        return response.getBody();
    }

    public ApiBeycoTokenResponse refreshBeycoAuthToken(String refreshToken, Long companyId) throws ApiException {
        Company company = companyQueries.fetchCompany(companyId);
        if (company.getAllowBeycoIntegration() == null || !company.getAllowBeycoIntegration()) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Company is not allowed to use Beyco platform");
        }

        RestTemplate restTemplate = new RestTemplate();
        ApiBeycoTokenRequest tokenRequest = new ApiBeycoTokenRequest();
        tokenRequest.setRefreshToken(refreshToken);
        tokenRequest.setClientSecret(this.clientSecret);
        tokenRequest.setClientId(this.clientId);
        tokenRequest.setGrantType(TokenGrantType.RefreshToken);

        ResponseEntity<ApiBeycoTokenResponse> response = restTemplate.exchange(this.authUrl, HttpMethod.POST,
                new HttpEntity<>(tokenRequest), ApiBeycoTokenResponse.class);
        return response.getBody();
    }

    public Object sendBeycoOrder(ApiBeycoOrderFields beycoOrder, String token, Long companyId) throws ApiException {
        Company company = companyQueries.fetchCompany(companyId);
        if (company.getAllowBeycoIntegration() == null || !company.getAllowBeycoIntegration()) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Company is not allowed to use Beyco platform");
        }

        beycoOrder.setPrivacy(BeycoPrivacy.Private);
        for(ApiBeycoOrderCoffees beycoCoffees : beycoOrder.getOfferCoffees()) {
            beycoCoffees.setIsFixedPrice(true);
            beycoCoffees.getCoffee().setIsBulk(true);
            beycoCoffees.getCoffee().setUnit(BeycoCoffeeUnit.Kg);
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<ApiBeycoOrderFields> request = new HttpEntity<>(beycoOrder, headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                this.beycoUrl + BeycoOrderService.BEYCO_ORDER_ENDPOINT,
                HttpMethod.POST,
                request,
                Object.class
        );
        return response.getBody();
    }

    public ApiBeycoOrderFields getBeycoOrderFieldList(List<Long> stockOrderIds, Long companyId) throws ApiException {
        Company company = companyQueries.fetchCompany(companyId);
        if (company.getAllowBeycoIntegration() == null || !company.getAllowBeycoIntegration()) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Company is not allowed to use Beyco platform");
        }

        ApiBeycoOrderFields beycoOrderFields = new ApiBeycoOrderFields();
        StockOrder stockOrder = stockOrderService.fetchEntity(stockOrderIds.get(0), StockOrder.class);
        beycoOrderFields.setPrivacy(BeycoPrivacy.Private);
        beycoOrderFields.setPortOfExport(new ApiBeycoPortOfExport());
        beycoOrderFields.setOfferCoffees(new ArrayList<>());

        String cityAddress = Stream.of(
                stockOrder.getFacility().getFacilityLocation().getAddress().getAddress(),
                stockOrder.getFacility().getFacilityLocation().getAddress().getCity(),
                stockOrder.getFacility().getFacilityLocation().getAddress().getZip()
        ).filter(s -> s != null && !s.isEmpty()).collect(Collectors.joining(", "));
        String villageAddress = Stream.of(
                stockOrder.getFacility().getFacilityLocation().getAddress().getCell(),
                stockOrder.getFacility().getFacilityLocation().getAddress().getSector(),
                stockOrder.getFacility().getFacilityLocation().getAddress().getVillage()
        ).filter(s -> s != null && !s.isEmpty()).collect(Collectors.joining(", "));
        beycoOrderFields.getPortOfExport().setAddress(!cityAddress.isEmpty() ? cityAddress : villageAddress);
        beycoOrderFields.getPortOfExport().setCountry(stockOrder.getFacility().getFacilityLocation().getAddress().getCountry().getName());
        beycoOrderFields.getPortOfExport().setLatitude(stockOrder.getFacility().getFacilityLocation().getLatitude());
        beycoOrderFields.getPortOfExport().setLatitude(stockOrder.getFacility().getFacilityLocation().getLongitude());

        for(Long stockOrderId : stockOrderIds) {
            stockOrder = stockOrderService.fetchEntity(stockOrderId, StockOrder.class);
            ApiBeycoOrderCoffees orderCoffees = new ApiBeycoOrderCoffees();
            orderCoffees.setCoffee(new ApiBeycoCoffee());

            orderCoffees.getCoffee().setName(stockOrder.getInternalLotNumber());
            orderCoffees.getCoffee().setRegion(stockOrder.getFacility().getFacilityLocation().getAddress().getState());
            orderCoffees.getCoffee().setCountry(beycoOrderFields.getPortOfExport().getCountry());
            orderCoffees.getCoffee().setHarvestAt(stockOrder.getProductionDate());

            List<ApiBeycoCoffeeCertificate> apiCerts = new ArrayList<>();
            for (CompanyCertification cert : stockOrder.getCompany().getCertifications()) {
                if (cert.getType().equals("FAIRTRADE")) {
                    ApiBeycoCoffeeCertificate apiCert = new ApiBeycoCoffeeCertificate();
                    apiCert.setType(BeycoCertificateType.Fairtrade);
                    apiCerts.add(apiCert);
                } else if(cert.getType().equals("EU_ORGANIC")) {
                    ApiBeycoCoffeeCertificate apiCert = new ApiBeycoCoffeeCertificate();
                    apiCert.setType(BeycoCertificateType.Organic);
                    apiCerts.add(apiCert);
                }
            }
            orderCoffees.getCoffee().setCertificates(apiCerts);

            findRequiredFieldsInHistory(orderCoffees.getCoffee(), stockOrder);
            beycoOrderFields.getOfferCoffees().add(orderCoffees);
        }
        return beycoOrderFields;
    }

    private void findRequiredFieldsInHistory(ApiBeycoCoffee coffee, StockOrder stockOrder) {
        if(stockOrder != null && (coffee.getMinScreenSize() == null || coffee.getCuppingScore() == null || coffee.getGrades() == null)) {
            if(coffee.getMinScreenSize() == null) {
                StockOrderPEFieldValue field = stockOrder.getProcessingEFValues().stream().filter(
                                v -> v.getProcessingEvidenceField().getFieldName().equals(SCREEN_SIZE_FIELD_NAME)
                        ).findFirst().orElse(null);

                Integer screenSize = field != null && field.getStringValue() != null && field.getStringValue().matches("-?\\d+") ?
                        Integer.parseInt(field.getStringValue()) : null;
                coffee.setMinScreenSize(screenSize);
                coffee.setMaxScreenSize(screenSize);
            }

            if(coffee.getCuppingScore() == null) {
                StockOrderPEFieldValue field = stockOrder.getProcessingEFValues().stream().filter(
                                v -> v.getProcessingEvidenceField().getFieldName().equals(CUPPING_SCORE_FIELD_NAME)
                        ).findFirst().orElse(null);
                Integer cuppingScore = field != null && field.getStringValue().matches("-?\\d+") ?
                        Integer.parseInt(field.getStringValue()) : null;

                if(cuppingScore != null) {
                    coffee.setCuppingScore(cuppingScore);
                    ApiBeycoCoffeeQuality coffeeQuality = new ApiBeycoCoffeeQuality();
                    coffeeQuality.setType(
                            cuppingScore >= 80 ?
                                BeycoQualitySegmentType.Specialty : BeycoQualitySegmentType.CommodityConventional
                    );
                    coffee.setQualitySegments(List.of(coffeeQuality));
                }

            }

            if(coffee.getGrades() == null) {
                StockOrderPEFieldValue field = stockOrder.getProcessingEFValues().stream().filter(
                                v -> v.getProcessingEvidenceField().getFieldName().equals(GRADE_FIELD_NAME)
                        ).findFirst().orElse(null);

                if(field != null && field.getStringValue() != null) {
                    ApiBeycoCoffeeGrade coffeeGrade = new ApiBeycoCoffeeGrade();
                    try {
                        coffeeGrade.setType(BeycoGradeType.valueOf(field.getStringValue()));
                    } catch (IllegalArgumentException e) {
                        coffeeGrade.setType(BeycoGradeType.Other);
                        coffee.setAdditionalQualityDescriptors(field.getStringValue());
                    }
                    coffee.setGrades(List.of(coffeeGrade));
                }
            }

            if (stockOrder.getProcessingOrder() != null && !stockOrder.getProcessingOrder().getInputTransactions().isEmpty()) {
                // Get the Stock order that were used as input when executing the Processing order
                List<StockOrder> inputStockOrders = stockOrder.getProcessingOrder().getInputTransactions()
                        .stream()
                        .filter(transaction -> !transaction.getStatus().equals(TransactionStatus.CANCELED))
                        .map(Transaction::getSourceStockOrder)
                        .collect(Collectors.toList());

                inputStockOrders.forEach(sourceOrder -> findRequiredFieldsInHistory(coffee, sourceOrder));
            }
        }
    }

}
