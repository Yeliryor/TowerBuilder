package main;

public class Material
{
	private double density;
	private double strength;
	private double elasticity;
	private double shearStrength;
	private String material;;
	
	public Material()
	{
		density = 2700;
		strength = 60000000;
		elasticity = 50.00E+9;
		shearStrength = strength;
		material = "stone";
	}

	public void setMaterial(String newMaterial)
	{
		if (newMaterial.equalsIgnoreCase("stone"))
		{
			density = 2700;
			strength = 60000000;
			shearStrength = strength;
			elasticity = 50.00E+9;
			material = newMaterial;
		}
		else if (newMaterial.equalsIgnoreCase("steel"))
		{
			density = 7850;
			strength = 550000000;
			shearStrength = 0.75*strength;
			elasticity = 200.00E+9;
			material = newMaterial;
		}
		else if (newMaterial.equalsIgnoreCase("concrete"))
		{
			density = 2400;
			strength = 40000000;
			shearStrength = 17000000;
			elasticity = 17.00E+9;
			material = newMaterial;
		}
		else if (newMaterial.equalsIgnoreCase("brick"))
		{
			density = 1890;
			strength = 14000000;
			shearStrength = 1*strength;
			elasticity = 15.00E+9;
			material = newMaterial;
		}
		else if (newMaterial.equalsIgnoreCase("mudbrick"))
		{
			density = 1890;
			strength = 1900000;
			shearStrength = 1*strength;
			elasticity = 15.00E+9;
			material = newMaterial;
		}
		else if (newMaterial.equalsIgnoreCase("aluminum") || material.equalsIgnoreCase("aluminium"))
		{
			density = 2700;
			strength = 310000000;
			shearStrength = 0.65*strength;
			elasticity = 69.00E+9;
			material = newMaterial;
		}
		else if (newMaterial.equalsIgnoreCase("titanium"))
		{
			density = 4420;
			strength = 1000000000;
			shearStrength = 550000000;
			elasticity = 110.00E+9;
			material = newMaterial;
		}
		else if (newMaterial.equalsIgnoreCase("magnesium"))
		{
			density = 1740;
			strength = 130000000;
			shearStrength = 1*strength;
			elasticity = 45.00E+9;
			material = newMaterial;
		}
	}

	public String getMaterial()
	{
		return material;
	}
	
	public double getDensity()
	{
		return density;
	}
	
	public double getStrength()
	{
		return strength;
	}

	public double getShearStrength()
	{
		return shearStrength;
	}

	public double getElasticity()
	{
		return elasticity;
	}
}
