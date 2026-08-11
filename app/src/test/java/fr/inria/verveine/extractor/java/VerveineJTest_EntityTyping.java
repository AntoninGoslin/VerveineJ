package fr.inria.verveine.extractor.java;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import org.moosetechnology.model.famix.famixjavaentities.EntityTyping;
import org.moosetechnology.model.famix.famixjavaentities.Method;
import org.moosetechnology.model.famix.famixjavaentities.NamedEntity;
import org.moosetechnology.model.famix.famixtraits.TTypedEntity;

public class VerveineJTest_EntityTyping extends VerveineJTestAbstract {

	protected VerveineJParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		new File(DEFAULT_OUTPUT_FILE).delete();
		parser = new VerveineJParser();
		repo = parser.getFamixRepo();
	}

	private void parse(String[] sources) {
		parser.configure(sources);
		parser.parse();
		parser.exportModel(DEFAULT_OUTPUT_FILE);
	}

	/*
	 * These tests are here to check a bug of duplication happening on the entity
	 * typing
	 */
	@Test
	public void testStubConstructorHasExactlyOneVoidTyping() {
		parse(new String[] { "src/test/resources/entity_typing/MarshalInputStream.java",
							 "src/test/resources/entity_typing/MarshalledObject.java"});

		int voidTypingCount = 0;
		Method constructor = entitiesNamed(Method.class, "MarshalInputStream").iterator().next();
		
		// declared type is void and typed entity is MarshalInputStream
		for (EntityTyping typing : entitiesOfType(EntityTyping.class)) {
			if ("void".equals(typing.getDeclaredType().getName())) {
				TTypedEntity typedEntity = typing.getTypedEntity();

				// bug on MarshalInputStream
				if (typedEntity == constructor) {
					voidTypingCount++;		
				}
			}
		}

		// we should have only 1 void for MarshalInputStream
		assertEquals(1, voidTypingCount);
	}
}
