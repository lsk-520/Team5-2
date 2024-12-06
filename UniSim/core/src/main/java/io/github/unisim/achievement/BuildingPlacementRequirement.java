package io.github.unisim.achievement;

import io.github.unisim.building.BuildingType;
import io.github.unisim.world.World;

import java.util.ArrayList;

public class BuildingPlacementRequirement implements  AchievementRequirement {
    public BuildingType buildingType;
    public int countNeeded;

    public BuildingType[] buildingTypes;

    public BuildingPlacementRequirement(BuildingType[] buildingTypes, int count) {
        this.buildingTypes = buildingTypes;
        countNeeded = count;
    }

    @Override
    public boolean checkAchieved(World world) {
        int total = 0;
        for (BuildingType buildingType : buildingTypes) {
            total += world.getBuildingCount(buildingType);
        }
        if (total == countNeeded) {
            return true;
        }
        return  false;
    }
}
