package wesley.magic.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class DarkMagicItem extends Item {

    // Class for building lore better
    public static class Lore implements Iterable<Text> {
        private List<Text> _lore = new ArrayList<>();

        public Lore() {}

        public Lore add(Text line) {
            _lore.add(line);
            return this;
        }

        @Override
        public Iterator<Text> iterator() {
            return _lore.iterator();
        }
    }

    private Lore _lore;

    public DarkMagicItem(Settings settings) {
        super(settings);
        _lore = new Lore();
    }

    public DarkMagicItem(Lore lore, Settings settings) {
        super(settings);
        if (lore != null) {
            this._lore = lore;
        } else {
            this._lore = new Lore();
        }
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        for (Text text : _lore) {
            tooltip.add(text);
        }
    }
}
