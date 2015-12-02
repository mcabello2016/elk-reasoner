package org.semanticweb.elk.reasoner.indexing.classes;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2015 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.owl.interfaces.ElkAxiom;
import org.semanticweb.elk.reasoner.indexing.model.IndexedAxiom;
import org.semanticweb.elk.reasoner.indexing.model.ModifiableIndexedClass;
import org.semanticweb.elk.reasoner.indexing.model.ModifiableIndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.model.ModifiableIndexedDefinitionAxiom;
import org.semanticweb.elk.reasoner.indexing.model.ModifiableOntologyIndex;
import org.semanticweb.elk.reasoner.saturation.rules.subsumers.IndexedClassDecompositionRule;
import org.semanticweb.elk.reasoner.saturation.rules.subsumers.IndexedClassFromDefinitionRule;
import org.semanticweb.elk.reasoner.saturation.rules.subsumers.SuperClassFromSubClassRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModifiableIndexedDefinitionAxiomImpl<A extends ElkAxiom>
		extends
			ModifiableIndexedAxiomImpl<A>
		implements
			ModifiableIndexedDefinitionAxiom {

	static final Logger LOGGER_ = LoggerFactory
			.getLogger(ModifiableIndexedDefinitionAxiomImpl.class);

	private final ModifiableIndexedClass definedClass_;
	private final ModifiableIndexedClassExpression definition_;

	protected ModifiableIndexedDefinitionAxiomImpl(A originalAxiom,
			ModifiableIndexedClass definedClass,
			ModifiableIndexedClassExpression definition) {
		super(originalAxiom);
		this.definedClass_ = definedClass;
		this.definition_ = definition;
	}

	@Override
	public ModifiableIndexedClass getDefinedClass() {
		return this.definedClass_;
	}

	@Override
	public ModifiableIndexedClassExpression getDefinition() {
		return this.definition_;
	}

	@Override
	public String toStringStructural() {
		return "EquivalentClasses(" + this.definedClass_ + ' '
				+ this.definition_ + ')';
	}

	@Override
	public boolean addOccurrence(ModifiableOntologyIndex index) {
		ElkAxiom reason = getOriginalAxiom();
		if (IndexedClassDecompositionRule.tryAddRuleFor(this, index, reason))
			return IndexedClassFromDefinitionRule.addRuleFor(this, index,
					reason);
		// else
		return SuperClassFromSubClassRule.addRulesFor(this, index, reason);
	}

	@Override
	public boolean removeOccurrence(ModifiableOntologyIndex index) {
		ElkAxiom reason = getOriginalAxiom();
		if (IndexedClassDecompositionRule.tryRemoveRuleFor(this, index, reason))
			return IndexedClassFromDefinitionRule.removeRuleFor(this, index,
					reason);
		// else
		return SuperClassFromSubClassRule.removeRulesFor(this, index, reason);
	}

	@Override
	public <O> O accept(IndexedAxiom.Visitor<O> visitor) {
		return visitor.visit(this);
	}

}