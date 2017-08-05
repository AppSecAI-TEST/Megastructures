package elec0.megastructures.universe;


import elec0.megastructures.general.Vector2i;
import elec0.megastructures.general.Vector2l;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Galaxy extends Location
{
	private static final double SECTOR_DENSITY = 0.1; // Probability of a solar system spawning in a subsector


	private List<SolarSystem> solarSystems;
	private HashMap<Vector2i, List<Integer>> sectorMap;

	public Galaxy(long seed)
	{
		super(seed);
		solarSystems = new ArrayList<>();
		// This makes each dimension's seed unique, but still able to get the same solar systems out of the same minecraft seed
		setSeed(seed);
	}
	public Galaxy(World world)
	{
		super();
		solarSystems = new ArrayList<>();
		// This makes each dimension's seed unique, but still able to get the same solar systems out of the same minecraft seed
		// Not using linearly related seeds
		setSeed(world.getSeed() + (long)Math.pow(world.provider.getDimension(), 3));
	}

	/**
	 * First time galaxy generation. Should only be called once per dimension
	 * Will generate a certain amount of solar systems close to the overworld
	 */
	public void generate()
	{
		Random rand = new Random(getSeed());

		setName("Milky Way"); // Randomly generate this eventually

		// The galaxy's position is the 'center' of the galaxy, namely where the overworld system will be placed.
		setPosition(new Vector2l(rand.nextInt(), rand.nextInt()));
		// The galaxy's sector is the same as it's position
		setSector(positionToSector(getPosition()));

		// Generate the overworld solar system custom, since it needs specific planets
		SolarSystem overSystem = SolarSystem.generateOverSystem(rand.nextLong());
		overSystem.setPosition(getPosition());
		overSystem.setSector(positionToSector(overSystem.getPosition()));
		solarSystems.add(overSystem);

		// Loop through each square subsector
		// subsector 0,0 is top left of the sector
		for(int i = 0; i < SUBSECTORS; ++i)
		{
			for(int j = 0; j < SUBSECTORS; ++j)
			{
				// Determine if a solar system should be placed in this subsector
				if(rand.nextDouble() < SECTOR_DENSITY)
				{
					// Since we set the seed specifically at the beginning of generation, all numbers will be generated
					//		the same, given the order. It's better than doing math on the actual seed.
					SolarSystem curSS = new SolarSystem(rand.nextLong());
					curSS.setSector(getSector()); // The generate method has to know what the sector is
					Vector2l curPos = sectorToPositon(getSector());
					// Set the system's position to the subsector position. The positions are not random anymore, but if a system is spawned
					// 	in a certain position is now random.
					curSS.setPosition(new Vector2l(curPos.getX() + i * SUBSECTOR_SIZE, curPos.getY() + i * SUBSECTOR_SIZE));
					curSS.generate();

					solarSystems.add(curSS);
				}
			}
		}
	}

	public List<SolarSystem> getSolarSystems() { return solarSystems; }
	public void setSolarSystems(List<SolarSystem> solarSystems)
	{
		this.solarSystems = solarSystems;
	}

	public void addSolarSystem(SolarSystem system) { this.solarSystems.add(system); }

	@Override
	public String toString()
	{
		return getPosition() + ", SS: " + getSolarSystems().size();
	}

}
