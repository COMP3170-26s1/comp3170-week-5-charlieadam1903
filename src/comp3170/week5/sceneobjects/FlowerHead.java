package comp3170.week5.sceneobjects;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ShaderLibrary;
public class FlowerHead extends SceneObject {
	
	private static final String VERTEX_SHADER = "vertex.glsl";
	private static final String FRAGMENT_SHADER = "fragment.glsl";
	private Shader shader;

	private Vector3f petalColour = new Vector3f(1.0f,1.0f,1.0f);

	private Vector4f[] vertices;
	private int vertexBuffer;
	
	private int[] indices;
	private int indexBuffer;

	public FlowerHead(int nPetals, Vector3f colour) {
		
		// TODO: Create the flower head. (TASK 1)
		// Consider the best way to draw the mesh with the nPetals input. 
		// Note that this may involve moving some code OUT of this class!
		
		shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);	
		
		float angle = comp3170.Math.TAU / (nPetals * 2); // split up 360 into all the petals as equal angles, in radians
		float innerRadius = 0.3f; // in normalised device coords
		float outerRadius = 0.7f;
		
		vertices = new Vector4f[nPetals * 2 + 1]; //  center + (2 * amount of nPetals )
		vertices[0] = new Vector4f(0, 0, 0, 1);
		
		// we change placement of first petal to 0, bc otherwise it would skip the 1st
		// petals radius
		// angle of 1st petal is 0 degrees
		
		for (int i = 1; i < vertices.length; i++) {
					
			float c = Math.cos(angle * (i - 1));
			float s = Math.sin(angle * (i - 1));
			
			float radius = (i % 2 == 0) ? innerRadius : outerRadius; // this is a ternary statement

			vertices[i] = new Vector4f(c * radius, s * radius, 0f, 1f);
		}
//			System.out.println("angle = " + angle * (i - 1));
		
//	
			
//			for (int i = 0; i < vertices.length; i++) {
////				System.out.println("vertex [" + i + "] = " + vertices[i]);
//			}

			int numberOfTriangles = nPetals * 2;
			
			indices = new int[numberOfTriangles * 3]; // 6 indices per petal (2 triangles)
		
			for (int i = 0; i < numberOfTriangles; i++) {
				// Inner and outer vertex indices for the current petal

				//for this triangle, set the indices to be [0, i+1, i+2]
				indices[i * 3] = 0; // center of flower (vertex 0)
				indices[i * 3 + 1] = i + 1; // inner vertex of petal
				indices[i * 3 + 2] = i + 2; // outer vertex of petal

			}

			indices[indices.length - 1] = 1; //sets  last index to top vertex


			for (int i = 0; i < indices.length; i++) {
				System.out.print(indices[i] + ", ");
				if (i % 3 == 2)
					System.out.println();
			}

			vertexBuffer = GLBuffers.createBuffer(vertices);
			indexBuffer = GLBuffers.createIndexBuffer(indices);
		
		petalColour = colour;
	}

	public void update(float dt) {
		// TODO: Make the flower head rotate. (TASK 5)
	}

	public void drawSelf(Matrix4f mvpMatrix) {
		  //This vvv
		shader.setUniform("u_mvpMatrix", mvpMatrix);
  		shader.setUniform("u_colour", new Vector3f(1.0f, 1.0f, 0.0f)); // yellow petals
  		shader.setAttribute("a_position", vertexBuffer);
  		
  		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
  		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
	}
}
