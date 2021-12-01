package org.jwybw.battlecity.component.entities;

import org.jwybw.battlecity.Directions;
import org.jwybw.battlecity.component.Component;
import org.jwybw.battlecity.frame.GameFrame;
import org.jwybw.battlecity.frame.MapPanel;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;

class Point {
	int x, y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

@SuppressWarnings("SuspiciousNameCombination")
public class BotTank extends Tank {
	private final Random random = new Random();

	protected int botAttackTimer = 80;
	protected int simpleAttack = 80, preciseAttack = 100;

	private Stack<Node> pathStack;
	private int avoidBrick = 0;
	private String target = "NONE";
	private Node nextNode;

	public BotTank(int x, int y, MapPanel mapPanel) {
		super(x, y, 0, mapPanel);
		this.speed = 1;

		mapPanel.startThread(new TickThread());
	}

	public void botAI() {
		if (!movable) {
			return;
		}

		boolean walkState = true;
		if (Objects.equals(target, "COMPONENT") &&
			pathStack != null && pathStack.size() > 1 &&
			random.nextInt(2) == 0 && botAttackTimer <= 0
		) {
			int nowX = getX() / 16, nowY = getY() / 16;
			int endX = pathStack.firstElement().y, endY = pathStack.firstElement().x;
			if (Math.abs(endY - nowY) <= 1 && Math.abs(endX - nowX) <= 4) {
				if (direction == Directions.Right && endX > nowX || direction == Directions.Left && endX < nowX) {
					if (attack("bot")) {
						botAttackTimer += preciseAttack;
						System.out.println(random.nextInt(10) + "左右");
					}
				}
			} else if (Math.abs(endX - nowX) <= 1 && Math.abs(endY - nowY) <= 4) {
				if (direction == Directions.Down && endY > nowY || direction == Directions.Up && endY < nowY) {
					if (attack("bot")) {
						botAttackTimer += preciseAttack;
						System.out.println(random.nextInt(10) + "上下");
					}
				}
			}
		} else if (botAttackTimer <= 0 && attack("bot")) {
			botAttackTimer += simpleAttack;
		} else {
			botAttackTimer--;
		}
		if (getX() % 16 != 0 && (direction == Directions.Left || direction == Directions.Right)) {
//横向未对齐
			switch (direction) {
				case Left -> {
					walkState = leftWard();
					if (!walkState) {
						rightWard();
					}
				}
				case Right -> {
					walkState = rightWard();
					if (!walkState) {
						leftWard();
					}
				}
			}
		} else if (getY() % 16 != 0 && (direction == Directions.Up || direction == Directions.Down)) {
//纵向未对齐
			switch (direction) {
				case Up -> {
					walkState = upWard();
					if (!walkState) {
						downWard();
					}
				}
				case Down -> {
					walkState = downWard();
					if (!walkState) {
						upWard();
					}
				}
			}
		} else if (Objects.equals(target, "NONE")) {
			Component targetComp = null;
			int targetDistance = Integer.MAX_VALUE;
			if (random.nextInt(2) != 0) {
				target = "COMPONENT";
				if (mapPanel.eagleBlock.isExist()) {
					targetComp = mapPanel.eagleBlock;
					pathStack = findRoad(avoidBrick, getX(), getY(), targetComp.getX(), targetComp.getY());
					targetDistance = getDistance(pathStack);
					nextNode = pathStack.pop();
				}
				for (Tank tank : mapPanel.tankList) {
					if (tank.isAlive()) {
						Stack<Node> tankStack = findRoad(avoidBrick, getX(), getY(), tank.getX(), tank.getY());
						int tankDistance = getDistance(tankStack);
						if (targetDistance > tankDistance) {
							targetComp = tank;
							pathStack = tankStack;
							targetDistance = tankDistance;
							nextNode = pathStack.pop();
						}
					}
				}

				if (targetComp == null) {
					//没目标了
					target = "NONE";
				} else if (targetDistance == Integer.MAX_VALUE) {
					//有目标，但过不去
					avoidBrick = 5;
					target = "NONE";
				} else {
					//正常
					avoidBrick--;
				}
			} else {
				target = "POINT";
				Point point = new Point(random.nextInt(26), random.nextInt(26));
				while (mapPanel.mapArray[point.y][point.x] != 0) {
					point.x = random.nextInt(26);
					point.y = random.nextInt(26);
				}
				pathStack = findRoad(0, getX(), getY(), point.x * 16, point.y * 16);
				nextNode = pathStack.pop();

				if (getDistance(pathStack) == Integer.MAX_VALUE) {
					//有目标，但过不去
					target = "NONE";
				}
			}
// System.out.println("$ " + getX() / 32 + " " + getY() / 32 + " " + nextNode.x + " " + nextNode.y);
		} else {
			if (pathStack.size() <= 2) {
				if (Objects.equals(target, "COMPONENT")) {
					botAttackTimer--;
				}
				walkState = false;
			} else {
				int nowX = getX() / 16, nowY = getY() / 16;
				int nodeX = nextNode.y, nodeY = nextNode.x;
				while (Math.abs(nodeX * 16 - getX()) <= speed && Math.abs(nodeY * 16 - getY()) <= speed) {
					nextNode = pathStack.pop();
					nodeX = nextNode.y;
					nodeY = nextNode.x;
				}
				if (nodeX == nowX) {
					if (nodeY > nowY) {
						walkState = downWard();
					} else if (nodeY < nowY) {
						walkState = upWard();
					}
				} else if (nodeY == nowY) {
					if (nodeX > nowX) {
						walkState = rightWard();
					} else {
						walkState = leftWard();
					}
				}
			}
		}

		if (!walkState && random.nextInt(10) == 0) {
			target = "NONE";
		}
	}

