package org.jwybw.battlecity.component;

import org.jwybw.battlecity.component.blocks.Block;

import java.util.List;
import java.util.Map;

public class Maps {
	public int level;
	public int unusedBot;
	public int unusedBasicBot, unusedFastBot, unusedPowerBotBot, unusedArmorBot;
	public List<Block> blockList;
	public Map<String, Integer> botTanks;

	public Maps(int level, List<Block> blockList, Map<String, Integer> botTanks) {
		this.level = level;
		this.blockList = blockList;
		this.botTanks = botTanks;
		this.unusedBot = botTanks.getOrDefault("basicBot", 0) +
			botTanks.getOrDefault("fastBot", 0) +
			botTanks.getOrDefault("powerBot", 0) +
			botTanks.getOrDefault("armorBot", 0);
		this.unusedBasicBot = botTanks.getOrDefault("basicBot", 0);
		this.unusedFastBot = botTanks.getOrDefault("fastBot", 0);
		this.unusedPowerBotBot = botTanks.getOrDefault("powerBot", 0);
		this.unusedArmorBot = botTanks.getOrDefault("armorBot", 0);
	}
}