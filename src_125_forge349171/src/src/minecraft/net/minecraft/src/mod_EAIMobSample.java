package net.minecraft.src;

import java.util.Map;

import net.minecraft.client.Minecraft;
import bbc_mc.EAIMobSample.EAIMobSample;

public class mod_EAIMobSample extends BaseMod {
    
    public final EAIMobSample mod;
    
    private static mod_EAIMobSample instance;
    
    public mod_EAIMobSample() {
        mod = new EAIMobSample(this);
        instance = this;
    }
    
    @Override
    public String getVersion() {
        return mod.getVersion();
    }
    
    @Override
    public void load() {
        mod.load();
    }
    
    @Override
    public boolean onTickInGame(float partialTick, Minecraft mc) {
        return mod.onTickInGame(partialTick, mc);
    }
    
    @Override
    public void addRenderer(Map map) {
        mod.addRenderer(map);
    }
    
    @Override
    public void keyboardEvent(KeyBinding keybinding) {
        mod.keyboardEvent(keybinding);
    }
    
    @Override
    public void modsLoaded() {
        mod.modsLoaded();
    }
}
