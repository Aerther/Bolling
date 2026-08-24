package football.model.enums;

import java.awt.Color;
import java.util.Random;

public enum Material {

    RUBBER(0.95f, 1100f, Color.pink),
    WOOD(0.60f, 700f, Color.orange),
    STEEL(0.85f, 7850f, Color.white),
    ICE(0.98f, 917f, Color.blue),
    CONCRETE(0.40f, 2400f, Color.gray);

    private final float restitution;
    private final float density;
    private final Color color;

    Material(float restitution, float density, Color color) {
        this.restitution = restitution;
        this.density = density;
        this.color = color;
    }
    
    public Color getColor() {
    	return this.color;
    }

    public float getRestitution() {
        return restitution;
    }

    public float getDensity() {
        return density;
    }
    
    public static Material getRandomMaterial() {
    	Random random = new Random();
    	
    	Material[] materials = Material.values();
    	
    	return materials[random.nextInt(materials.length)];
    }
}

