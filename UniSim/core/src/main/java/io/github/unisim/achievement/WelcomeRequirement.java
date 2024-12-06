package io.github.unisim.achievement;

import io.github.unisim.world.World;

public class WelcomeRequirement implements AchievementRequirement {
    public boolean checkAchieved(World world) { return true; }
}
