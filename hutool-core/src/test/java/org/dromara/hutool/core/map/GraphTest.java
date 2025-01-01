/*
 * Copyright (c) 2013-2025 Hutool Team and hutool.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.hutool.core.map;

import org.dromara.hutool.core.map.multi.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * test for {@link Graph}
 */
public class GraphTest {

	@Test
	public void testPutEdge() {
		final Graph<Integer> graph = new Graph<>();
		graph.putEdge(0, 1);
		graph.putEdge(1, 2);
		graph.putEdge(2, 0);

		Assertions.assertEquals(asSet(1, 2), graph.getValues(0));
		Assertions.assertEquals(asSet(0, 2), graph.getValues(1));
		Assertions.assertEquals(asSet(0, 1), graph.getValues(2));
	}

	@Test
	public void testContainsEdge() {
		// 0 -- 1
		// |    |
		// 3 -- 2
		final Graph<Integer> graph = new Graph<>();
		graph.putEdge(0, 1);
		graph.putEdge(1, 2);
		graph.putEdge(2, 3);
		graph.putEdge(3, 0);

		Assertions.assertTrue(graph.containsEdge(0, 1));
		Assertions.assertTrue(graph.containsEdge(1, 0));

		Assertions.assertTrue(graph.containsEdge(1, 2));
		Assertions.assertTrue(graph.containsEdge(2, 1));

		Assertions.assertTrue(graph.containsEdge(2, 3));
		Assertions.assertTrue(graph.containsEdge(3, 2));

		Assertions.assertTrue(graph.containsEdge(3, 0));
		Assertions.assertTrue(graph.containsEdge(0, 3));

		Assertions.assertFalse(graph.containsEdge(1, 3));
	}

	@Test
	public void removeEdge() {
		final Graph<Integer> graph = new Graph<>();
		graph.putEdge(0, 1);
		Assertions.assertTrue(graph.containsEdge(0, 1));

		graph.removeEdge(0, 1);
		Assertions.assertFalse(graph.containsEdge(0, 1));
	}

	@Test
	public void testContainsAssociation() {
		// 0 -- 1
		// |    |
		// 3 -- 2
		final Graph<Integer> graph = new Graph<>();
		graph.putEdge(0, 1);
		graph.putEdge(1, 2);
		graph.putEdge(2, 3);
		graph.putEdge(3, 0);

		Assertions.assertTrue(graph.containsAssociation(0, 2));
		Assertions.assertTrue(graph.containsAssociation(2, 0));

		Assertions.assertTrue(graph.containsAssociation(1, 3));
		Assertions.assertTrue(graph.containsAssociation(3, 1));

		Assertions.assertFalse(graph.containsAssociation(-1, 1));
		Assertions.assertFalse(graph.containsAssociation(1, -1));
	}

	@Test
	public void testGetAssociationPoints() {
		// 0 -- 1
		// |    |
		// 3 -- 2
		final Graph<Integer> graph = new Graph<>();
		graph.putEdge(0, 1);
		graph.putEdge(1, 2);
		graph.putEdge(2, 3);
		graph.putEdge(3, 0);

		Assertions.assertEquals(asSet(0, 1, 2, 3), graph.getAssociatedPoints(0, true));
		Assertions.assertEquals(asSet(1, 2, 3), graph.getAssociatedPoints(0, false));

		Assertions.assertEquals(asSet(1, 2, 3, 0), graph.getAssociatedPoints(1, true));
		Assertions.assertEquals(asSet(2, 3, 0), graph.getAssociatedPoints(1, false));

		Assertions.assertEquals(asSet(2, 3, 0, 1), graph.getAssociatedPoints(2, true));
		Assertions.assertEquals(asSet(3, 0, 1), graph.getAssociatedPoints(2, false));

		Assertions.assertEquals(asSet(3, 0, 1, 2), graph.getAssociatedPoints(3, true));
		Assertions.assertEquals(asSet(0, 1, 2), graph.getAssociatedPoints(3, false));

		Assertions.assertTrue(graph.getAssociatedPoints(-1, false).isEmpty());
	}

	@Test
	public void testGetAdjacentPoints() {
		// 0 -- 1
		// |    |
		// 3 -- 2
		final Graph<Integer> graph = new Graph<>();
		graph.putEdge(0, 1);
		graph.putEdge(1, 2);
		graph.putEdge(2, 3);
		graph.putEdge(3, 0);

		Assertions.assertEquals(asSet(1, 3), graph.getAdjacentPoints(0));
		Assertions.assertEquals(asSet(2, 0), graph.getAdjacentPoints(1));
		Assertions.assertEquals(asSet(1, 3), graph.getAdjacentPoints(2));
		Assertions.assertEquals(asSet(2, 0), graph.getAdjacentPoints(3));
	}

	@Test
	public void testRemovePoint() {
		// 0 -- 1
		// |    |
		// 3 -- 2
		final Graph<Integer> graph = new Graph<>();
		graph.putEdge(0, 1);
		graph.putEdge(1, 2);
		graph.putEdge(2, 3);
		graph.putEdge(3, 0);

		// 0
		// |
		// 3 -- 2
		graph.removePoint(1);

		Assertions.assertEquals(asSet(3), graph.getAdjacentPoints(0));
		Assertions.assertTrue(graph.getAdjacentPoints(1).isEmpty());
		Assertions.assertEquals(asSet(3), graph.getAdjacentPoints(2));
		Assertions.assertEquals(asSet(2, 0), graph.getAdjacentPoints(3));
	}

	@SafeVarargs
	private static <T> Set<T> asSet(final T... ts) {
		return new LinkedHashSet<>(Arrays.asList(ts));
	}

}
