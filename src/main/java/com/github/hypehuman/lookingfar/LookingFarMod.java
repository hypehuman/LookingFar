package com.github.hypehuman.lookingfar;

import net.minecraftforge.client.ForgeIngameGui;
import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("lookingfar")
public class LookingFarMod
{
    public LookingFarMod() {
        ForgeIngameGui.rayTraceDistance = 20000;
    }
}
