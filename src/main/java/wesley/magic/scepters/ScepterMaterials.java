package wesley.magic.scepters;

public enum ScepterMaterials {

    DIAMOND (7.0f, 10);
    
    private final double damage;
    private final int cooldownTicks;
    ScepterMaterials(double damage, int cooldownTicks) {
        this.damage = damage;
        this.cooldownTicks = cooldownTicks;
    }
}
