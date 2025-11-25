package com.project.backend_project.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class Metadata {
    
      private static final Map<String, Object> extraData = extraData();

    private static Map<String, Object> extraData() {
        Map<String, Object> map = new HashMap<>();

        map.put("addressCity", "Dodoma Mjini");
        map.put("addressFromDate", "01/04/2010");
        map.put("addressLine1", "Dodoma1");
        map.put("addressLine2", "Dodoma2");
        map.put("addressLine3", "Dodoma3");
        map.put("addressLine4", "Dodoma4");
        map.put("employAddressLine", "Dodoma5");
        map.put("employmentAddress", "Dodoma6");
        map.put("employmentCity", "Dodoma7");
        map.put("postalCode", "");
        map.put("idCityOfIssue", "Dar es salaam");
        map.put("idExpiryDate", "25/02/2030");
        map.put("idIssueDate", "25/02/2011");
        map.put("signature", "SGVsbG8sIFdvcmxkIQ==");
        map.put("idPhoto", "SGVsbG8sIFdvcmxkIQ==");
        map.put("employerName", null);
        map.put("employmentCategoryId", null);
        map.put("employmentStartMonth", null);
        map.put("employmentStartYear", null);
        map.put("titleId", 11);
        map.put("industryCode", "1116");
        map.put("marriageFlag", "S");
        map.put("addressPropertyTypeId", 353);
        map.put("addressTypeId", 41);
        map.put("countryOfBirthId", 676);
        map.put("countryOfIdIssue", 676);
        map.put("countryOfResidenceId", 676);
        map.put("customerCategory", "PER");
        map.put("customerSegmentId", 391);
        map.put("customerType", 718960);
        map.put("dependantCount", 0);
        map.put("employed", true);
        map.put("grossAnnualSalId", 384);
        map.put("identificationId", 403);
        map.put("identityType", "012");
        map.put("identityTypeId", 403);
        map.put("industryId", 263);
        map.put("marketingCampaignCd", "114");
        map.put("marketingCampaignId", 335);
        map.put("nationalityId", 261);
        map.put("occupationId", 425);
        map.put("openingReasonId", 491);
        map.put("primaryAddress", false);
        map.put("profQualificationCode", "018");
        map.put("profQualificationId", 308);
        map.put("professionCd", "018");
        map.put("professionId", 308);
        map.put("qualificationCode", "03");
        map.put("qualificationId", 431);
        map.put("religionId", 235);
        map.put("resident", true);
        map.put("riskCode", "P13");
        map.put("riskCountryId", 676);
        map.put("riskId", 566);
        map.put("serviceLevelId", 11);
        map.put("sourceOfFundCd", "231");
        map.put("sourceOfFundId", 231);
        map.put("sourceOfFundsId", 231);
        map.put("taxGroupCode", "200");
        map.put("taxGroupId", 382);
        map.put("taxStatusId", 441);
        map.put("verified", true);

        return map;
    }
}
