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
package org.semanticweb.elk.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import org.semanticweb.elk.util.collections.intervals.Interval;
import org.semanticweb.elk.util.collections.intervals.IntervalTree;

/**
 *
 * @author Pospishnyi Oleksandr
 */
public class IntervalTreeTest extends TestCase {

	public IntervalTreeTest(String name) {
		super(name);
	}

	public void testWideTree() {
		final int treeSize = 1000;

		Random rnd = new Random(System.currentTimeMillis());

		IntervalTree<TestInterval, TestInterval> testTree = new IntervalTree<TestInterval, TestInterval>();
		ArrayList<TestInterval> intervals = new ArrayList<TestInterval>(treeSize);

		for (int i = 0; i < treeSize; i++) {
			int a = rnd.nextInt();
			int b = rnd.nextInt();
			TestInterval testInterval = new TestInterval(a > b ? b : a, true, a > b ? a : b, true);
			testTree.add(testInterval, testInterval);
			intervals.add(testInterval);
			assertTrue(testInterval.getLow().compareTo(testInterval.getHigh()) <= 0);
		}

		//test that every stored interval can be retrieved

		for (TestInterval testInterval : intervals) {
			assertTrue(testTree.searchIncludes(testInterval).contains(testInterval));
		}

		//test that every interval was retrieved for every stored interval

		for (TestInterval testInterval : intervals) {
			Collection<TestInterval> treeAnswer = testTree.searchIncludes(testInterval);
			Collection<TestInterval> listAnswer = getAllIncludes(intervals, testInterval);
			assertEquals(treeAnswer.size(), listAnswer.size());
			assertTrue(listAnswer.containsAll(treeAnswer));
		}
		

		//test that every interval was retrieved for any random query interval

		for (int i = 0; i < treeSize; i++) {
			int a = rnd.nextInt();
			int b = rnd.nextInt();
			TestInterval testInterval = new TestInterval(a > b ? b : a, true, a > b ? a : b, true);
			Collection<TestInterval> treeAnswer = testTree.searchIncludes(testInterval);
			Collection<TestInterval> listAnswer = getAllIncludes(intervals, testInterval);
			assertEquals(treeAnswer.size(), listAnswer.size());
			assertTrue(listAnswer.containsAll(treeAnswer));
		}
	}

	public void testNarrowTree() {
		final int treeSize = 1000;

		Random rnd = new Random(System.currentTimeMillis());

		IntervalTree<TestInterval, TestInterval> testTree = new IntervalTree<TestInterval, TestInterval>();
		ArrayList<TestInterval> intervals = new ArrayList<TestInterval>(treeSize);

		for (int i = 0; i < treeSize; i++) {
			int a = rnd.nextInt(20);
			int b = rnd.nextInt(20);
			TestInterval testInterval = new TestInterval(a > b ? b : a, true, a > b ? a : b, true);
			testTree.add(testInterval, testInterval);
			intervals.add(testInterval);
			assertTrue(testInterval.getLow().compareTo(testInterval.getHigh()) <= 0);
		}

		//test that every stored interval can be retrieved

		for (TestInterval testInterval : intervals) {
			assertTrue(testTree.searchIncludes(testInterval).contains(testInterval));
		}

		//test that every interval was retrieved for every stored interval

		for (TestInterval testInterval : intervals) {
			Collection<TestInterval> treeAnswer = testTree.searchIncludes(testInterval);
			Collection<TestInterval> listAnswer = getAllIncludes(intervals, testInterval);
			assertEquals(treeAnswer.size(), listAnswer.size());
			assertTrue(listAnswer.containsAll(treeAnswer));
		}


		//test that every interval was retrieved for any random query interval

		for (int i = 0; i < treeSize; i++) {
			int a = rnd.nextInt(30) - 10;
			int b = rnd.nextInt(30) - 10;
			TestInterval testInterval = new TestInterval(a > b ? b : a, true, a > b ? a : b, true);
			Collection<TestInterval> treeAnswer = testTree.searchIncludes(testInterval);
			Collection<TestInterval> listAnswer = getAllIncludes(intervals, testInterval);
			assertEquals(treeAnswer.size(), listAnswer.size());
			assertTrue(listAnswer.containsAll(treeAnswer));
		}
	}

	private Collection<TestInterval> getAllIncludes(Collection<TestInterval> intervals, TestInterval i) {
		ArrayList<TestInterval> ret = new ArrayList<TestInterval>(50);
		for (TestInterval testInterval : intervals) {
			if (testInterval.contains(i)) {
				ret.add(testInterval);
			}
		}
		return ret;
	}

	class TestInterval implements Interval<Endpoint> {

		private Endpoint low;
		private Endpoint high;

		public TestInterval(int low, int high) {
			this.low = new Endpoint(low, true, true);
			this.high = new Endpoint(high, true, false);
		}

		public TestInterval(int low, boolean lowInclusive, int high, boolean highInclusive) {
			this.low = new Endpoint(low, lowInclusive, true);
			this.high = new Endpoint(high, highInclusive, false);
		}

		@Override
		public Endpoint getLow() {
			return low;
		}

		@Override
		public Endpoint getHigh() {
			return high;
		}

		@Override
		public boolean contains(Interval<Endpoint> interval) {
			return low.compareTo(interval.getLow()) <= 0 && high.compareTo(interval.getHigh()) >= 0;
		}

		@Override
		public int compareTo(Interval<Endpoint> o) {
			int cmp = low.compareTo(o.getLow());
			if (cmp == 0) {
				return high.compareTo(o.getHigh());
			} else {
				return cmp;
			}
		}

		@Override
		public int hashCode() {
			int hash = 7;
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final TestInterval other = (TestInterval) obj;
			if (this.low != other.low && (this.low == null || !this.low.equals(other.low))) {
				return false;
			}
			if (this.high != other.high && (this.high == null || !this.high.equals(other.high))) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return "" + low + ',' + high;
		}
	}

	class Endpoint implements Comparable<Endpoint> {

		private int value;
		private boolean inclusive;
		private boolean low;

		public Endpoint(int value, boolean inclusive, boolean low) {
			this.value = value;
			this.inclusive = inclusive;
			this.low = low;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final Endpoint other = (Endpoint) obj;
			if (this.value != other.value) {
				return false;
			}
			if (this.inclusive != other.inclusive) {
				return false;
			}
			if (this.low != other.low) {
				return false;
			}
			return true;
		}

		@Override
		public int compareTo(Endpoint o) {
			int cmp = Integer.compare(value, o.value);
			if (cmp == 0 && inclusive != o.inclusive) {
				return (low ^ inclusive) ? 1 : -1;
			} else {
				return cmp;
			}
		}

		@Override
		public String toString() {
			if (low) {
				return "" + (inclusive ? "[" : "(") + value;
			} else {
				return "" + value + (inclusive ? "]" : ")");
			}
		}
	}
}