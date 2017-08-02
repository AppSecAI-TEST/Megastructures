package elec0.megastructures.universe;


import elec0.megastructures.general.Vector2i;
import elec0.megastructures.general.Vector2l;

public class Location
{
	public static int LAST_ID = 0; // Iterates every time a new object is created
	public static final int SECTOR_SIZE = 10000; // x and y size of a sector

	private long seed; // The random seed used for generating things, usually through the generate() method
	private Vector2l position; // Position of the object in space
	private Vector2i sector; // x,y sector location of the object, which correlates to the position
								// positionToSector() returns the position of the upper-left part of the sector
	private String name; // User-facing name of the object
	private int ID; // A unique ID 1-MAXINT for everything inheriting from Location, which should be pretty much everything

	public Location() { ID = ++LAST_ID; name = "";}
	public Location(long seed) { this.seed = seed; ID = ++LAST_ID; name = "";}

	public long getSeed()
	{
		return seed;
	}
	public void setSeed(long seed)
	{
		this.seed = seed;
	}

	/**
	 * Converts a position Vector2l into a sector
	 * Sector 0,0 corresponds to position 0,0
	 * Sectors are squares, sector 1,1 is (SECTOR_SIZE + 1, SECTOR_SIZE + 1)
	 * @param position
	 * @return
	 */
	public static Vector2i positionToSector(Vector2l position)
	{
		if(position == null)
			return new Vector2i(-1, -1);
		return new Vector2i((int)(position.getX() / SECTOR_SIZE), (int)(position.getY() / SECTOR_SIZE));
	}

	/**
	 * Converts a sector Vector2i to a position
	 * @param sector
	 * @return
	 */
	public static Vector2l sectorToPositon(Vector2i sector)
	{
		if(sector == null)
			return new Vector2l(-1, -1);
		return new Vector2l(sector.getX() * SECTOR_SIZE, sector.getY() * SECTOR_SIZE);
	}

	public Vector2l getPosition()
	{
		return position;
	}
	public void setPosition(Vector2l position)
	{
		this.position = position;
	}


	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public int getID()
	{
		return ID;
	}
	public void setID(int ID)
	{
		this.ID = ID;
	}

	public Vector2i getSector()
	{
		return sector;
	}
	public void setSector(Vector2i sector)
	{
		this.sector = sector;
	}
}
