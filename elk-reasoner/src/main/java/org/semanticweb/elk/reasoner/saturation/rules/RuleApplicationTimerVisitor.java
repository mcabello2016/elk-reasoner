package org.semanticweb.elk.reasoner.saturation.rules;
/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2012 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClass;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedDisjointnessAxiom;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectIntersectionOf;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectSomeValuesFrom;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedSubClassOfAxiom;
import org.semanticweb.elk.reasoner.saturation.SaturationState;
import org.semanticweb.elk.reasoner.saturation.conclusions.BackwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.Contradiction;
import org.semanticweb.elk.reasoner.saturation.conclusions.ForwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.Propagation;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.util.logging.CachedTimeThread;

/**
 * A {@link RuleApplicationVisitor} wrapper for a given
 * {@link RuleApplicationVisitor} that additionally records the time spend
 * within methods in the given {@link RuleApplicationTimer}.
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public class RuleApplicationTimerVisitor implements RuleApplicationVisitor {

	/**
	 * the visitor whose methods to be timed
	 */
	private final RuleApplicationVisitor visitor_;

	/**
	 * timer used to time the visitor
	 */
	private final RuleApplicationTimer timer_;

	/**
	 * Creates a new {@link DecompositionRuleApplicationVisitor} that executes
	 * the corresponding methods of the given
	 * {@link DecompositionRuleApplicationVisitor} and measures the time spent
	 * within the corresponding methods using the given
	 * {@link RuleApplicationTimer}.
	 * 
	 * @param visitor
	 *            the {@link DecompositionRuleApplicationVisitor} used to
	 *            execute the methods
	 * @param timer
	 *            the {@link RuleApplicationTimer} used to mesure the time spent
	 *            within the methods
	 */
	public RuleApplicationTimerVisitor(RuleApplicationVisitor visitor,
			RuleApplicationTimer timer) {
		this.timer_ = timer;
		this.visitor_ = visitor;
	}

	@Override
	public void visit(
			IndexedClass.OwlThingContextInitializationRule owlThingContextInitializationRule,
			SaturationState.Writer writer, Context context) {
		timer_.timeOwlThingContextInitializationRule -= CachedTimeThread.currentTimeMillis;
		visitor_.visit(owlThingContextInitializationRule, writer, context);
		timer_.timeOwlThingContextInitializationRule += CachedTimeThread.currentTimeMillis;
	}

	@Override
	public void visit(
			IndexedDisjointnessAxiom.ThisCompositionRule thisCompositionRule,
			SaturationState.Writer writer, Context context) {
		timer_.timeDisjointnessAxiomCompositionRule -= CachedTimeThread.currentTimeMillis;
		visitor_.visit(thisCompositionRule, writer, context);
		timer_.timeDisjointnessAxiomCompositionRule += CachedTimeThread.currentTimeMillis;
	}

	@Override
	public void visit(
			IndexedDisjointnessAxiom.ThisContradictionRule thisContradictionRule,
			SaturationState.Writer writer, Context context) {
		timer_.timeDisjointnessAxiomContradictionRule -= CachedTimeThread.currentTimeMillis;
		visitor_.visit(thisContradictionRule, writer, context);
		timer_.timeDisjointnessAxiomContradictionRule += CachedTimeThread.currentTimeMillis;

	}

	@Override
	public void visit(
			IndexedObjectIntersectionOf.ThisCompositionRule thisCompositionRule,
			SaturationState.Writer writer, Context context) {
		timer_.timeObjectIntersectionOfCompositionRule -= CachedTimeThread.currentTimeMillis;
		visitor_.visit(thisCompositionRule, writer, context);
		timer_.timeObjectIntersectionOfCompositionRule += CachedTimeThread.currentTimeMillis;
	}

	@Override
	public void visit(
			IndexedSubClassOfAxiom.ThisCompositionRule thisCompositionRule,
			SaturationState.Writer writer, Context context) {
		timer_.timeSubClassOfAxiomCompositionRule -= CachedTimeThread.currentTimeMillis;
		visitor_.visit(thisCompositionRule, writer, context);
		timer_.timeSubClassOfAxiomCompositionRule += CachedTimeThread.currentTimeMillis;
	}

	@Override
	public void visit(
			IndexedObjectSomeValuesFrom.ThisCompositionRule thisCompositionRule,
			SaturationState.Writer writer, Context context) {
		timer_.timeObjectSomeValuesFromCompositionRule -= CachedTimeThread.currentTimeMillis;
		visitor_.visit(thisCompositionRule, writer, context);
		timer_.timeObjectSomeValuesFromCompositionRule += CachedTimeThread.currentTimeMillis;
	}

	@Override
	public void visit(ForwardLink.ThisBackwardLinkRule thisBackwardLinkRule,
			SaturationState.Writer writer, BackwardLink backwardLink) {
		timer_.timeForwardLinkBackwardLinkRule -= CachedTimeThread.currentTimeMillis;
		visitor_.visit(thisBackwardLinkRule, writer, backwardLink);
		timer_.timeForwardLinkBackwardLinkRule += CachedTimeThread.currentTimeMillis;

	}

	@Override
	public void visit(Propagation.ThisBackwardLinkRule thisBackwardLinkRule,
			SaturationState.Writer writer, BackwardLink backwardLink) {
		timer_.timePropagationBackwardLinkRule -= CachedTimeThread.currentTimeMillis;
		visitor_.visit(thisBackwardLinkRule, writer, backwardLink);
		timer_.timePropagationBackwardLinkRule += CachedTimeThread.currentTimeMillis;
	}

	@Override
	public void visit(
			Contradiction.BottomBackwardLinkRule bottomBackwardLinkRule,
			SaturationState.Writer writer, BackwardLink backwardLink) {
		timer_.timeContradictionBottomBackwardLinkRule -= CachedTimeThread.currentTimeMillis;
		visitor_.visit(bottomBackwardLinkRule, writer, backwardLink);
		timer_.timeContradictionBottomBackwardLinkRule += CachedTimeThread.currentTimeMillis;
	}

}