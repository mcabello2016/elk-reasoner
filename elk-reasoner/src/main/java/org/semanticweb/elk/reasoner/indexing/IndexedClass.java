/*
 * #%L
 * elk-reasoner
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Department of Computer Science, University of Oxford
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.semanticweb.elk.reasoner.indexing;

import java.util.Collections;

import org.semanticweb.elk.syntax.interfaces.ElkClass;
import org.semanticweb.elk.syntax.interfaces.ElkClassExpression;

/**
 * Represents all occurrences of an ElkClass in an ontology.
 * 
 * @author Frantisek Simancik
 * 
 */
public class IndexedClass extends IndexedClassExpression implements
		IndexedEntity {

	/**
	 * Creates an object representing the given ElkClass.
	 */
	protected IndexedClass(ElkClass elkClass) {
		super(Collections.singletonList((ElkClassExpression) elkClass));
	}

	/**
	 * @return The represented ElkClass.
	 */
	public ElkClass getElkClass() {
		return (ElkClass) representatives.get(0);
	}

	/**
	 * Represent the object's ElkClass as a string. This implementation reflects
	 * the fact that we generally consider only one IndexedClass for each
	 * ElkClass.
	 * 
	 * @return String representation.
	 */
	public String toString() {
		return "[" + getElkClass().toString() + "]";
	}

	@Override
	public <O> O accept(IndexedClassExpressionVisitor<O> visitor) {
		return visitor.visit(this);
	}

	public <O> O accept(IndexedEntityVisitor<O> visitor) {
		return visitor.visit(this);
	}
}