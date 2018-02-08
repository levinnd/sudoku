package solvers;

import static org.junit.Assert.*;

import org.junit.Test;

import solvers.Generator;

public class testGenerator {

	@Test
	public void testGenerate(){
		
		Generator generator = new Generator();
		
		assertTrue(generator.getSolution().validate());
		System.out.println(generator.getSolution().toString());
	}
}
