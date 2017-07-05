package com.treph.compaign;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.treph.compaign.bean.Campaign;
import com.treph.compaign.bean.CampaignDTO;
import com.treph.compaign.bean.CampaignResponse;
import org.springframework.stereotype.Service;


/**
 * Performs the operations for campaigns. All the operations will return a {@link CampaignResponse} that will detail
 * if the operation was successful or failed.
 */
@Service
public class CampaignService {
    private static final String SUCCESS = "OK";
    private static final String ACTIVE_CAMPAIGN = "Unable to add campaign, partner has active campaign";


    private Map<String, Campaign> campaignMap = new ConcurrentHashMap<>();

    public CampaignService() {}

    /**
     * Adds a campaign if the partner does not have an active campaign.
     * @param campaignDTO {@link CampaignDTO} contains the campaign data to be added
     * @return {@link CampaignResponse} will have error code of {@link CampaignResponse#SUCCESS_CODE} and
     * message of {@link CampaignResponse#SUCCESS_MESSAGE} if add was successful.
     */
    public CampaignResponse addCampaign(CampaignDTO campaignDTO) {
        CampaignResponse campaignResponse = new CampaignResponse();
        CampaignValidator validator = new CampaignValidator(campaignDTO, campaignResponse);
        boolean isValidCampaign = validator.validateCampaign();

        if (!isValidCampaign) {
            return campaignResponse;
        }

        // check for active campaign
        Campaign campaign = campaignMap.get(campaignDTO.getPartner_id());

        if (campaign != null && campaign.isActive()) {
            campaignResponse.setErrorCode(CampaignResponse.UNABLE_TO_ADD_ACTIVE_CAMPAIGN_CODE);
            campaignResponse.setErrorMessage(CampaignResponse.UNABLE_TO_ADD_ACTIVE_CAMPAIGN_MESSAGE);
        } else {
            // Add the campaign
            campaign = new Campaign(campaignDTO);
            campaignMap.put(campaign.getPartnerId(), campaign);
            campaignResponse.setErrorCode(CampaignResponse.SUCCESS_CODE);
            campaignResponse.setErrorMessage(CampaignResponse.SUCCESS_MESSAGE);
        }

        return campaignResponse;
    }

    /**
     * Retrieves the active campaign for a partner.
     * @param partnerId The partner id for the ad campaign
     * @return {@link CampaignResponse} If the partner does not have an active campaign, the {@link CampaignResponse}
     * will have error code of {@link CampaignResponse#NO_ACTIVE_CAMPAIGN_ERROR_CODE} and error message stating the
     * partner has no active campaigns.  The campaign list will be null. Otherwise,  the error code of
     * {@link CampaignResponse#SUCCESS_CODE}, message of {@link CampaignResponse#SUCCESS_MESSAGE}, and the active campaign
     * in the list campaigns.
     */
    public CampaignResponse getActiveCampaign(String partnerId) {
        Campaign campaign = campaignMap.get(partnerId);
        CampaignResponse campaignResponse = new CampaignResponse();

        if (campaign != null && campaign.isActive()) {
            CampaignDTO campaignDTO = convert(campaign);
            campaignResponse.setErrorCode(CampaignResponse.SUCCESS_CODE);
            campaignResponse.setErrorMessage(CampaignResponse.SUCCESS_MESSAGE);

            List<CampaignDTO> campaignDTOList = new ArrayList<>();
            campaignDTOList.add(campaignDTO);
            campaignResponse.setCampaignDTOList(campaignDTOList);
        }
        else {
            campaignResponse.setErrorCode(CampaignResponse.NO_ACTIVE_CAMPAIGN_ERROR_CODE);
            campaignResponse.setErrorMessage("Partner " + partnerId + " does not have any active campaigns");
            campaignResponse.setCampaignDTOList(null);
        }

        return campaignResponse;
    }

    /**
     * Converts a {@link Campaign} to a {@link CampaignDTO}
     * @param campaign {@link Campaign} object to be converted
     * @return {@link CampaignDTO} the converted campaign
     */
    private CampaignDTO convert(Campaign campaign) {
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setPartner_id(campaign.getPartnerId());
        campaignDTO.setAd_content(campaign.getContent());
        campaignDTO.setDuration(campaign.getDurationInSeconds());

        return campaignDTO;
    }

    /**
     * Gets all campaigns (includes inactive campaigns).
     * @return {@link CampaignResponse} If no active campaigns, the {@link CampaignResponse}
     * will have error code of {@link CampaignResponse#NO_ACTIVE_CAMPAIGN_ERROR_CODE} and error message stating
     * no active campaigns found. The campaign list will be null. Otherwise, the error code of
     * {@link CampaignResponse#SUCCESS_CODE}, message of {@link CampaignResponse#SUCCESS_MESSAGE}, and the active campaigns
     * in the list campaigns.

     */
    public CampaignResponse getAllCampaigns() {
        // Get the partner ids as the keys
        Set<String> keySet = campaignMap.keySet();
        Iterator<String> iterator = keySet.iterator();
        List<CampaignDTO> campaignList = new ArrayList<>();
        CampaignResponse campaignResponse = new CampaignResponse();

        // Populate the campaign list from the map
        while (iterator.hasNext()) {
            String partnerId = iterator.next();
            Campaign campaign = campaignMap.get(partnerId);
            CampaignDTO campaignDTO = convert(campaign);
            campaignList.add(campaignDTO);
        }

        if (campaignList.size() > 0) {
            campaignResponse.setErrorCode(CampaignResponse.SUCCESS_CODE);
            campaignResponse.setErrorMessage(CampaignResponse.SUCCESS_MESSAGE);
            campaignResponse.setCampaignDTOList(campaignList);
        } else {
            campaignResponse.setErrorCode(CampaignResponse.NO_ACTIVE_CAMPAIGN_ERROR_CODE);
            campaignResponse.setErrorMessage(CampaignResponse.NO_ACTIVE_CAMPAIGN_MESSAGES);
            campaignResponse.setCampaignDTOList(null);
        }

        return campaignResponse;
    }
}
