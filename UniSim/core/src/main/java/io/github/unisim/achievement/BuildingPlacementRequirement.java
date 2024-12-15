package io.github.unisim.achievement;

import com.badlogic.gdx.Game;
import io.github.unisim.GameState;
import io.github.unisim.building.BuildingType;
import io.github.unisim.world.World;

/**
 * A requirement that depends on the number of buildings placed at different times throughout the game.
 */
public class BuildingPlacementRequirement implements AchievementRequirement {
    public int countNeeded;
    public int time;

    public BuildingType[] buildingTypes;

    /**
     * Creates a requirement dependent on the number of buildings placed at any point through the game.
     *
     * @param buildingTypes The types of buildings that will count towards the total placed.
     * @param count The number of buildings needed to be placed.
     */
    public BuildingPlacementRequirement(BuildingType[] buildingTypes, int count) {
        this.buildingTypes = buildingTypes;
        countNeeded = count;
        time = 300_000;
    }

    /**
     * Creates a requirement dependent on the number of buildings placed after a specific point in the game.
     *
     * @param buildingTypes The types of buildings that will count towards the total placed.
     * @param count The number of buildings needed to be placed.
     * @param time The time after which the achievement can be achieved.
     */
    public BuildingPlacementRequirement(BuildingType[] buildingTypes, int count, int time) {
        this.buildingTypes = buildingTypes;
        countNeeded = count;
        this.time = time;
    }

    /**
     * @return True if both the building total and time requirement have been met, false otherwise.
     */
    @Override
    public boolean checkAchieved(World world) {
        int total = 0;
        for (BuildingType buildingType : buildingTypes) {
            total += world.getBuildingCount(buildingType);
        }
        if (total == countNeeded && world.getRemainingTime() < time) {
            return true;
        }
        return false;
    }
}
