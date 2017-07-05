package com.treph.compaign.bean;

import java.util.List;

/**
 * Created by Theodore on 7/1/2017.
 */
public class CampaignResponse {
    public static final int SUCCESS_CODE = 0;
    public static final int NO_ACTIVE_CAMPAIGN_ERROR_CODE = 1;
    public static final int UNABLE_TO_ADD_ACTIVE_CAMPAIGN_CODE = 2;
    public static final int INVALID_PARTNER_ID_CODE = 3;
    public static final int INVALID_DURATION_CODE = 4;
    public static final int INVALID_AD_CONTENT_CODE = 5;
    public static final int SERVER_ERROR_CODE = 500;

    public static final String SUCCESS_MESSAGE = "OK";
    public static final String UNABLE_TO_ADD_ACTIVE_CAMPAIGN_MESSAGE = "Unable to add campaign, partner has active campaign";
    public static final String NO_ACTIVE_CAMPAIGN_MESSAGES = "No active compaigns";
    public static final String INVALID_PARTNER_ID_MESSAGE = "Invalid partner_id, partner_id must not be a null value or be an empty string";
    public static final String INVALID_DURATION_MESSAGE = "Invalid duration, duration must be >= 1";
    public static final String INVALID_AD_CONTENT_MESSAGE = "Invalid ad_content, ad_content must not be a null value or be an empty string";
    public static final String SERVER_MESSAGE = "An error occurred on the campaign server";

    private int errorCode;
    private String errorMessage = null;
    private List<CampaignDTO> campaignDTOList = null;

    public CampaignResponse() {}

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<CampaignDTO> getCampaignDTOList() {
        return campaignDTOList;
    }

    public void setCampaignDTOList(List<CampaignDTO> campaignDTOList) {
        this.campaignDTOList = campaignDTOList;
    }
}
