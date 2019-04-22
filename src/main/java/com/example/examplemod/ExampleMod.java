package com.example.examplemod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("examplemod")
public class ExampleMod
{
    public static double looking_at_distance = 20000; // default 20

    public ExampleMod() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPostOverlayRender(RenderGameOverlayEvent.Text event) {
        // Re-do, with modification, what was done in net.minecraftforge.client.GuiIngameForge.GuiOverlayDebugForge.update()
        Entity entity = Minecraft.getInstance().getRenderViewEntity();
        RayTraceResult rayTraceBlock = entity.rayTrace(looking_at_distance, 0.0F, RayTraceFluidMode.NEVER);
        RayTraceResult rayTraceFluid = entity.rayTrace(looking_at_distance, 0.0F, RayTraceFluidMode.ALWAYS);

        // Remove what was done in net.minecraft.client.gui.GuiOverlayDebug.call()
        // It added up to two "Looking at " entries to the end of the list.
        ArrayList<String> list = event.getLeft();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).startsWith("Looking at ")) {
                list.remove(i);
                i--;
            }
        }

        // Re-do it with our new field instead of 20.
        if (rayTraceBlock != null && rayTraceBlock.type == RayTraceResult.Type.BLOCK) {
           BlockPos blockpos1 = rayTraceBlock.getBlockPos();
           list.add(String.format("Looking at block: %d %d %d", blockpos1.getX(), blockpos1.getY(), blockpos1.getZ()));
        }
        if (rayTraceFluid != null && rayTraceFluid.type == RayTraceResult.Type.BLOCK) {
           BlockPos blockpos2 = rayTraceFluid.getBlockPos();
           list.add(String.format("Looking at liquid: %d %d %d", blockpos2.getX(), blockpos2.getY(), blockpos2.getZ()));
        }
    }
}
