package _3DPlanet;

/*
 * @Author: EliteKenn
 */
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class Earth extends Application {
	public static final double HEIGHT = 800;
	public static final double WIDTH = 1400;
	private double anchorX, anchorY;
	private double anchorAngleX = 0;
	private double anchorAngleY = 0;
	private final DoubleProperty angleX = new SimpleDoubleProperty(0);
	private final DoubleProperty angleY = new SimpleDoubleProperty(0);
	private final Sphere sphere = new Sphere(150);
	@Override
	public void start(Stage window) {
		Camera camera = new PerspectiveCamera(true);
		camera.setNearClip(1);
		camera.setFarClip(10000);
		camera.translateZProperty().set(-1000);
		
		SmartGroup world = new SmartGroup();
		world.getChildren().add(prepareEarth());

		Slider slider = preparedSlider(); 
		world.translateZProperty().bind(slider.valueProperty());
		
		
		Group root = new Group();
		root.getChildren().add(world);
		root.getChildren().add(preparedImageView());
		root.getChildren().add(slider);
		
		Scene scene = new Scene(root, WIDTH, HEIGHT, true);
		scene.setFill(Color.SILVER);
		scene.setCamera(camera);
		
//		initMouseControl(world, scene, window);
		
		
		window.setTitle("Earth");
		window.setScene(scene);
		window.show();
		
		preparedAnimation();
	}
	
	
	private void preparedAnimation() {
		AnimationTimer timer = new AnimationTimer() {
		
		public void handle(long now) {
			sphere.rotateProperty().set(sphere.getRotate() + .2);
			
		}
	};
		timer.start();
	}
	
	private ImageView preparedImageView() {
		Image image = new Image(Earth.class.getResourceAsStream("/resources/resources/galaxy/galaxy.jpg"));
		ImageView imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.getTransforms().add(new Translate(-image.getWidth()/2, -image.getHeight()/2,800));
		return imageView;
	}
	
	private Slider preparedSlider() {
		Slider slider = new Slider();
		slider.setMax(800);
		slider.setMin(-200);
		slider.setPrefWidth(300);
		slider.setLayoutX(-150);
		slider.setLayoutY(200);
		slider.setShowTickLabels(true); 
		slider.setTranslateZ(5);
		slider.setStyle("-fx-base: black");
		return slider;
	}

	private Node prepareEarth() {
		// TODO Auto-generated method stub
		PhongMaterial earthMaterial = new PhongMaterial();
		earthMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/resources/resources/earth/earth-d.jpg")));
		earthMaterial.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/resources/resources/earth/earth-l.jpg")));
		earthMaterial.setSpecularMap(new Image(getClass().getResourceAsStream("/resources/resources/earth/earth-s.jpg")));
		earthMaterial.setBumpMap(new Image(getClass().getResourceAsStream("/resources/resources/earth/earth-n.jpg")));
		sphere.setRotationAxis(Rotate.Y_AXIS);
		sphere.setMaterial(earthMaterial);
		return sphere;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	
	class SmartGroup extends Group {
		Rotate r;
		Transform t = new Rotate();

		void rotateByX(int ang) {
			r = new Rotate(ang, Rotate.X_AXIS);
			t = t.createConcatenation(r);
			this.getTransforms().clear();
			this.getTransforms().addAll(t);
		}

		void rotateByY(int ang) {
			r = new Rotate(ang, Rotate.Y_AXIS);
			t = t.createConcatenation(r);
			this.getTransforms().clear();
			this.getTransforms().addAll(t);

		}
	}
	
	private void initMouseControl(SmartGroup group, Scene scene, Stage stage) {
		Rotate xRotate;
		Rotate yRotate;
		group.getTransforms().addAll(xRotate = new Rotate(0, Rotate.X_AXIS), yRotate = new Rotate(0, Rotate.Y_AXIS));

		xRotate.angleProperty().bind(angleX);
		yRotate.angleProperty().bind(angleY);

		scene.setOnMousePressed(event -> {
			anchorX = event.getSceneX();
			anchorY = event.getSceneY();
			anchorAngleX = angleX.get();
			anchorAngleY = angleY.get();
		});

		scene.setOnMouseDragged(event -> {
			angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
			angleY.set(anchorAngleY + anchorX - event.getSceneX());
		});

		stage.addEventFilter(ScrollEvent.SCROLL, e -> {
			double movement = e.getDeltaY();
			group.translateZProperty().set(group.getTranslateZ() + movement);
		});
	}
	

	}
	

