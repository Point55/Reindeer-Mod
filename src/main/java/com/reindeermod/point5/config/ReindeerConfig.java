package com.reindeermod.point5.config;

import net.minecraftforge.common.config.Config;
import com.reindeermod.point5.util.Reference;

@Config(modid = Reference.ModID, name = "Reindeer_Mod" + Reference.VERSION + "_Config")
@Config.LangKey("config.reindeermod.title")
public class ReindeerConfig {

    @Config.Name("Gameplay: Tamed reindeer follow")
    @Config.Comment("Enable or disable tamed reindeer following")
    public static boolean reindeerFollow = true;

    @Config.Name("Gameplay: Mutation chance for reindeer")
    @Config.Comment("Chance of mutation occuring when breeding lovebirds (1/x, so the higher the number the lower the chance)")
    public static int mutationLovebird = 10;

    @Config.Name("Spawns: All spawns")
    @Config.Comment("Enables natural spawning of entities from Reindeer Mod.")
    public static boolean allSpawns = true;

    @Config.Name("Spawns: reindeer spawn rate")
    @Config.Comment("Spawn weight of reindeer. (default: 80)")
    public static int reindeerSpawnRate = 20;

}
