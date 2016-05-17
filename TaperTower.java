//--------------------------------------------------------------------------------------------------

package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

public class TaperTower
{
	// Path location reference strings
	private String filePath;
	private String imagePath;
	
	// Number of iterations to perform. Calculated internally based on height.
	private int iterations;
	
	private double height;			// Tower height in m
	private double density;			// Material density (specified in Material class)
	private double strength;		// Material strength
	private double safety;			// Safety factor (Material strength is divided by this)
	private double gravity;			// Acceleration due to gravity (9.81 m/s on Earth)
	private double viewScale;		// Length represented by a single pixel in m
	private double calcScale;		// Iterations are performed at multiples of this.
	private double length;			// Internal variable. Final value is tower base footprint.
	private double capMass;			// Mass that the tower peak must support.
	private double baseLength;		// Maximum footprint length or diameter.
	private double topLength;		// Minimum peak diameter or length.
	private double mass;			// Internal variable. Final value is total tower mass.
	private double airDensity;		// Density of air (1.25 kg/m3)
	private double airSpeed;		// Wind speed (default is severe hurricane at 40 m/s)
	private double shapeFactor;		// Shape factor for drag equation
	private double lean;			// Maximum building lean in degrees
	private double shearStrength;	// Material shear strength (mostly irrelevant)
	private double innerLength;		// Diameter or length of hollow internal space (helps with
									// bending stresses)
	
	Material material;				// Material object. Defines strength and density.

	private double[] lengthlist;	// Array of lengths at each iteration.
	private double[] masslist;		// Array of cumulative mass at each iteration
	private double[] arealist;		// Array of areas at each iteration
	private double[] stresslist;	// Array of stresses at each iteration
	private double[] stressfraction;// Array of ratios between actual stress and strength
	private double[] weightlist;	// Array of weight contributions of stress
	private double[] weightfraction;// Array of weight stress relative to strength
	private double[] leanlist;		// Array of lean bending stress contribution
	private double[] leanfraction;
	private double[] windlist;		// Array of wind bending stress contribution
	private double[] windfraction;
	private double[] shearlist;		// Array of bending shear stresses
	private double[] shearfraction;
	
	public TaperTower()
	{
		filePath = material + "TaperTower.txt";
		imagePath = material + "TaperTower.png";
		height = 1000;						// 1 km
		material = new Material();
		setMaterial("stone");
		safety = 2;							// Effective strength is half material strength
		gravity = 9.81;						// 9.81 m/s/s
		calcScale = 0.001;					// 1 mm
		viewScale = 1;						// 1 m
		capMass = 1000;						// 1000 kg
		baseLength = 200;					// 200 m
		topLength = 0;						// No minimum length
		airDensity = 1.25;					// 1.25 kg/m3
		airSpeed = 40;						// 40 m/2 (severe hurricane)
		shapeFactor = 0.5;
		lean = 2 * Math.PI / 180;			// 2 degrees (max wind induced lean of skyscraper)
		innerLength = 0;					// No inner space (solid tower)
		iterations = -1;					// Forces a tower to generate before certain methods.
	}
	
	// Changes the material and updates associated properties
	public void setMaterial(String newMaterial)
	{
		material.setMaterial(newMaterial);
		density = material.getDensity();
		strength = material.getStrength();
		shearStrength = material.getShearStrength();
	}
	
	// This and following methods update various properties within the tower object.
	public void filePath(String newPath)
	{
		filePath = newPath;
	}

	public void imagePath(String newPath)
	{
		imagePath = newPath;
	}
	
	public void height(double newHeight)
	{
		height = newHeight;
	}
	
	public void safety(double newSafety)
	{
		safety = newSafety;
	}
	
	public void gravity(double newGravity)
	{
		gravity = newGravity;
	}
	
	public void viewScale(double newView)
	{
		viewScale = newView;
	}
	
	public void calcScale(double newCalc)
	{
		calcScale = newCalc;
	}
	
	public void capMass(double newCap)
	{
		capMass = newCap;
	}
	
	public void baseLength(double newBase)
	{
		baseLength = newBase;
	}
	
