package comp3170.week5.sceneobjects;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL41.*;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ShaderLibrary;

public class Flower extends SceneObject {
	
	private static final String VERTEX_SHADER = "vertex.glsl";
	private static final String FRAGMENT_SHADER = "fragment.glsl";
	private static final float TAU = comp3170.Math.TAU;
	private Shader shader;
	
	private final float HEIGHT = 1.0f;
	private final float WIDTH = 0.1f;
	private Vector3f colour = new Vector3f(0f, 0.5f, 0f); // Dark Green
	private Vector3f petalColour = new Vector3f(1.0f, 1.0f, 0.0f); 		

	private Vector4f[] stemVertices;

	
//	private int petalVertexBuffer;
	private int stemVertBuffer;
	
	private int[] stemIndices;
//	private int[] petalIndices;
	private int stemIndBuffer;
//	private int petalIndexBuffer;
	
	private Vector3f stemColour;
	
	private FlowerHead head;
	private SceneObject flowerPivot = new SceneObject();
	private SceneObject root = new SceneObject();
	private Matrix4f modelMatrix = new Matrix4f();

	public Flower(int nPetals) {
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);		
	
		// make the stem of the flower

		// vertices for a wxh square with origin at the end
		// 
		//  (-w/2, h)     (w/2, h)		
		//       2-----------3
		//       | \         |
		//       |   \       |
		//       |     \     |
		//       |       \   |
		//       |         \ |
		//       0-----*-----1
		//  (-w/2, 0)     (w/2, 0)	
		
		//@formatter:off
		stemVertices = new Vector4f[] {
			new Vector4f(-WIDTH / 2,           0, 0, 1),
			new Vector4f( WIDTH / 2,           0, 0, 1),
			new Vector4f(-WIDTH / 2, HEIGHT, 0, 1),
			new Vector4f( WIDTH / 2, HEIGHT, 0, 1),
		};
		//@formatter:on
		stemVertBuffer = GLBuffers.createBuffer(stemVertices);
		
	    stemIndices = new int[] {
		    	0, 1, 2,
		    	3, 2, 1,
		};
		    
		stemIndBuffer = GLBuffers.createIndexBuffer(stemIndices);
		stemColour = new Vector3f(0f, 0.5f, 0f); // dark green
		
		root = this;
		
		flowerPivot.setParent(this);
		flowerPivot.getMatrix().translate(0, 0, 0);
		head = new FlowerHead(nPetals, petalColour);
		head.setParent(flowerPivot);
		head.getMatrix().translate(0.0f, 1.0f, 0.0f).scale(0.75f, 0.75f, 0.0f);
		
	
		
	
	}
	
	public void drawSelf(Matrix4f mvpMatrix) {
		shader.enable();
		shader.setUniform("u_mvpMatrix", mvpMatrix);
	    shader.setAttribute("a_position", stemVertBuffer);
	    shader.setUniform("u_colour", colour);	    
	    
	    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, stemIndBuffer);
	    glDrawElements(GL_TRIANGLES, stemIndices.length, GL_UNSIGNED_INT, 0);	
	    
	  //This vvv
//	  		shader.setUniform("u_colour", new Vector3f(1.0f, 1.0f, 0.0f)); // yellow petals
//	  		shader.setAttribute("a_position", petalVertexBuffer);
	  		
//	  		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, petalIndexBuffer);
//	  		glDrawElements(GL_TRIANGLES, petalIndices.length, GL_UNSIGNED_INT, 0);
	}
	
	public void update(float dt) {
		// TODO: make the flower sway. (TASK 5)
	}
}
