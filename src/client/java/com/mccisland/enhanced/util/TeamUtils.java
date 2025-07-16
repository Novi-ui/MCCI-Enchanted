package com.mccisland.enhanced.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;

public class TeamUtils {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    
    // MCC Island team colors
    private static final int RED_TEAM_COLOR = 0xFF5555;
    private static final int BLUE_TEAM_COLOR = 0x5555FF;
    private static final int GREEN_TEAM_COLOR = 0x55FF55;
    private static final int YELLOW_TEAM_COLOR = 0xFFFF55;
    private static final int ORANGE_TEAM_COLOR = 0xFFAA00;
    private static final int PURPLE_TEAM_COLOR = 0xAA55FF;
    private static final int PINK_TEAM_COLOR = 0xFF55FF;
    private static final int CYAN_TEAM_COLOR = 0x55FFFF;
    private static final int LIME_TEAM_COLOR = 0xAAFF55;
    private static final int AQUA_TEAM_COLOR = 0x00FFAA;
    
    /**
     * Checks if a player is on the same team as the client player
     */
    public static boolean isTeammate(PlayerEntity player) {
        if (client.player == null) return false;
        
        Team playerTeam = getPlayerTeam(client.player);
        Team targetTeam = getPlayerTeam(player);
        
        if (playerTeam == null || targetTeam == null) {
            return false;
        }
        
        return playerTeam.equals(targetTeam);
    }
    
    /**
     * Gets the team of a player
     */
    public static Team getPlayerTeam(PlayerEntity player) {
        if (client.world == null) return null;
        
        Scoreboard scoreboard = client.world.getScoreboard();
        return scoreboard.getPlayerTeam(player.getGameProfile().getName());
    }
    
    /**
     * Gets the color associated with a player's team
     */
    public static int getTeamColor(PlayerEntity player) {
        Team team = getPlayerTeam(player);
        if (team == null) {
            return 0xFFFFFF; // White default
        }
        
        return getColorFromTeam(team);
    }
    
    /**
     * Gets the color from a team object
     */
    public static int getColorFromTeam(Team team) {
        if (team == null) return 0xFFFFFF;
        
        // Try to get color from team display name or prefix
        Text displayName = team.getDisplayName();
        String teamName = displayName.getString().toLowerCase();
        
        // Check team name for color keywords
        if (teamName.contains("red")) return RED_TEAM_COLOR;
        if (teamName.contains("blue")) return BLUE_TEAM_COLOR;
        if (teamName.contains("green")) return GREEN_TEAM_COLOR;
        if (teamName.contains("yellow")) return YELLOW_TEAM_COLOR;
        if (teamName.contains("orange")) return ORANGE_TEAM_COLOR;
        if (teamName.contains("purple")) return PURPLE_TEAM_COLOR;
        if (teamName.contains("pink")) return PINK_TEAM_COLOR;
        if (teamName.contains("cyan")) return CYAN_TEAM_COLOR;
        if (teamName.contains("lime")) return LIME_TEAM_COLOR;
        if (teamName.contains("aqua")) return AQUA_TEAM_COLOR;
        
        // Try to get color from formatting
        try {
            return team.getColor().getColorValue() != null ? team.getColor().getColorValue() : 0xFFFFFF;
        } catch (Exception e) {
            return 0xFFFFFF;
        }
    }
    
    /**
     * Gets the team name for display purposes
     */
    public static String getTeamDisplayName(PlayerEntity player) {
        Team team = getPlayerTeam(player);
        if (team == null) {
            return "No Team";
        }
        
        return team.getDisplayName().getString();
    }
    
    /**
     * Checks if the player is in a team-based game mode
     */
    public static boolean isInTeamGame() {
        if (client.player == null || client.world == null) return false;
        
        Scoreboard scoreboard = client.world.getScoreboard();
        Team playerTeam = scoreboard.getPlayerTeam(client.player.getGameProfile().getName());
        
        return playerTeam != null;
    }
    
    /**
     * Gets all players on the same team as the client player
     */
    public static java.util.List<PlayerEntity> getTeammates() {
        if (client.world == null || client.player == null) {
            return java.util.Collections.emptyList();
        }
        
        Team playerTeam = getPlayerTeam(client.player);
        if (playerTeam == null) {
            return java.util.Collections.emptyList();
        }
        
        return client.world.getPlayers().stream()
            .filter(player -> player != client.player)
            .filter(player -> {
                Team team = getPlayerTeam(player);
                return team != null && team.equals(playerTeam);
            })
            .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Gets all enemy players (players not on the same team)
     */
    public static java.util.List<PlayerEntity> getEnemies() {
        if (client.world == null || client.player == null) {
            return java.util.Collections.emptyList();
        }
        
        Team playerTeam = getPlayerTeam(client.player);
        
        return client.world.getPlayers().stream()
            .filter(player -> player != client.player)
            .filter(player -> {
                Team team = getPlayerTeam(player);
                return playerTeam == null || team == null || !team.equals(playerTeam);
            })
            .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Determines if this is likely an MCC Island server based on scoreboard teams
     */
    public static boolean isMCCIslandServer() {
        if (client.world == null) return false;
        
        Scoreboard scoreboard = client.world.getScoreboard();
        
        // Check for common MCC Island team patterns
        for (String teamName : scoreboard.getTeamNames()) {
            String name = teamName.toLowerCase();
            if (name.contains("red") || name.contains("blue") || name.contains("green") || 
                name.contains("yellow") || name.contains("orange") || name.contains("purple") ||
                name.contains("pink") || name.contains("cyan") || name.contains("lime") || 
                name.contains("aqua")) {
                return true;
            }
        }
        
        return false;
    }
}