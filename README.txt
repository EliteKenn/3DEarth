These are just notes I made while making this project for me to understand 3D Designing/3D modeling

/*The first step in creating a three-dimensional JavaFX application is adding a camera to the scene graph. 
 * You do that by creating a PerspectiveCamera object, fiddling with its settings, 
 * and then calling the scene’s setCamera method.
 * 
 */

/*Now there are 2 basic ways to go about this moving globe project. You can either have 
 * 1.Set camera + Moving Object (Sphere) 
 * OR
 * 2.Set Object(Sphere) + Moving Camera. 
 * 
 *
 */
 
 // Made these attributes constant because the dimensions will stay the same.
	public static final double HEIGHT = 800;
	public static final double WIDTH = 1400;
  
  //Created a custom SmartGroup inner class that inherits the Group class to contain all the nodes
 SmartGroup world = new SmartGroup();
 
 // Diffuse map is the common kind of texture map. It defines the color and
		// pattern of the object.
		// Mapping the diffuse color is like painting an image on the surface of the
		// object.
 
 /**
		 * Specular maps are the maps you use to define a surface's shininess and
		 * highlight color. The higher the value of a pixel(from black to white) the
		 * shinier the surface will appear. Therefore, surfaces such as dry stone or
		 * cotton fabric would tend to have a very dark specular map, while surfaces
		 * like polished chrome or plastic would /tend to have lighter specular maps
		 */
     
     //AnimationTimer provides the use of have any created animation being able to run on it's own
