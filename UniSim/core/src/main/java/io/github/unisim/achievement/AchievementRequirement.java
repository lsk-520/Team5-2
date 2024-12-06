package io.github.unisim.achievement;

import io.github.unisim.world.World;

public interface AchievementRequirement {
    boolean checkAchieved(World world);
}

