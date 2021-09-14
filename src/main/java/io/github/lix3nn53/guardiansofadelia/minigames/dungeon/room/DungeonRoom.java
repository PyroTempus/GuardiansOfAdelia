package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.SkillOnGroundWithOffset;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DungeonRoom {

    private final List<DungeonRoomDoor> doors;
    private final HashMap<Integer, List<DungeonRoomSpawner>> waveToSpawners;
    private final List<SkillOnGroundWithOffset> skillsOnGround;
    /**
     * Rooms to start after this room ends
     */
    private final List<Integer> nextRooms;

    public DungeonRoom(List<DungeonRoomDoor> doors, HashMap<Integer, List<DungeonRoomSpawner>> waveToSpawners,
                       List<SkillOnGroundWithOffset> skillsOnGround, List<Integer> nextRooms) {
        this.doors = doors;
        this.waveToSpawners = waveToSpawners;
        this.skillsOnGround = skillsOnGround;
        this.nextRooms = nextRooms;
    }

    public void onDungeonStart(Location dungeonStart) {
        for (DungeonRoomDoor door : doors) {
            door.close(dungeonStart);
        }
    }

    public void onRoomStart(DungeonRoomState state, Location dungeonStart) {
        for (DungeonRoomDoor door : doors) {
            door.close(dungeonStart);
        }

        List<DungeonRoomSpawner> spawners = waveToSpawners.get(1);
        for (DungeonRoomSpawner spawner : spawners) {
            List<Entity> spawned = spawner.spawn(dungeonStart);
            state.onMobSpawn(spawned.size());
        }

        for (SkillOnGroundWithOffset skillOnGround : skillsOnGround) {
            skillOnGround.activate(dungeonStart, 40L);
        }
    }

    /**
     * @param players
     * @param roomNo
     * @return true if room completed, false otherwise
     */
    public boolean onMobKill(DungeonRoomState state, List<Player> players, int roomNo, String mobCode, Location dungeonStart) {
        boolean thisRoomsMob = false;

        int currentWave = state.getCurrentWave();

        List<DungeonRoomSpawner> spawners = waveToSpawners.get(currentWave);
        for (DungeonRoomSpawner spawner : spawners) {
            String spawnerMobCode = spawner.getMobCode();
            if (spawnerMobCode.equals(mobCode)) {
                thisRoomsMob = true;
                break;
            }
        }

        if (!thisRoomsMob) {
            return false;
        }

        state.onMobKill(1);

        if (state.isClear()) {
            currentWave++;

            if (waveToSpawners.containsKey(currentWave)) {
                state.onNextWaveStart();

                for (Player player : players) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "ROOM-" + roomNo + " WAVE-" + currentWave + " incoming!");
                }

                spawners = waveToSpawners.get(currentWave);

                for (DungeonRoomSpawner spawner : spawners) {
                    List<Entity> spawned = spawner.spawn(dungeonStart);
                    state.onMobSpawn(spawned.size());
                }
            } else {
                for (Player player : players) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "ROOM-" + roomNo + " completed!");
                }

                return true;
            }
        }

        return false;
    }

    public List<Integer> onRoomEnd(Location dungeonStart) {
        for (DungeonRoomDoor door : doors) {
            door.open(dungeonStart);
        }
        for (SkillOnGroundWithOffset skillOnGround : skillsOnGround) {
            skillOnGround.deactivate();
        }

        return nextRooms;
    }

    public void addSpawner(int wave, DungeonRoomSpawner spawner) {
        List<DungeonRoomSpawner> spawners = new ArrayList<>();
        if (waveToSpawners.containsKey(wave)) {
            spawners = waveToSpawners.get(wave);
        }

        spawners.add(spawner);
        waveToSpawners.put(wave, spawners);
    }

    public void addDoor(DungeonRoomDoor door) {
        doors.add(door);
    }

    public List<DungeonRoomDoor> getDoors() {
        return doors;
    }

    public HashMap<Integer, List<DungeonRoomSpawner>> getWaveToSpawners() {
        return waveToSpawners;
    }

    public List<Integer> getNextRooms() {
        return nextRooms;
    }

    public void addSkillOnGround(SkillOnGroundWithOffset skill) {
        skillsOnGround.add(skill);
    }

    public List<SkillOnGroundWithOffset> getSkillsOnGround() {
        return skillsOnGround;
    }
}
