package org.semanticweb.elk.reasoner.saturation.properties;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2013 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedPropertyChain;
import org.semanticweb.elk.util.concurrent.computation.InputProcessor;
import org.semanticweb.elk.util.concurrent.computation.InputProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A factory of engines for computing sub-properties and compositions induced by
 * property inclusions, property chains, and reflexive properties
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
public class PropertyHierarchyCompositionComputationFactory
		implements
		InputProcessorFactory<IndexedPropertyChain, PropertyHierarchyCompositionComputationFactory.Engine> {

	// logger for this class
	static final Logger LOGGER_ = LoggerFactory
			.getLogger(PropertyHierarchyCompositionComputationFactory.class);

	// statistical information
	private final PropertyCompositionStatistics statistics_ = new PropertyCompositionStatistics();

	@Override
	public Engine getEngine() {
		return new Engine();
	}

	@Override
	public void finish() {
		statistics_.print();
	}

	class Engine implements InputProcessor<IndexedPropertyChain> {

		private final CompositionCreatingVisitor visitor_ = new CompositionCreatingVisitor();

		@Override
		public void submit(IndexedPropertyChain job) {
			job.accept(visitor_);
		}

		@Override
		public void process() throws InterruptedException {
			// everything should be process during submission
		}

		@Override
		public void finish() {
			statistics_.merge(visitor_.getStatistics());
		}

	}
}
