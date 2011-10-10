/*
 * #%L
 * ELK Reasoner
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
package org.semanticweb.elk.owl.implementation;

import java.util.List;

import org.semanticweb.elk.owl.interfaces.ElkLiteral;
import org.semanticweb.elk.owl.interfaces.ElkDataOneOf;
import org.semanticweb.elk.owl.visitors.ElkDataRangeVisitor;
import org.semanticweb.elk.owl.visitors.ElkObjectVisitor;

/**
 * ELK implementation of ElkDataOneOf.
 * 
 * @author Markus Kroetzsch
 */
public class ElkDataOneOfImpl extends ElkObjectListObject<ElkLiteral>
		implements ElkDataOneOf {

	private static final int constructorHash_ = "ElkDataOneOf".hashCode();

	/* package-private */ElkDataOneOfImpl(
			List<? extends ElkLiteral> literals) {
		super(literals);
		this.structuralHashCode = ElkObjectImpl.computeCompositeHash(
				constructorHash_, literals);
	}

	public List<? extends ElkLiteral> getLiterals() {
		return elkObjects;
	}

	@Override
	public String toString() {
		return buildFssString("DataOneOf");
	}

	public boolean structuralEquals(Object object) {
		if (this == object) {
			return true;
		} else if (object instanceof ElkDataOneOf) {
			return elkObjects
					.equals(((ElkDataOneOf) object).getLiterals());
		} else {
			return false;
		}
	}

	public <O> O accept(ElkDataRangeVisitor<O> visitor) {
		return visitor.visit(this);
	}

	public <O> O accept(ElkObjectVisitor<O> visitor) {
		return visitor.visit(this);
	}

}