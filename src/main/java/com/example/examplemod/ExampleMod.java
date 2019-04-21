package com.example.examplemod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("examplemod")
public class ExampleMod
{
    public static double looking_at_distance = 200; // default 20

    public ExampleMod() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPostOverlayRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() != ElementType.DEBUG) {
            return;
        }

        GuiIngame guiIngame = Minecraft.getInstance().ingameGUI;
        GuiOverlayDebug overlayDebug = getOverlayDebug(guiIngame);

        // Override what was done in net.minecraftforge.client.GuiIngameForge.GuiOverlayDebugForge.update()
        Entity entity = Minecraft.getInstance().getRenderViewEntity();
        setTraceBlock(overlayDebug, entity.rayTrace(looking_at_distance, 0.0F, RayTraceFluidMode.NEVER));
        setTraceFluid(overlayDebug, entity.rayTrace(looking_at_distance, 0.0F, RayTraceFluidMode.ALWAYS));
    }

    /*
     * Access protected field GuiOverlayDebug.overlayDebug
     */
    private static GuiOverlayDebug getOverlayDebug(GuiIngame instance) {
        return ObfuscationReflectionHelper.getPrivateValue(GuiIngame.class, instance, "field_175198_t");
    }

    /*
     * Mutate private field GuiOverlayDebug.rayTraceBlock
     */
    private static void setTraceBlock(GuiOverlayDebug overlayDebug, RayTraceResult value) {
        ObfuscationReflectionHelper.setPrivateValue(GuiOverlayDebug.class, overlayDebug, value, "field_211537_g");
    }

    /*
     * Mutate private field GuiOverlayDebug.rayTraceFluid
     */
    private static void setTraceFluid(GuiOverlayDebug overlayDebug, RayTraceResult value) {
        ObfuscationReflectionHelper.setPrivateValue(GuiOverlayDebug.class, overlayDebug, value, "field_211538_h");
    }
}
