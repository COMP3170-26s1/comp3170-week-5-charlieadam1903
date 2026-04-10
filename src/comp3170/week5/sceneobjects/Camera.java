package comp3170.week5.sceneobjects;

import static comp3170.Math.TAU;
import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import comp3170.SceneObject;
import comp3170.InputManager;

public class Camera extends SceneObject {

//	private final static float CAMERA_DISTANCE = 8f;
//	private final static float CAMERA_WIDTH = 30f;
//	private final static float CAMERA_HEIGHT = 30f;

	private float zoom = 200.0f; // You'll need this when setting up your projection matrix...
	private float angle;
	private float aspect;
//	private Vector2f position;
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f viewMatrix = new Matrix4f();
//	
	private Matrix4f modelMatrix;
	private Matrix4f translationMatrix;
	private Matrix4f rotationMatrix;
	private Matrix4f scaleMatrix
	;
	private float width;
	private float height;

	
	
	public Camera() {
//		position = new Vector2f(0,0);
		aspect = 1;
//		angle = 0;
//		modelMatrix = new Matrix4f();
//		translationMatrix = new Matrix4f();
//		rotationMatrix = new Matrix4f();
//		scaleMatrix = new Matrix4f();
	}
	
	public void resize(int w, int h) {
		this.width = w;
		this.height = h;
		this.aspect = width / height;
	}
	

	
	public Matrix4f GetViewMatrix(Matrix4f dest) {
//		translationMatrix.m20
		viewMatrix.identity();
		viewMatrix = getMatrix();

		return viewMatrix.invert(dest);
	}
	

	public Matrix4f GetProjectionMatrix(Matrix4f dest) {
		
		//      [ sx  0   0 ]
		// MS = [ 0   sy  0 ]
		//      [ 0   0   1 ]
		projectionMatrix.identity(); // SO FUCKING IMPORTANT TO SET IDENTITY	
//		scaleMatrix.m00(width / zoom);
//		scaleMatrix.m11(height / zoom);
//
//		return scaleMatrix.invert(dest);
		
		projectionMatrix.m00(width/zoom);
		projectionMatrix.m11(height/zoom);
	   
	    var result = projectionMatrix.invert(dest);
	
		return result;
	}
	
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
	
// TODO: Make the camera zoom in-and-out based on user input. (TASK 4)
// You'll need to move some code around!
	
	public void update(InputManager input, float deltaTime) {
		if (input.isKeyDown(GLFW_KEY_UP)) {
			// TODO: Zoom the camera in
		}
			
		if (input.isKeyDown(GLFW_KEY_DOWN)) {
			// TODO: Zoom the camera out
		}
	}
}