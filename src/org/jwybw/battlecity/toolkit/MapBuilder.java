package org.jwybw.battlecity.toolkit;

import org.jwybw.battlecity.component.Maps;
import org.jwybw.battlecity.component.blocks.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class MapBuilder {
	public static Maps readMap(int level) throws IOException {
		File mapFile = new File("maps/" + level + ".map");
		Properties filePro = new Properties();

		//读取方块
		List<Block> blockList = new ArrayList<>();
		filePro.load(new FileInputStream(mapFile));//FileInputStream 读取文件并存入输入流 Properties.load从输入流中获取键值序列并存入\
		blockList.addAll(parseBlockLine(filePro.get("brick"), "brick"));
		blockList.addAll(parseBlockLine(filePro.get("grass"), "grass"));
		blockList.addAll(parseBlockLine(filePro.get("river"), "river"));
		blockList.addAll(parseBlockLine(filePro.get("iron"), "iron"));
		blockList.addAll(parseBlockLine(filePro.get("gravel"), "gravel"));

		//读取坦克
		Map<String, Integer> botTanks = new HashMap<>();
		botTanks.put("basicBot", Integer.parseInt((String) filePro.getOrDefault("basicBot", "0"), 10));
		botTanks.put("fastBot", Integer.parseInt((String) filePro.getOrDefault("fastBot", "0"), 10));
		botTanks.put("powerBot", Integer.parseInt((String) filePro.getOrDefault("powerBot", "0"), 10));
		botTanks.put("armorBot", Integer.parseInt((String) filePro.getOrDefault("armorBot", "0"), 10));
		return new Maps(level, blockList, botTanks);
	}

	public static List<Block> parseBlockLine(Object data, String type) {
		List<Block> blockList = new LinkedList<>();
		if (data == null) {
			return blockList;
		}
		String[] blocks = ((String) data).split(";");
		if (blocks.length == 0) {
			return blockList;
		}
		switch (type) {
			case "brick":
				for (String wStr : blocks) {
					String[] singleBlock = wStr.split(",");
					int x = Integer.parseInt(singleBlock[0], 10), y = Integer.parseInt(singleBlock[1], 10);
					blockList.add(new BrickBlock(x, y));
					blockList.add(new BrickBlock(x, y + 8));
					blockList.add(new BrickBlock(x + 8, y));
					blockList.add(new BrickBlock(x + 8, y + 8));
				}
				break;
			case "grass":
				for (String wStr : blocks) {
					String[] singleBlock = wStr.split(",");
					blockList.add(new GrassBlock(Integer.parseInt(singleBlock[0], 10), Integer.parseInt(singleBlock[1], 10)));
				}
				break;
			case "river":
				for (String wStr : blocks) {
					String[] singleBlock = wStr.split(",");
					blockList.add(new RiverBlock(Integer.parseInt(singleBlock[0], 10), Integer.parseInt(singleBlock[1], 10)));
				}
				break;
			case "iron":
				for (String wStr : blocks) {
					String[] singleBlock = wStr.split(",");
					blockList.add(new IronBlock(Integer.parseInt(singleBlock[0], 10), Integer.parseInt(singleBlock[1], 10)));
				}
				break;
			case "gravel":
				for (String wStr : blocks) {
					String[] singleBlock = wStr.split(",");
					blockList.add(new GravelBlock(Integer.parseInt(singleBlock[0], 10), Integer.parseInt(singleBlock[1], 10)));
				}
				break;
		}
		blockList.add(new EagleBlock(192, 384));
		return blockList;
	}
}
