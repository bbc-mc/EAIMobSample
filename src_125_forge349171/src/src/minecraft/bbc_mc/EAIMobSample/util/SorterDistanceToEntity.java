package bbc_mc.EAIMobSample.util;

/**
 * Sort by Distance from BASE-Entity
 *
 * @author bbc_mc
 * @date 2012.06.25
 */

import java.util.Comparator;

import net.minecraft.src.Entity;

public class SorterDistanceToEntity implements Comparator {
    private Entity sourceEntity;
    
    public SorterDistanceToEntity(Entity par1sourceEntity) {
        this.sourceEntity = par1sourceEntity;
    }
    
    @Override
    public int compare(Object o1, Object o2) {
        float dist1 = this.sourceEntity.getDistanceToEntity((Entity) o1);
        float dist2 = this.sourceEntity.getDistanceToEntity((Entity) o2);
        
        if (dist1 < dist2) {
            return -1;
        } else if (dist1 > dist2) {
            return 1;
        } else {
            return 0;
        }
    }
}