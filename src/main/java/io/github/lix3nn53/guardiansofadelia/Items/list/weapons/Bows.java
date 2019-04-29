package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponRanged;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Bows {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Short Bow";
        Material material = Material.BOW;
        int customModelDataId = 10000001;
        int level = 1;
        RPGClass rpgClass = RPGClass.ARCHER;
        int rangedDamage = 10;
        AttackSpeed attackSpeed = AttackSpeed.SLOW;
        int itemID = 101;

        if (placementNumber == 2) {
            name = "Light Bow";
            customModelDataId = 10000002;
            level = 10;
            rangedDamage = 40;
            itemID = 102;
        } else if (placementNumber == 3) {
            name = "Crossbow";
            customModelDataId = 10000003;
            level = 20;
            rangedDamage = 100;
            itemID = 103;
        } else if (placementNumber == 4) {
            name = "Battle Bow";
            customModelDataId = 10000004;
            level = 30;
            rangedDamage = 250;
            itemID = 104;
        } else if (placementNumber == 5) {
            name = "Satet Bow";
            customModelDataId = 10000005;
            level = 40;
            rangedDamage = 400;
            itemID = 105;
        } else if (placementNumber == 6) {
            name = "Leaf Fairy Bow";
            customModelDataId = 10000006;
            level = 50;
            rangedDamage = 650;
            itemID = 106;
        } else if (placementNumber == 7) {
            name = "Crossbow of Doom";
            customModelDataId = 10000008;
            level = 60;
            rangedDamage = 900;
            itemID = 107;
        } else if (placementNumber == 8) {
            name = "Unicorn Bow";
            customModelDataId = 10000010;
            level = 70;
            rangedDamage = 1200;
            itemID = 108;
        } else if (placementNumber == 9) {
            name = "Zephyr Bow";
            customModelDataId = 10000012;
            level = 80;
            rangedDamage = 1500;
            itemID = 109;
        } else if (placementNumber == 10) {
            name = "Arcade Bow";
            customModelDataId = 10000014;
            level = 90;
            rangedDamage = 2000;
            itemID = 110;
        }

        int damage = rangedDamage / 4;

        final WeaponRanged weapon = new WeaponRanged(name, tier, itemTag, material, customModelDataId, level, rpgClass, damage, rangedDamage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats, itemID);
        return weapon.getItemStack();
    }
}