	public void topLength(double newTop)
	{
		topLength = newTop;
	}

	public void airSpeed(double newSpeed)
	{
		airSpeed = newSpeed;
	}

	public void airDensity(double newAirDensity)
	{
		airDensity = newAirDensity;
	}

	public void lean(double newLean)
	{
		lean = newLean * Math.PI / 180;
	}

	public void innerLength(double newInnerLength)
	{
		innerLength = newInnerLength;
	}
	
	// Performs the iterations to construct the tower and populates the list arrays.
	public void generateArrays()
	{
		System.out.print("Generating tower..");
		
		// Find iterations by dividing the tower height by the calculation scale, and rounding.
		iterations = (int)Math.round(height / calcScale);
		
		// Initial mass.
		mass = capMass;
		
		// Effective strength
		double stress = strength / safety;
		
		double area = 0;
		double inertia = 0;
		double crossArea = 0;
		double totalLength = 0;
		length = calcScale;
		double innerRadius = innerLength / 2;
		double radius = innerRadius + calcScale/2;
		
		// Wind pressure equation. Multiplied by the cross sectional area of the tower above each
		// section and multiplied by the centroid to get the general wind torque.
		double windPressure = airDensity * airSpeed * airSpeed * shapeFactor / 2;
		
		lengthlist = new double[iterations];
		masslist = new double[iterations];
		arealist = new double[iterations];
		stresslist = new double[iterations];
		stressfraction = new double[iterations];
		weightlist = new double[iterations];
		weightfraction = new double[iterations];
		leanlist = new double[iterations];
		leanfraction = new double[iterations];
		windlist = new double[iterations];
		windfraction = new double[iterations];
		shearlist = new double[iterations];
		shearfraction = new double[iterations];

		// Tower construction iteration. Each iteration constructs a new layer based on the
		// properties of the layers above.
		for (int i = 0; i < iterations; i++)
		{
			// Each layer is examined at the top, but properties must be calculated by the bottom.
			// This is because the radius is constant, but stress increases do to the increased
			// mass and cross sectional area of the slice.
			masslist[i] = mass;
			
			// Determines a rough centroid and cross sectional area for torque and wind force
			// calculations.
			totalLength = totalLength + length;
			double averageLength = totalLength / i;
			double centroid = crossArea / averageLength / 2;
			
			// The quartic equation for the radius cannot be solved analytically. However an efficient
			// iterative approach is possible because the radius always increases or stays the same
			// at each layer. The radius is tested and if the resulting stress is higher than the
			// allowed stress, it is increased until it does not.
			double testStress = Double.MAX_VALUE;
			
			// Radius must be decreased first to prevent unneeded increases and to prevent the need
			// to have a separate set of identical equations. Scale is divided by 2 because it is
			// based on the diameter rather than the radius.
			radius = radius - calcScale/2;

			while (testStress > stress)
			{
				// A very complex quartic equation shown in a separate word document. Broken down as
				// much as possible for efficiency.
				radius = radius + calcScale/2;
				area = Math.PI * (radius*radius - innerRadius*innerRadius);
				inertia = Math.PI * (radius*radius*radius*radius - innerRadius*innerRadius*innerRadius*innerRadius) / 4; 
				double sliceMass = density * gravity * calcScale;
				double A = gravity*(mass+sliceMass*area);
				double B = area;
				double C = centroid*radius;
				double D = Math.sin(lean)*gravity*(mass+sliceMass*area)+windPressure*(crossArea+2*radius*calcScale);
				double E = inertia;
				testStress = A/B+C*D/E;
			}
			
			length = 2 * radius;
			
			if (length < topLength)
				{
				length = topLength;
				}
			
			radius = length / 2;
			
			area = Math.PI * (radius * radius - innerRadius * innerRadius);
			inertia = Math.PI * (Math.pow(radius, 4) - Math.pow(innerRadius, 4)) / 4;
			
			weightlist[i] = mass * gravity / area;
			weightfraction[i] = weightlist[i] / strength;	

			leanlist[i] = centroid * mass * gravity * Math.sin(lean) * radius / inertia;
			leanfraction[i] = leanlist[i] / strength;
			
			windlist[i] = centroid * windPressure * crossArea * radius / inertia;
			windfraction[i] = windlist[i] / strength;
			
			stresslist[i] = weightlist[i] + leanlist[i] + windlist[i];
			stressfraction[i] = stresslist[i] / strength;
			
			shearlist[i] = 4 * (gravity * mass * Math.sin(lean) + windPressure * crossArea) * (radius * radius - innerRadius * innerRadius) / 3 / inertia;
			shearfraction[i] = shearlist[i] / shearStrength;	
			
			crossArea = crossArea + length * 2 * calcScale;
			mass = mass + (area * calcScale * density);

			arealist[i] = area;
			lengthlist[i] = length;
			
			if (length > baseLength)
			{
				height = (double)i * height / (double)iterations;
				iterations = i;
				break;
			}
		}

		System.out.println(" complete.");
		System.out.println();
	}
	
