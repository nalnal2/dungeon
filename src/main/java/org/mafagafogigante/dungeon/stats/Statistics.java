package org.mafagafogigante.dungeon.stats;

import org.mafagafogigante.dungeon.commands.IssuedCommand;
import org.mafagafogigante.dungeon.io.Writer;
import org.mafagafogigante.dungeon.util.Table;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Statistics class that stores, processes, and writes game statistics.
 */
public final class Statistics implements Serializable {

  private final WorldStatistics worldStatistics = new WorldStatistics();
  private final ExplorationStatistics explorationStatistics = new ExplorationStatistics();
  private final BattleStatistics battleStatistics = new BattleStatistics();
  private final CommandStatistics commandStatistics = new CommandStatistics();

  /**
   * Returns the WorldStatistics object of this Statistics.
   *
   * @return a WorldStatistics object.
   */
  public WorldStatistics getWorldStatistics() {
    return worldStatistics;
  }

  public ExplorationStatistics getExplorationStatistics() {
    return explorationStatistics;
  }

  /**
   * Returns the BattleStatistics object of this Statistics.
   *
   * @return a BattleStatistics object.
   */
  public BattleStatistics getBattleStatistics() {
    return battleStatistics;
  }

  /**
   * Adds an issued command to the CommandStatistics.
   */
  public void addCommand(IssuedCommand issuedCommand) {
    commandStatistics.addCommand(issuedCommand);
  }

  /**
   * Writes most of the available statistics.
   */
  public void writeStatistics() {
    Table statistics = new Table("Property", "Value");
    insertCommandStatistics(statistics);
    statistics.insertSeparator();
    insertWorldStatistics(statistics);
    Writer.write(statistics);
  }

  private void insertCommandStatistics(Table statistics) {
    int commandCount = commandStatistics.getCommandCount();
    int chars = commandStatistics.getChars();
    int words = commandStatistics.getWords();
    statistics.insertRow("Commands issued", String.valueOf(commandCount));
    statistics.insertRow("Characters entered", String.valueOf(chars));
    statistics.insertRow("Average characters per command", String.format("%.2f", (double) chars / commandCount));
    statistics.insertRow("Words entered", String.valueOf(words));
    statistics.insertRow("Average words per command", String.format("%.2f", (double) words / commandCount));
  }

  private void insertWorldStatistics(Table statistics) {
    statistics.insertRow("Created Locations", String.valueOf(worldStatistics.getLocationCount()));
    SortedSet<String> locationNames = new TreeSet<>(worldStatistics.getLocationCounter().keySet());
    for (String name : locationNames) {
      statistics.insertRow("  " + name, String.valueOf(worldStatistics.getLocationCounter().getCounter(name)));
    }
    statistics.insertSeparator();
    statistics.insertRow("Spawned Creatures", String.valueOf(worldStatistics.getSpawnCount()));
    SortedSet<String> spawnNames = new TreeSet<>(worldStatistics.getSpawnCounter().keySet());
    for (String name : spawnNames) {
      statistics.insertRow("  " + name, String.valueOf(worldStatistics.getSpawnCounter().getCounter(name)));
    }
  }

}
