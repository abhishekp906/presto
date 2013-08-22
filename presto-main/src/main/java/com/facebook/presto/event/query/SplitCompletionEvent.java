package com.facebook.presto.event.query;

import com.facebook.presto.execution.QueryId;
import com.facebook.presto.execution.StageId;
import com.facebook.presto.execution.TaskId;
import com.google.common.base.Preconditions;
import io.airlift.event.client.EventField;
import io.airlift.event.client.EventType;
import io.airlift.units.Duration;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import static com.facebook.presto.operator.OperatorStats.SmallCounterStat.SmallCounterStatSnapshot;

@Immutable
@EventType("SplitCompletion")
public class SplitCompletionEvent
{
    private final QueryId queryId;
    private final StageId stageId;
    private final TaskId taskId;

    private final String environment;

    private final Duration queuedTimeMs;
    private final DateTime executionStartTime;

    private final Long timeToFirstByteMs;
    private final Long timeToLastByteMs;

    private final SmallCounterStatSnapshot completedDataSize;
    private final SmallCounterStatSnapshot completedPositions;

    private final Long wallTimeMs;
    private final Long cpuTimeMs;
    private final Long userTimeMs;

    private final String splitInfoJson;

    public SplitCompletionEvent(
            QueryId queryId,
            StageId stageId,
            TaskId taskId,
            String environment,
            Duration queuedTimeMs,
            @Nullable DateTime executionStartTime,
            @Nullable Duration timeToFirstByte,
            @Nullable Duration timeToLastByte,
            SmallCounterStatSnapshot completedDataSize,
            SmallCounterStatSnapshot completedPositions,
            @Nullable Duration wallTime,
            @Nullable Duration cpuTime,
            @Nullable Duration userTime,
            String splitInfoJson)
    {
        Preconditions.checkNotNull(queryId, "queryId is null");
        Preconditions.checkNotNull(stageId, "stageId is null");
        Preconditions.checkNotNull(taskId, "taskId is null");
        Preconditions.checkNotNull(completedDataSize, "completedDataSize is null");
        Preconditions.checkNotNull(completedPositions, "completedPositions is null");
        Preconditions.checkNotNull(splitInfoJson, "splitInfoJson is null");

        this.queryId = queryId;
        this.stageId = stageId;
        this.taskId = taskId;
        this.environment = environment;
        this.queuedTimeMs = queuedTimeMs;
        this.executionStartTime = executionStartTime;
        this.timeToFirstByteMs = durationToMillis(timeToFirstByte);
        this.timeToLastByteMs = durationToMillis(timeToLastByte);
        this.completedDataSize = completedDataSize;
        this.completedPositions = completedPositions;
        this.wallTimeMs = durationToMillis(wallTime);
        this.cpuTimeMs = durationToMillis(cpuTime);
        this.userTimeMs = durationToMillis(userTime);
        this.splitInfoJson = splitInfoJson;
    }

    @Nullable
    private static Long durationToMillis(@Nullable Duration duration)
    {
        if (duration == null) {
            return null;
        }
        return duration.toMillis();
    }

    @EventField
    public String getQueryId()
    {
        return queryId.toString();
    }

    @EventField
    public String getStageId()
    {
        return stageId.toString();
    }

    @EventField
    public String getTaskId()
    {
        return taskId.toString();
    }

    @EventField
    public String getEnvironment()
    {
        return environment;
    }

    @EventField
    public long getQueuedTimeMs()
    {
        return queuedTimeMs.toMillis();
    }

    @EventField
    public DateTime getExecutionStartTime()
    {
        return executionStartTime;
    }

    @EventField
    public Long getTimeToFirstByteMs()
    {
        return timeToFirstByteMs;
    }

    @EventField
    public Long getTimeToLastByteMs()
    {
        return timeToLastByteMs;
    }

    @EventField
    public long getCompletedDataSizeTotal()
    {
        return completedDataSize.getTotalCount();
    }

    @EventField
    public double getCompletedDataSizeCountTenSec()
    {
        return completedDataSize.getTenSeconds().getCount();
    }

    @EventField
    public double getCompletedDataSizeRateTenSec()
    {
        return completedDataSize.getTenSeconds().getRate();
    }

    @EventField
    public double getCompletedDataSizeCountThirtySec()
    {
        return completedDataSize.getThirtySeconds().getCount();
    }

    @EventField
    public double getCompletedDataSizeRateThirtySec()
    {
        return completedDataSize.getThirtySeconds().getRate();
    }

    @EventField
    public double getCompletedDataSizeCountOneMin()
    {
        return completedDataSize.getOneMinute().getCount();
    }

    @EventField
    public double getCompletedDataSizeRateOneMin()
    {
        return completedDataSize.getOneMinute().getRate();
    }

    @EventField
    public long getCompletedPositionsTotal()
    {
        return completedPositions.getTotalCount();
    }

    @EventField
    public double getCompletedPositionsCountTenSec()
    {
        return completedPositions.getTenSeconds().getCount();
    }

    @EventField
    public double getCompletedPositionsRateTenSec()
    {
        return completedPositions.getTenSeconds().getRate();
    }

    @EventField
    public double getCompletedPositionsCountThirtySec()
    {
        return completedPositions.getThirtySeconds().getCount();
    }

    @EventField
    public double getCompletedPositionsRateThirtySec()
    {
        return completedPositions.getThirtySeconds().getRate();
    }

    @EventField
    public double getCompletedPositionsCountOneMin()
    {
        return completedPositions.getOneMinute().getCount();
    }

    @EventField
    public double getCompletedPositionsRateOneMin()
    {
        return completedPositions.getOneMinute().getRate();
    }

    @EventField
    public Long getWallTimeMs()
    {
        return wallTimeMs;
    }

    @EventField
    public Long getCpuTimeMs()
    {
        return cpuTimeMs;
    }

    @EventField
    public Long getUserTimeMs()
    {
        return userTimeMs;
    }

    @EventField
    public String getSplitInfoJson()
    {
        return splitInfoJson;
    }
}