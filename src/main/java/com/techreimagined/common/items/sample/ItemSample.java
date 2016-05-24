package com.techreimagined.common.items.sample;

import com.techreimagined.common.items.ItemBase;
import com.techreimagined.TechReimaginedCreativeTabs;

public class ItemSample extends ItemBase {
    public ItemSample() {
        super("sample/sampleitem");
        this.setCreativeTab(TechReimaginedCreativeTabs.tabPower);
        this.setInternalName("sample_item");
    }
}
