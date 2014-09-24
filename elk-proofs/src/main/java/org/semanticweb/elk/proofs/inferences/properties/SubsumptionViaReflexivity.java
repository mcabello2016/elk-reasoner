/**
 * 
 */
package org.semanticweb.elk.proofs.inferences.properties;
/*
 * #%L
 * ELK Proofs Package
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2014 Department of Computer Science, University of Oxford
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

import java.util.Collection;
import java.util.Collections;

import org.semanticweb.elk.proofs.expressions.Expression;
import org.semanticweb.elk.proofs.inferences.Inference;
import org.semanticweb.elk.proofs.inferences.InferenceVisitor;
import org.semanticweb.elk.proofs.inferences.Printer;
import org.semanticweb.elk.proofs.sideconditions.SideCondition;

/**
 * @author Pavel Klinov
 *
 * pavel.klinov@uni-ulm.de
 */
public class SubsumptionViaReflexivity implements Inference  {
	
	private final Expression premise_;
	
	private final Expression conclusion_;
	
	public SubsumptionViaReflexivity(Expression premise, Expression conclusion) {
		premise_ = premise;
		conclusion_ = conclusion;
	}
	
	@Override
	public Collection<? extends Expression> getPremises() {
		return Collections.singletonList(premise_);
	}

	@Override
	public SideCondition getSideCondition() {
		return null;
	}

	@Override
	public <I, O> O accept(InferenceVisitor<I, O> visitor, I input) {
		return visitor.visit(this, input);
	}

	@Override
	public Expression getConclusion() {
		return conclusion_;
	}

	@Override
	public String toString() {
		return Printer.print(this);
	}
}
