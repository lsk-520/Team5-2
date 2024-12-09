package io.github.unisim.achievement;

import io.github.unisim.world.World;

/**
 * A message to the user at the beginning of the game.
 */
public class WelcomeRequirement implements AchievementRequirement {
    public boolean checkAchieved(World world) { return true; }
}
