package io.github.lix3nn53.guardiansofadelia.utilities.Scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class BoardWithPlayers extends ScoreboardGeneral {

    private final List<String> topLines;

    public BoardWithPlayers(List<Player> players, String name, List<String> topLines) {
        super(name);
        this.topLines = topLines;
        this.setLine("", 1);

        int lineCounter = 2;
        for (String s : topLines) {
            this.setLine(s, lineCounter);
            lineCounter++;

        }

        this.setLine(ChatColor.GREEN + "» Leader", lineCounter);
        lineCounter++;
        this.setLine(players.get(0).getName() + ChatColor.RED + " ❤" + (int) (players.get(0).getHealth() + 0.5), lineCounter);
        lineCounter++;
        this.setLine(ChatColor.GREEN + "» Members", lineCounter);
        lineCounter++;

        for (int i = 1; i < players.size(); i++) {
            Player member = players.get(i);
            this.setLine(member.getName() + ChatColor.RED + " ❤" + (int) (member.getHealth() + 0.5), lineCounter);
            lineCounter++;
        }
        for (Player p : players) {
            this.show(p);
        }
    }

    public void updateHP(String playerName, int hp) {
        if (playerName.length() > 6) {
            playerName = playerName.substring(0, 6);
        }
        for (int k : getRowLines().keySet()) {
            String s = getRowLines().get(k);
            if (s.contains(playerName)) {
                this.setLine(playerName + ChatColor.RED + " ❤" + hp, k);
                break;
            }
        }
    }

    public void remake(List<Player> players) {
        this.setLine("", 1);

        int lineCounter = 2;
        for (String s : topLines) {
            this.setLine(s, lineCounter);
            lineCounter++;

        }

        this.setLine(ChatColor.GREEN + "» Members", lineCounter);
        lineCounter++;

        for (Player member : players) {
            this.setLine(member.getName() + ChatColor.RED + " ❤" + (int) (member.getHealth() + 0.5), lineCounter);
            lineCounter++;
        }
        for (Player p : players) {
            this.show(p);
        }
    }
}