	public void generateFile() throws FileNotFoundException
	{
		if (iterations == -1) generateArrays();
		
		System.out.print("Generating data file...");
		
		PrintWriter file = new PrintWriter(filePath);
		
		double scaleRatio = viewScale / calcScale;
		
		int viewLength = 2*(int)Math.floor((viewScale + length) / viewScale / 2)+1;
		if (viewLength > 201) viewLength = 201;
		
		for (int i = 0; i < Math.round(height / viewScale); i++)
		{
			int k = (int)Math.round(i * scaleRatio);
			
			int currentLength = 2*(int)Math.floor((viewScale + lengthlist[k]) / viewScale / 2)+1;
			{
				for (int j = 1; j <= viewLength; j++)
				{
					if (j <= (viewLength - currentLength) / 2) file.print(" ");
					else if (j <= viewLength - (viewLength - currentLength) / 2) file.print("X");
					else file.print(" ");
				}
			}
			file.println("      " + (i * viewScale) + " m    " + Math.round(lengthlist[k]*1000) + " mm    " +  (Math.round(masslist[k])) + " kg   " + (Math.round(arealist[k])) + " m2   " + Math.round(weightlist[k]) + " Pa   " + Math.round(leanlist[k]) + " Pa   " + Math.round(windlist[k]) + " Pa   " + Math.round(stresslist[k]) + " Pa   " + Math.round(100*weightfraction[k]) + " %    " + Math.round(100*leanfraction[k]) + " %    " + Math.round(100*windfraction[k]) + " %    " + Math.round(100*stressfraction[k]) + " %    " + Math.round(shearlist[k]) + " Pa   " + Math.round(100*shearfraction[k]) + " %    ");
		}

		file.close();

		System.out.println(" complete.");
		System.out.println();
	}
	
	public double getHeight()
	{
		return height;
	}
	
	public double getLength()
	{
		return length;
	}

	public double getMass()
	{
		return mass;
	}
	
	public void generateImage() throws IOException
	{	
		if (iterations == -1) generateArrays();
		
		System.out.print("Generating image file...");
		
		double scaleRatio = viewScale / calcScale;
		
		int imageLength = 2*(int)Math.floor((viewScale + length) / viewScale / 2)+1;
		int imageHeight = (int)Math.round(height / viewScale);
		int imageSize = imageHeight;
		if (imageLength > imageHeight) imageSize = imageLength;
		
		BufferedImage image = new BufferedImage(imageSize, imageHeight, BufferedImage.TYPE_INT_RGB);
		
		for (int i = 0; i < imageHeight; i++)
		{
			int k = (int)Math.round(i * scaleRatio);
			
			int currentLength = 2*(int)Math.floor((viewScale + lengthlist[k]) / viewScale / 2)+1;
			
			for (int j = 0; j < imageSize; j++)
			{
				int white = (255 << 24) | (135 << 16) | (206 << 8) | 250;
				int black = (255 << 24) | (64 << 16) | (64 << 8) | 64;
				if (j <= (imageSize - currentLength) / 2) image.setRGB(j, i, white);
				else if (j <= imageSize - (imageSize - currentLength) / 2) image.setRGB(j, i, black);
				else image.setRGB(j, i, white);
			}
		}
		
		File imageFile = new File(imagePath);
		ImageIO.write(image, "png", imageFile);
		
		System.out.println(" complete.");
		System.out.println();
	}
	
