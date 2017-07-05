package com.treph.compaign;

import com.treph.compaign.bean.CampaignDTO;
import com.treph.compaign.bean.CampaignResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Theodore on 7/3/2017.
 */
public class CampaignServiceTest {
    private static final String PARTNER_ID_1 = "partner_1";
    private static final String PARTNER_ID_2 = "partner_2";
    private static final int DURATION_1 = 1;
    private static final int DURATION_10 = 10;
    private static final int DURATION_100 = 100;
    private static final String CONTENT_1 = "content 1";
    private static final String CONTENT_2 = "content 2";
    private static final String NO_ACTIVE_CAMPAIGNS_FOR_PARTNER = "Partner " + PARTNER_ID_1 + " does not have any active campaigns";

    private CampaignService campaignService;


    @Before
    public void initialize() {
        campaignService = new CampaignService();
    }

    private CampaignDTO createCampaignDTO(String partnerId, int duration, String content) {
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setPartner_id(partnerId);
        campaignDTO.setDuration(duration);
        campaignDTO.setAd_content(content);

        return campaignDTO;
    }

    @Test
    public void addCampaignTest() {
        CampaignDTO campaignDTO = createCampaignDTO(PARTNER_ID_1, DURATION_10, CONTENT_1);
        CampaignResponse campaignResponse = campaignService.addCampaign(campaignDTO);

        assertNotNull(campaignResponse);
        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.SUCCESS_CODE);
        assertEquals(campaignResponse.getErrorMessage(), CampaignResponse.SUCCESS_MESSAGE);
        assertNull(campaignResponse.getCampaignDTOList());
    }

    @Test
    public void addCampaignWithNullPartnerIdTest() {
        CampaignDTO campaignDTO = createCampaignDTO(null, DURATION_10, CONTENT_1);
        CampaignResponse campaignResponse = campaignService.addCampaign(campaignDTO);

        assertNotNull(campaignResponse);
        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.INVALID_PARTNER_ID_CODE);
        assertEquals(campaignResponse.getErrorMessage(), CampaignResponse.INVALID_PARTNER_ID_MESSAGE);
        assertNull(campaignResponse.getCampaignDTOList());
    }

    @Test
    public void addCampaignWithSpacesForPartnerIdTest() {
        CampaignDTO campaignDTO = createCampaignDTO("   ", DURATION_10, CONTENT_1);
        CampaignResponse campaignResponse = campaignService.addCampaign(campaignDTO);

        assertNotNull(campaignResponse);
        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.INVALID_PARTNER_ID_CODE);
        assertEquals(campaignResponse.getErrorMessage(), CampaignResponse.INVALID_PARTNER_ID_MESSAGE);
        assertNull(campaignResponse.getCampaignDTOList());
    }

    @Test
    public void addCampaignWithEmptySpacesForPartnerIdTest() {
        CampaignDTO campaignDTO = createCampaignDTO("", DURATION_10, CONTENT_1);
        CampaignResponse campaignResponse = campaignService.addCampaign(campaignDTO);

        assertNotNull(campaignResponse);
        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.INVALID_PARTNER_ID_CODE);
        assertEquals(campaignResponse.getErrorMessage(), CampaignResponse.INVALID_PARTNER_ID_MESSAGE);
        assertNull(campaignResponse.getCampaignDTOList());
    }

    @Test
    public void addCampaignWithNulAdContentTest() {
        CampaignDTO campaignDTO = createCampaignDTO(PARTNER_ID_1, DURATION_10, null);
        CampaignResponse campaignResponse = campaignService.addCampaign(campaignDTO);

        assertNotNull(campaignResponse);
        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.INVALID_AD_CONTENT_CODE);
        assertEquals(campaignResponse.getErrorMessage(), CampaignResponse.INVALID_AD_CONTENT_MESSAGE);
        assertNull(campaignResponse.getCampaignDTOList());
    }

    @Test
    public void addCampaignWithSpacesForAdContentTest() {
        CampaignDTO campaignDTO = createCampaignDTO(PARTNER_ID_1, DURATION_10, "   ");
        CampaignResponse campaignResponse = campaignService.addCampaign(campaignDTO);

        assertNotNull(campaignResponse);
        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.INVALID_AD_CONTENT_CODE);
        assertEquals(campaignResponse.getErrorMessage(), CampaignResponse.INVALID_AD_CONTENT_MESSAGE);
        assertNull(campaignResponse.getCampaignDTOList());
    }

    @Test
    public void addCampaignWithEmptySpacesForAdContentTest() {
        CampaignDTO campaignDTO = createCampaignDTO(PARTNER_ID_1, DURATION_10, "");
        CampaignResponse campaignResponse = campaignService.addCampaign(campaignDTO);

        assertNotNull(campaignResponse);
        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.INVALID_AD_CONTENT_CODE);
        assertEquals(campaignResponse.getErrorMessage(), CampaignResponse.INVALID_AD_CONTENT_MESSAGE);
        assertNull(campaignResponse.getCampaignDTOList());
    }

    @Test
    public void addCampaignWithInvalidDurationTest() {
        CampaignDTO campaignDTO = createCampaignDTO(PARTNER_ID_1, -1, CONTENT_1);
        CampaignResponse campaignResponse = campaignService.addCampaign(campaignDTO);

        assertNotNull(campaignResponse);
        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.INVALID_DURATION_CODE);
        assertEquals(campaignResponse.getErrorMessage(), CampaignResponse.INVALID_DURATION_MESSAGE);
        assertNull(campaignResponse.getCampaignDTOList());
    }

    @Test
    public void addCampaignWhileCampaignActiveTest() {
        // Create 1st campaign with long time out
        CampaignDTO campaignDTO = createCampaignDTO(PARTNER_ID_1, DURATION_100, CONTENT_1);
        campaignService.addCampaign(campaignDTO);

        // Attempt to add 2nd campaign while first campaign is active
        campaignDTO = createCampaignDTO(PARTNER_ID_1, DURATION_10, CONTENT_2);
        CampaignResponse campaignResponse = campaignService.addCampaign(campaignDTO);

        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.UNABLE_TO_ADD_ACTIVE_CAMPAIGN_CODE);
        assertEquals(campaignResponse.getErrorMessage(), CampaignResponse.UNABLE_TO_ADD_ACTIVE_CAMPAIGN_MESSAGE);
        assertNull(campaignResponse.getCampaignDTOList());
    }

    @Test
    public void addCampaignAfterCampaignExpiresTest() throws Exception {
        // first campaign expires after 1 second
        CampaignDTO campaignDTO = createCampaignDTO(PARTNER_ID_1, DURATION_1, CONTENT_1);
        campaignService.addCampaign(campaignDTO);

        // Wait for campaign to expire
        Thread.sleep(2000);

        // Create second compaign after 1st campaign has expired
        campaignDTO = createCampaignDTO(PARTNER_ID_1, DURATION_10, CONTENT_2);
        CampaignResponse campaignResponse = campaignService.addCampaign(campaignDTO);

        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.SUCCESS_CODE);
        assertEquals(campaignResponse.getErrorMessage(), CampaignResponse.SUCCESS_MESSAGE);
        assertNull(campaignResponse.getCampaignDTOList());
    }

    @Test
    public void getActiveCampaignForPartnerWithEmptyList() {
        CampaignResponse campaignResponse = campaignService.getActiveCampaign(PARTNER_ID_1);
        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.NO_ACTIVE_CAMPAIGN_ERROR_CODE);
        assertEquals(campaignResponse.getErrorMessage(), NO_ACTIVE_CAMPAIGNS_FOR_PARTNER);
        assertNull(campaignResponse.getCampaignDTOList());
    }

    @Test
    public void getActiveCampaignForPartnersWithMultipleCampaigns() throws Exception{
        // Create 1st campaign with long time out
        CampaignDTO campaignDTO = createCampaignDTO(PARTNER_ID_1, DURATION_1, CONTENT_1);
        campaignService.addCampaign(campaignDTO);

        Thread.sleep(2000L);
        // Attempt to add 2nd campaign while first campaign is active
        campaignDTO = createCampaignDTO(PARTNER_ID_1, DURATION_10, CONTENT_2);
        campaignService.addCampaign(campaignDTO);

        CampaignResponse campaignResponse = campaignService.getActiveCampaign(PARTNER_ID_1);
        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.SUCCESS_CODE);
        assertEquals(campaignResponse.getErrorMessage(), CampaignResponse.SUCCESS_MESSAGE);

    }
}
