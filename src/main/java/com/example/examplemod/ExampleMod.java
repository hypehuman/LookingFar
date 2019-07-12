package com.example.examplemod;

import net.minecraftforge.client.ForgeIngameGui;
import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("examplemod")
public class ExampleMod
{
    public ExampleMod() {
        ForgeIngameGui.rayTraceDistance = 20000;
    }
}
