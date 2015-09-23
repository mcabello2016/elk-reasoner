package org.semanticweb.elk.reasoner.saturation.inferences.visitors;

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

import org.semanticweb.elk.reasoner.saturation.inferences.ComposedBackwardLink;
import org.semanticweb.elk.reasoner.saturation.inferences.DecomposedExistentialBackwardLink;
import org.semanticweb.elk.reasoner.saturation.inferences.DecomposedReflexiveBackwardLink;
import org.semanticweb.elk.reasoner.saturation.inferences.ReversedForwardLink;
import org.semanticweb.elk.reasoner.saturation.inferences.SuperReversedForwardLink;

public interface BackwardLinkInferenceVisitor<I, O> {

	public O visit(ComposedBackwardLink inference, I input);

	public O visit(DecomposedExistentialBackwardLink inference, I input);

	public O visit(DecomposedReflexiveBackwardLink inference, I input);

	public O visit(ReversedForwardLink inference, I input);

	public O visit(SuperReversedForwardLink inference, I input);

}