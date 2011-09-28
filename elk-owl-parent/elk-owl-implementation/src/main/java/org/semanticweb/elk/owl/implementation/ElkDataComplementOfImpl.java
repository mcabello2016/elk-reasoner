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

import org.semanticweb.elk.owl.interfaces.ElkDataRange;
import org.semanticweb.elk.owl.interfaces.ElkDataComplementOf;
import org.semanticweb.elk.owl.visitors.ElkDataRangeVisitor;
import org.semanticweb.elk.owl.visitors.ElkObjectVisitor;
import org.semanticweb.elk.util.hashing.HashGenerator;

/**
 * ELK implementation of ElkDataComplementOf.
 * 
 * @author Markus Kroetzsch
 * 
 */
public class ElkDataComplementOfImpl extends ElkObjectImpl implements
		ElkDataComplementOf {

	protected final ElkDataRange dataRange;

	private static final int constructorHash_ = "ElkDataComplementOf"
			.hashCode();

	/* package-private */ElkDataComplementOfImpl(
			ElkDataRange dataRange) {

		this.dataRange = dataRange;
		this.structuralHashCode = HashGenerator.combineListHash(
				constructorHash_, dataRange.structuralHashCode());
	}

	public ElkDataRange getDataRange() {
		return dataRange;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("DataComplementOf(");
		result.append(dataRange.toString());
		result.append(")");
		return result.toString();
	}

	public boolean structuralEquals(Object object) {
		if (this == object) {
			return true;
		} else if (object instanceof ElkDataComplementOf) {
			return dataRange.equals(((ElkDataComplementOf) object)
					.getDataRange());
		} else {
			return false;
		}
	}

	public <O> O accept(ElkObjectVisitor<O> visitor) {
		return visitor.visit(this);
	}

	public <O> O accept(ElkDataRangeVisitor<O> visitor) {
		return visitor.visit(this);
	}

}
