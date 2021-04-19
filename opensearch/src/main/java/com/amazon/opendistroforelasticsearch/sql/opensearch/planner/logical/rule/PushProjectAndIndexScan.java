/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 *
 * Modifications Copyright OpenSearch Contributors. See
 * GitHub history for details.
 */

/*
 *
 *    Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License").
 *    You may not use this file except in compliance with the License.
 *    A copy of the License is located at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    or in the "license" file accompanying this file. This file is distributed
 *    on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *    express or implied. See the License for the specific language governing
 *    permissions and limitations under the License.
 *
 */

package com.amazon.opendistroforelasticsearch.sql.opensearch.planner.logical.rule;

import static com.amazon.opendistroforelasticsearch.sql.opensearch.planner.logical.rule.OptimizationRuleUtils.findReferenceExpressions;
import static com.amazon.opendistroforelasticsearch.sql.planner.optimizer.pattern.Patterns.source;
import static com.facebook.presto.matching.Pattern.typeOf;

import com.amazon.opendistroforelasticsearch.sql.expression.ReferenceExpression;
import com.amazon.opendistroforelasticsearch.sql.opensearch.planner.logical.OpenSearchLogicalIndexScan;
import com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalPlan;
import com.amazon.opendistroforelasticsearch.sql.planner.logical.LogicalProject;
import com.amazon.opendistroforelasticsearch.sql.planner.optimizer.Rule;
import com.facebook.presto.matching.Capture;
import com.facebook.presto.matching.Captures;
import com.facebook.presto.matching.Pattern;
import java.util.Set;

/**
 * Push Project list into ElasticsearchLogicalIndexScan.
 */
public class PushProjectAndIndexScan implements Rule<LogicalProject> {

  private final Capture<OpenSearchLogicalIndexScan> indexScanCapture;

  private final Pattern<LogicalProject> pattern;

  private Set<ReferenceExpression> pushDownProjects;

  /**
   * Constructor of MergeProjectAndIndexScan.
   */
  public PushProjectAndIndexScan() {
    this.indexScanCapture = Capture.newCapture();
    this.pattern = typeOf(LogicalProject.class).matching(
        project -> {
          pushDownProjects = findReferenceExpressions(project.getProjectList());
          return !pushDownProjects.isEmpty();
        }).with(source()
        .matching(typeOf(OpenSearchLogicalIndexScan.class)
            .matching(indexScan -> !indexScan.hasProjects())
            .capturedAs(indexScanCapture)));

  }

  @Override
  public Pattern<LogicalProject> pattern() {
    return pattern;
  }

  @Override
  public LogicalPlan apply(LogicalProject project,
                           Captures captures) {
    OpenSearchLogicalIndexScan indexScan = captures.get(indexScanCapture);
    indexScan.setProjectList(pushDownProjects);
    return new LogicalProject(indexScan, project.getProjectList());
  }
}
