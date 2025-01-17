/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.inlong.sort.cdc.mongodb.source.config;

import static com.ververica.cdc.connectors.mongodb.source.utils.MongoUtils.buildConnectionString;
import static org.apache.flink.util.Preconditions.checkNotNull;

import com.ververica.cdc.connectors.base.options.StartupOptions;
import com.ververica.cdc.connectors.mongodb.source.MongoDBSource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import org.apache.inlong.sort.base.Constants;
import org.apache.inlong.sort.cdc.base.config.MetricConfig;
import org.apache.inlong.sort.cdc.base.config.SourceConfig;

/** A MongoDB Source configuration which is used by {@link MongoDBSource}.
 * Copy from com.ververica:flink-connector-mongodb-cdc:2.3.0.
 */
public class MongoDBSourceConfig implements SourceConfig, MetricConfig {

    private static final long serialVersionUID = 1L;

    private final String hosts;
    @Nullable
    private final String username;
    @Nullable
    private final String password;
    @Nullable
    private final List<String> databaseList;
    @Nullable
    private final List<String> collectionList;
    private final String connectionString;
    private final int batchSize;
    private final int pollAwaitTimeMillis;
    private final int pollMaxBatchSize;
    private final boolean updateLookup;
    private final StartupOptions startupOptions;
    private final int heartbeatIntervalMillis;
    private final int splitMetaGroupSize;
    private final int splitSizeMB;
    private final String inlongMetric;
    private final String inlongAudit;

    MongoDBSourceConfig(
            String hosts,
            @Nullable String username,
            @Nullable String password,
            @Nullable List<String> databaseList,
            @Nullable List<String> collectionList,
            @Nullable String connectionOptions,
            int batchSize,
            int pollAwaitTimeMillis,
            int pollMaxBatchSize,
            boolean updateLookup,
            StartupOptions startupOptions,
            int heartbeatIntervalMillis,
            int splitMetaGroupSize,
            int splitSizeMB,
            String inlongMetric,
            String inlongAudit) {
        this.hosts = checkNotNull(hosts);
        this.username = username;
        this.password = password;
        this.databaseList = databaseList;
        this.collectionList = collectionList;
        this.connectionString =
                buildConnectionString(username, password, hosts, connectionOptions)
                        .getConnectionString();
        this.batchSize = batchSize;
        this.pollAwaitTimeMillis = pollAwaitTimeMillis;
        this.pollMaxBatchSize = pollMaxBatchSize;
        this.updateLookup = updateLookup;
        this.startupOptions = startupOptions;
        this.heartbeatIntervalMillis = heartbeatIntervalMillis;
        this.splitMetaGroupSize = splitMetaGroupSize;
        this.splitSizeMB = splitSizeMB;
        this.inlongMetric = inlongMetric;
        this.inlongAudit = inlongAudit;
    }

    public String getHosts() {
        return hosts;
    }

    public String getConnectionString() {
        return connectionString;
    }

    @Nullable
    public String getUsername() {
        return username;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    @Nullable
    public List<String> getDatabaseList() {
        return databaseList;
    }

    @Nullable
    public List<String> getCollectionList() {
        return collectionList;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public int getPollAwaitTimeMillis() {
        return pollAwaitTimeMillis;
    }

    public int getPollMaxBatchSize() {
        return pollMaxBatchSize;
    }

    public int getHeartbeatIntervalMillis() {
        return heartbeatIntervalMillis;
    }

    public boolean isUpdateLookup() {
        return updateLookup;
    }

    @Override
    public StartupOptions getStartupOptions() {
        return startupOptions;
    }

    @Override
    public int getSplitSize() {
        return splitSizeMB;
    }

    @Override
    public int getSplitMetaGroupSize() {
        return splitMetaGroupSize;
    }

    @Override
    public boolean isIncludeSchemaChanges() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MongoDBSourceConfig that = (MongoDBSourceConfig) o;
        return batchSize == that.batchSize
                && pollAwaitTimeMillis == that.pollAwaitTimeMillis
                && pollMaxBatchSize == that.pollMaxBatchSize
                && updateLookup == that.updateLookup
                && startupOptions == that.startupOptions
                && heartbeatIntervalMillis == that.heartbeatIntervalMillis
                && splitMetaGroupSize == that.splitMetaGroupSize
                && splitSizeMB == that.splitSizeMB
                && inlongMetric == that.inlongMetric
                && inlongAudit == that.inlongAudit
                && Objects.equals(hosts, that.hosts)
                && Objects.equals(username, that.username)
                && Objects.equals(password, that.password)
                && Objects.equals(databaseList, that.databaseList)
                && Objects.equals(collectionList, that.collectionList)
                && Objects.equals(connectionString, that.connectionString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                hosts,
                username,
                password,
                databaseList,
                collectionList,
                connectionString,
                batchSize,
                pollAwaitTimeMillis,
                pollMaxBatchSize,
                updateLookup,
                startupOptions,
                heartbeatIntervalMillis,
                splitMetaGroupSize,
                splitSizeMB,
                inlongMetric,
                inlongAudit);
    }

    @Override
    public String getInlongMetric() {
        return inlongMetric;
    }

    @Override
    public String getInlongAudit() {
        return inlongAudit;
    }

    @Override
    public List<String> getMetricLabelList() {
        return Arrays.asList(Constants.DATABASE_NAME, Constants.COLLECTION_NAME);
    }
}
