package com.thoughtworks.fam.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.google.common.collect.Lists;
import com.thoughtworks.fam.domain.Asset;
import com.thoughtworks.fam.domain.User;
import com.thoughtworks.fam.exception.ConflictException;

public class AssetServiceTest
{

    private AssetService assetService;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception
    {
        initMocks(this);
        assetService = new AssetService(assetRepository);
    }

    @Test
    public void should_create_asset() throws Exception
    {
        int beforeCreateAmount = AssetService.getAssets().size();

        Asset asset = new Asset("twer", "11111111", "123321",
                "Apple Laptop", new Date().toString());
        assetService.createAsset(asset);

        assertThat(AssetService.getAssets().size()).isEqualTo(beforeCreateAmount + 1);
        assertThat(AssetService.getAssets().contains(asset)).isTrue();
    }

    @Test(expected = ConflictException.class)
    public void should_throw_conflict_exception_when_asset_name_existed() {
        Asset asset = new Asset("twer", "12345678", "123321",
                "Apple Laptop", new Date().toString());
        assetService.createAsset(asset);
    }

    @Test
    public void should_get_assets_given_valid_user_id()
    {
        //given
        String account = "admin";
        given(assetRepository.findByOwnerName(account)).willReturn(Lists.newArrayList(
                new Asset("admin", "Macbook", "123456", "2015-5-23", "Laptop")
        ));

        //when
        List<Asset> assets = assetService.getUserAssets(account);

        //then
        assertThat(assets.size()).isGreaterThan(0);
        assertThat(assets.get(0).getAssetNumber()).isEqualTo("123456");
        assertThat(assets.get(0).getAssetType()).isEqualTo("Laptop");
    }

    @Test
    public void should_get_others_assets() throws Exception
    {
        String account = "admin";
        List<Asset> othersAssets = assetService.getOthersAssets(account);

        assertThat(othersAssets.size()).isGreaterThan(0);
        assertThat(othersAssets.get(0).getAssetNumber()).isEqualTo("123456");
        assertThat(othersAssets.get(0).getAssetType()).isEqualTo("Laptop");
    }
}