	public Stack<Node> findRoad(int avoidBrick, int startX, int startY, int endX, int endY) {
		mapPanel.updateMapArray(this, avoidBrick > 0);
		AStar as = new AStar(mapPanel.mapArray, 26, 26, startY / 16, startX / 16, endY / 16, endX / 16);
		// System.out.print("*");
		// for (Node node : testStack) {
		// 	System.out.print("<-(" + node.x + "," + node.y + ")");
		// }
		// System.out.println("<-" + "(" + startX / 32 + "," + startY / 32 + ")");
		return as.AStarSearch();
	}

	public int getDistance(Stack<Node> stack) {
		if (stack.size() == 2) {
			Node first = stack.firstElement(), last = stack.lastElement();
			if (Math.abs(first.x - last.x) > 1 || Math.abs(first.y - last.y) > 1) {
				//只有两项，但点不连续，则说明没找到路径
				return Integer.MAX_VALUE;
			}
		}
		return stack.size() - 1;
	}

	//x、y是反的
	static class Node {
		public int F, G, H;
		public int x, y;
		public boolean closed;
		public int val;
		Node father;

		public Node(int x, int y, int val) {
			this.x = x;
			this.y = y;
			this.val = val;
			G = 2147483647;
			closed = false;
		}
	}

	static class AStar {
		Node[][] map;
		int startX, startY, endX, endY;
		int lengthX, lengthY;
		ArrayList<Node> openList;

		public AStar(int[][] mapTemp, int lx, int ly, int sx, int sy, int ex, int ey) {
			this.lengthX = lx;
			this.lengthY = ly;
			this.startX = sx;
			this.startY = sy;
			this.endX = ex;
			this.endY = ey;
			this.map = new Node[lx + 1][ly + 1];
			for (int i = 0; i <= 25; i++) {
				for (int j = 0; j <= 25; j++) {
					this.map[i][j] = new Node(i, j, mapTemp[i][j]);
				}
			}

			openList = new ArrayList<>();
		}

		private int calH(int x, int y) {
			return 10 * (Math.abs(x - endX) + Math.abs(y - endY));
		}

		public Stack<Node> AStarSearch() {
			openList.add(map[startX][startY]);
			map[startX][startY].H = this.calH(startX, startY);
			map[startX][startY].G = 0;
			map[startX][startY].F = map[startX][startY].H + map[startX][startY].G;
			int fMin = 2147483647;
			while (!openList.isEmpty()) {
				int currentNodeIndex = 0;
				for (int i = 0; i < openList.size(); i++) {
					if (openList.get(i).F < fMin) {
						currentNodeIndex = i;
						fMin = openList.get(i).F;
					}
				}
				Node currentNode = openList.get(currentNodeIndex);
				Node nextNode;
				currentNode.closed = true;
				openList.remove(currentNodeIndex);
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (currentNode.x + i == endX && currentNode.y + j == endY) {
							Node current = currentNode;
							Stack<Node> sta = new Stack<>();
							sta.push(map[endX][endY]);
							while (current.father != null) {
								sta.push(current);
								current = current.father;
							}
							return sta;
						}
						if (currentNode.x + i > -1 && currentNode.y + j > -1 && currentNode.x + i <= lengthX - 1 && currentNode.y + j <= lengthY - 1
							&& map[currentNode.x + i][currentNode.y + j].val == 0 && !map[currentNode.x + i][currentNode.y + j].closed) {
							if (Math.abs(i) + Math.abs(j) != 2) {
								nextNode = map[currentNode.x + i][currentNode.y + j];
							} else {
								continue;
							}
							if (!openList.contains(nextNode)) {
								openList.add(nextNode);
								nextNode.father = currentNode;
//水平（竖直）移动的情况
								nextNode.G = currentNode.G + 10;
								nextNode.H = 10 * (Math.abs(nextNode.x - endX) + Math.abs(nextNode.y - endY));
								nextNode.F = nextNode.G + nextNode.H;

							} else {
								if (currentNode.G + 10 < nextNode.G) {
									nextNode.G = currentNode.G + 10;
									nextNode.H = 10 * (Math.abs(nextNode.x - endX) + Math.abs(nextNode.y - endY));
									nextNode.F = nextNode.G + nextNode.H;
									nextNode.father = currentNode;
								}
							}
						}
					}
				}
			}
			Stack<Node> sta = new Stack<>();
			sta.push(map[endX][endY]);
			return sta;
		}
	}

	protected class TickThread extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					botAI();
					Thread.sleep(GameFrame.FRESH);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}