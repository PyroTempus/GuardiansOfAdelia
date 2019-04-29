package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.AttackSpeed;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponMelee;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Daggers {

    public static ItemStack get(int placementNumber, ItemTier tier, String itemTag, double bonusPercent, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Short Dagger";
        Material material = Material.DIAMOND_HOE;
        int customModelDataId = 10000001;
        int level = 1;
        RPGClass rpgClass = RPGClass.NINJA;
        int damage = 5;
        AttackSpeed attackSpeed = AttackSpeed.FAST;
        int itemID = 201;

        if (placementNumber == 2) {
            name = "Steel Dagger";
            customModelDataId = 10000002;
            level = 10;
            damage = 20;
            itemID = 202;
        } else if (placementNumber == 3) {
            name = "Katana";
            customModelDataId = 10000003;
            level = 20;
            damage = 50;
            itemID = 203;
        } else if (placementNumber == 4) {
            name = "Crimson Dagger";
            customModelDataId = 10000004;
            level = 30;
            damage = 125;
            itemID = 204;
        } else if (placementNumber == 5) {
            name = "Jade Dagger";
            customModelDataId = 10000005;
            level = 40;
            damage = 200;
            itemID = 205;
        } else if (placementNumber == 6) {
            name = "Frozen Dagger";
            customModelDataId = 10000006;
            level = 50;
            damage = 325;
            itemID = 206;
        } else if (placementNumber == 7) {
            name = "Shadow Claws";
            customModelDataId = 10000008;
            level = 60;
            damage = 450;
            itemID = 207;
        } else if (placementNumber == 8) {
            name = "Dagger of Doom";
            customModelDataId = 10000010;
            level = 70;
            damage = 600;
            itemID = 208;
        } else if (placementNumber == 9) {
            name = "Blue Wind Dagger";
            customModelDataId = 10000012;
            level = 80;
            damage = 750;
            itemID = 209;
        } else if (placementNumber == 10) {
            name = "Twin Blades of Shadow";
            customModelDataId = 10000014;
            level = 90;
            damage = 1000;
            itemID = 210;
        }

        final WeaponMelee weapon = new WeaponMelee(name, tier, itemTag, material, customModelDataId, level, rpgClass, damage, bonusPercent,
                attackSpeed, minStatValue, maxStatValue, minNumberofStats, itemID);
        return weapon.getItemStack();
    }
}
