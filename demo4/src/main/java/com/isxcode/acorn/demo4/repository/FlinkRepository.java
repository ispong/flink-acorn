package com.isxcode.acorn.demo4.repository;

import com.isxcode.acorn.demo4.pojo.entity.FlinkFlowEntity;
import com.isxcode.acorn.demo4.pojo.entity.FlinkNodeEntity;
import com.isxcode.oxygen.flysql.core.Flysql;
import org.springframework.stereotype.Repository;

@Repository
public class FlinkRepository {

    private final Flysql flysql;

    public FlinkRepository(Flysql flysql) {
        this.flysql = flysql;
    }

    public void addNode(FlinkNodeEntity flinkNodeEntity) {

        flysql.build().insert(FlinkNodeEntity.class).save(flinkNodeEntity);
    }

    public FlinkNodeEntity getNode(String nodeId) {

        return flysql.build().select(FlinkNodeEntity.class)
                .eq("nodeId",nodeId)
                .getOne();
    }

    public FlinkFlowEntity getFlow(String flowId) {

        return flysql.build().select(FlinkFlowEntity.class)
                .eq("flowId",flowId)
                .getOne();
    }
}
