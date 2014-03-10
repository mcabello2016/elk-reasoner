/*
 * #%L
 * elk-reasoner
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Oxford University Computing Laboratory
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

package org.semanticweb.elk.alc.indexing.hierarchy;

import org.semanticweb.elk.alc.indexing.visitors.IndexedObjectPropertyVisitor;
import org.semanticweb.elk.alc.indexing.visitors.IndexedObjectVisitor;
import org.semanticweb.elk.owl.interfaces.ElkObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents all occurrences of an ElkObjectProperty in an ontology.
 * 
 * @author Frantisek Simancik
 * @author Markus Kroetzsch
 */

public class IndexedObjectProperty extends IndexedObject {

	// logger for events
	private static final Logger LOGGER_ = LoggerFactory
			.getLogger(IndexedObjectProperty.class);

	/**
	 * This counts how often this object occurred in the ontology.
	 */
	int occurrenceNo = 0;

	protected final ElkObjectProperty elkObjectProperty;

	/**
	 * Creates an object representing the given ElkObjectProperty.
	 */
	protected IndexedObjectProperty(ElkObjectProperty elkObjectProperty) {
		this.elkObjectProperty = elkObjectProperty;
	}

	/**
	 * @return The represented object property expression.
	 */
	public ElkObjectProperty getElkObjectProperty() {
		return elkObjectProperty;
	}

	@Override
	public boolean occurs() {
		return occurrenceNo > 0;
	}

	protected void updateOccurrenceNumber(int increment) {
		occurrenceNo += increment;
	}

	/**
	 * @return the string representation for the occurrence numbers of this
	 *         {@link IndexedClassExpression}
	 */
	public String printOccurrenceNumbers() {
		return "[all=" + occurrenceNo + "]";
	}

	/**
	 * verifies that occurrence numbers are not negative
	 */
	public void checkOccurrenceNumbers() {
		if (LOGGER_.isTraceEnabled())
			LOGGER_.trace(this + " occurences: " + printOccurrenceNumbers());
		if (occurrenceNo < 0)
			throw new ElkUnexpectedIndexingException(this
					+ " has a negative occurrence: " + printOccurrenceNumbers());
	}

	public void updateAndCheckOccurrenceNumbers(int increment) {
		updateOccurrenceNumber(increment);
		checkOccurrenceNumbers();
	}

	/**
	 * 
	 * @return The string representation of the {@link ElkObjectProperty}
	 *         corresponding to this object.
	 */
	@Override
	public String toStringStructural() {
		return '<' + getElkObjectProperty().getIri().getFullIriAsString() + '>';
	}

	@Override
	public <O> O accept(IndexedObjectVisitor<O> visitor) {
		return accept((IndexedObjectPropertyVisitor<O>) visitor);
	}

	public <O> O accept(IndexedObjectPropertyVisitor<O> visitor) {
		return visitor.visit(this);
	}

}