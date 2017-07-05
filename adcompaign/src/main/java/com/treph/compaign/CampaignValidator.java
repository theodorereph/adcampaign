package com.treph.compaign;

import com.treph.compaign.bean.CampaignDTO;
import com.treph.compaign.bean.CampaignResponse;

/**
 * Validates a {@link CampaignDTO}
 */
public class CampaignValidator {
    private CampaignDTO campaignDTO;
    private CampaignResponse campaignResponse;

    /**
     * Constructor
     * @param campaignDTO {@link CampaignDTO} to be validate
     * @param campaignResponse {@link CampaignResponse} will have the error code and error message set if
     *                         the {@link CampaignDTO} is deemed invalid
     */
    public CampaignValidator(CampaignDTO campaignDTO, CampaignResponse campaignResponse) {
        this.campaignDTO = campaignDTO;
        this.campaignResponse = campaignResponse;
    }

    /**
     * Checks to see if {@code partner_id} is not null and not comprised of spaces
     * @return {@code true} returned if valid, otherwise {@code false}
     */
    private boolean isValidPartnerId() {
        String partnerId = campaignDTO.getPartner_id();

        if (partnerId == null || partnerId.trim().length() == 0 ) {
            campaignResponse.setErrorCode(CampaignResponse.INVALID_PARTNER_ID_CODE);
            campaignResponse.setErrorMessage(CampaignResponse.INVALID_PARTNER_ID_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks to see if {@code ad_content} is not null and not comprised of spaces
     * @return {@code true} returned if valid, otherwise {@code false}
     */
    private boolean isValidAdContent() {
        String ad_content = campaignDTO.getAd_content();

        if (ad_content == null || ad_content.trim().length() == 0 ) {
            campaignResponse.setErrorCode(CampaignResponse.INVALID_AD_CONTENT_CODE);
            campaignResponse.setErrorMessage(CampaignResponse.INVALID_AD_CONTENT_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks to see if {@code duration} is >= 0
     * @return {@code true} returned if valid, otherwise {@code false}
     */
    private boolean isValidDuration() {
        if (campaignDTO.getDuration() < 1) {
            campaignResponse.setErrorCode(CampaignResponse.INVALID_DURATION_CODE);
            campaignResponse.setErrorMessage(CampaignResponse.INVALID_DURATION_MESSAGE);
            return false;
        } else {
            return true;
        }
    }
    /**
     * Performs validation on a {@link CampaignDTO}
     * Checks to see if the partner_id is not null or blank spaces
     * Checks to see that duration is greater than 0
     * Checks to see if the ad_content is not null or blank spaces
     * @return {@code true} if passes the validations, otherwise {@code false}. If the {@link CampaignDTO} is invalid,
     * the {@CampaignResponse} will be set with the correct error code and error message.
     */
    public boolean validateCampaign() {
        if (!isValidPartnerId()) {
            return false;
        }

        if (!isValidAdContent()) {
            return false;
        }

        if (!isValidDuration()) {
            return false;
        }

        return true;
    }
}
