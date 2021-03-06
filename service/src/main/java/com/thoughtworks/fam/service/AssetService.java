package com.thoughtworks.fam.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.thoughtworks.fam.domain.Asset;
import com.thoughtworks.fam.exception.ConflictException;
import com.thoughtworks.fam.exception.ErrorCode;

@Service
public class AssetService
{
    private static List<Asset> assets = new LinkedList<Asset>();

    private final AssetRepository assetRepository;

    @Autowired
    public AssetService(AssetRepository assetRepository)
    {
        this.assetRepository = assetRepository;
    }

    static {
        assets.add(new Asset("twer", "MacBook Pro", "17000000", "2015-5-23", "Laptop"));
    }

    public void createAsset(Asset asset)
    {
        if (!isAssetAvailable(asset.getAssetNumber())) {
            throw new ConflictException(ErrorCode.ASSET_NUMBER_EXISTED,
                    "The name already exist, please use another one.");
        }

        assets.add(asset);
    }

    private boolean isAssetAvailable(final String assetNumber)
    {
        for (Asset asset : assets) {
            if (assetNumber.equals(asset.getAssetNumber())) {
                return false;
            }
        }

        return true;
    }

    public static List<Asset> getAssets()
    {
        return assets;
    }

    public List<Asset> getUserAssets(String account)
    {
        return this.assetRepository.findByAccount(account);
    }

    public List<Asset> getOthersAssets(String account)
    {
        final List<Asset> userAssets = getUserAssets(account);
        if (userAssets.isEmpty()) {
            return Lists.newArrayList();
        }
        Iterable<Asset> allAssets = removeUserAssetsFromAllAssets(userAssets);
        return Lists.newArrayList(allAssets);
    }

    private Iterable<Asset> removeUserAssetsFromAllAssets(final List<Asset> userAssets)
    {
        Iterable<Asset> allAssets = this.assetRepository.findAll();
        Iterables.removeIf(allAssets, new Predicate<Asset>()
        {
            @Override
            public boolean apply(Asset asset)
            {
                for (Asset userAsset : userAssets) {
                    if ((userAsset.getAssetNumber().equals(asset.getAssetNumber()))) {
                        return true;
                    }
                }
                return false;
            }
        });
        return allAssets;
    }

    public void addAsset(Asset asset)
    {
        if (this.assetRepository.findByAssetNumber(asset.getAssetNumber()) != null) {
            throw new ConflictException(ErrorCode.ASSET_NUMBER_EXISTED,
                    "The name already exist, please use another one.");
        } else {
            this.assetRepository.save(asset);
        }
    }
}
