/**
 * 
 */
package org.semanticweb.elk.reasoner.stages;

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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.semanticweb.elk.reasoner.incremental.IncrementalStages;
import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;

/**
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class IncrementalContextInitializationStage extends
		AbstractReasonerStage {

	// logger for this class
	private static final Logger LOGGER_ = Logger
			.getLogger(IncrementalContextInitializationStage.class);
	/**
	 * The counter for deleted contexts
	 */
	private int initContexts_;
	/**
	 * The number of contexts
	 */
	private int maxContexts_;

	private final ReasonerStage dependency_;
	/**
	 * The state of the iterator of the input to be processed
	 */
	private Iterator<IndexedClassExpression> todo = null;

	public IncrementalContextInitializationStage(
			AbstractReasonerState reasoner, ReasonerStage dependency) {
		super(reasoner);

		this.dependency_ = dependency;
	}

	@Override
	public String getName() {
		return IncrementalStages.CONTEXT_INIT.toString();
	}

	@Override
	public boolean done() {
		return reasoner.incrementalState
				.getStageStatus(IncrementalStages.CONTEXT_INIT);
	}

	@Override
	public List<ReasonerStage> getDependencies() {
		return Collections.singletonList(dependency_);
	}

	@Override
	public void execute() throws ElkInterruptedException {

		if (todo == null)
			initComputation();
		try {
			progressMonitor.start(getName());
			for (;;) {
				if (!todo.hasNext())
					break;
				IndexedClassExpression ice = todo.next();

				if (ice.getContext() != null) {
					reasoner.saturationState.getWriter().initContext(
							ice.getContext());
				}

				initContexts_++;
				progressMonitor.report(initContexts_, maxContexts_);

				if (interrupted()) {
					continue;
				}
			}
		} finally {
			progressMonitor.finish();
		}
		reasoner.doneContextReset = true;
	}

	@Override
	void initComputation() {
		super.initComputation();
		todo = reasoner.saturationState.getNotSaturatedContexts().iterator();
		maxContexts_ = reasoner.saturationState.getNotSaturatedContexts()
				.size();
		initContexts_ = 0;
	}

	@Override
	public void printInfo() {
		if (initContexts_ > 0 && LOGGER_.isDebugEnabled())
			LOGGER_.debug("Contexts init:" + initContexts_);
	}
}