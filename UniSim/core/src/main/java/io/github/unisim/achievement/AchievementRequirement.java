package io.github.unisim.achievement;

import io.github.unisim.world.World;

/**
 * The requirement for the achievement to be displayed.
 */
public interface AchievementRequirement {
    boolean checkAchieved(World world);
}

