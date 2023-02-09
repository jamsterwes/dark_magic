package wesley.magic.state;

import java.util.UUID;

public class DevourState {
    public UUID player;
    public boolean feeding;
    
    public DevourState(UUID player, boolean feeding) {
        this.player = player;
        this.feeding = feeding;
    }
}
