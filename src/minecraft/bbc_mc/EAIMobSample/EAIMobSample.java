package bbc_mc.EAIMobSample;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModelSkeleton;
import net.minecraft.src.RenderBiped;
import net.minecraft.src.mod_EAIMobSample;

public class EAIMobSample {
    
    mod_EAIMobSample mod;
    
    public EAIMobSample(mod_EAIMobSample mod) {
        this.mod = mod;
    }
    
    public String getVersion() {
        return "1.2.5-1.0.0";
    }
    
    public void load() {
        ModLoader.setInGameHook(this.mod, true, false);
        ModLoader.setInGUIHook(this.mod, true, true);
        
        ModLoader.registerEntityID(EAI_EntitySample.class, "EasyAIInterface.Sample", ModLoader.getUniqueEntityId(), 0x333333, 0x000000);
        
        ModLoader.addLocalization("entity.EasyAIInterface.Sample.name", "en_US", "Steve the slender");
        ModLoader.addLocalization("entity.EasyAIInterface.Sample.name", "ja_JP", "すっきりスティーブ");
        
        ModLoader.addLocalization("Steve_Inventory", "en_US", "Steve's Inventory");
        ModLoader.addLocalization("Steve_Inventory", "ja_JP", "スティーブの持ち物");
    }
    
    public boolean onTickInGame(float partialTick, Minecraft mc) {
        return false;
    }
    
    public void addRenderer(Map map) {
        map.put(EAI_EntitySample.class, new RenderBiped(new ModelSkeleton(), 0.5F));
    }
    
    public void keyboardEvent(KeyBinding keybinding) {
        
    }
    
    public void modsLoaded() {
        
    }
}