	public double[][] getSnapShot()
	{
		double[][] array = new double[6][6];
		array[0][0] = 0;
		array[0][1] = Math.round(1 * height / 5);
		array[0][2] = Math.round(2 * height / 5);
		array[0][3] = Math.round(3 * height / 5);
		array[0][4] = Math.round(4 * height / 5);
		array[0][5] = Math.round(height);

		array[1][0] = calcScale*(double)Math.round(lengthlist[Math.round(0 * iterations / 5)]/calcScale);
		array[1][1] = calcScale*(double)Math.round(lengthlist[Math.round(1 * iterations / 5)]/calcScale);
		array[1][2] = calcScale*(double)Math.round(lengthlist[Math.round(2 * iterations / 5)]/calcScale);
		array[1][3] = calcScale*(double)Math.round(lengthlist[Math.round(3 * iterations / 5)]/calcScale);
		array[1][4] = calcScale*(double)Math.round(lengthlist[Math.round(4 * iterations / 5)]/calcScale);
		array[1][5] = calcScale*(double)Math.round(lengthlist[Math.round(5 * iterations / 5)-1]/calcScale);

		array[2][0] = (double)Math.round(weightfraction[Math.round(0 * iterations / 5)]*10000)/100;
		array[2][1] = (double)Math.round(weightfraction[Math.round(1 * iterations / 5)]*10000)/100;
		array[2][2] = (double)Math.round(weightfraction[Math.round(2 * iterations / 5)]*10000)/100;
		array[2][3] = (double)Math.round(weightfraction[Math.round(3 * iterations / 5)]*10000)/100;
		array[2][4] = (double)Math.round(weightfraction[Math.round(4 * iterations / 5)]*10000)/100;
		array[2][5] = (double)Math.round(weightfraction[Math.round(5 * iterations / 5)-1]*10000)/100;

		array[3][0] = (double)Math.round(leanfraction[Math.round(0 * iterations / 5)]*10000)/100;
		array[3][1] = (double)Math.round(leanfraction[Math.round(1 * iterations / 5)]*10000)/100;
		array[3][2] = (double)Math.round(leanfraction[Math.round(2 * iterations / 5)]*10000)/100;
		array[3][3] = (double)Math.round(leanfraction[Math.round(3 * iterations / 5)]*10000)/100;
		array[3][4] = (double)Math.round(leanfraction[Math.round(4 * iterations / 5)]*10000)/100;
		array[3][5] = (double)Math.round(leanfraction[Math.round(5 * iterations / 5)-1]*10000)/100;

		array[4][0] = (double)Math.round(windfraction[Math.round(0 * iterations / 5)]*10000)/100;
		array[4][1] = (double)Math.round(windfraction[Math.round(1 * iterations / 5)]*10000)/100;
		array[4][2] = (double)Math.round(windfraction[Math.round(2 * iterations / 5)]*10000)/100;
		array[4][3] = (double)Math.round(windfraction[Math.round(3 * iterations / 5)]*10000)/100;
		array[4][4] = (double)Math.round(windfraction[Math.round(4 * iterations / 5)]*10000)/100;
		array[4][5] = (double)Math.round(windfraction[Math.round(5 * iterations / 5)-1]*10000)/100;

		array[5][0] = (double)Math.round(shearfraction[Math.round(0 * iterations / 5)]*10000)/100;
		array[5][1] = (double)Math.round(shearfraction[Math.round(1 * iterations / 5)]*10000)/100;
		array[5][2] = (double)Math.round(shearfraction[Math.round(2 * iterations / 5)]*10000)/100;
		array[5][3] = (double)Math.round(shearfraction[Math.round(3 * iterations / 5)]*10000)/100;
		array[5][4] = (double)Math.round(shearfraction[Math.round(4 * iterations / 5)]*10000)/100;
		array[5][5] = (double)Math.round(shearfraction[Math.round(5 * iterations / 5)-1]*10000)/100;
		
		return array;
	}
}
