package com.isxcode.acorn.connector.out;

import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.functions.util.PrintSinkOutputWriter;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.operators.StreamingRuntimeContext;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.sink.DynamicTableSink;
import org.apache.flink.table.connector.sink.SinkFunctionProvider;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.factories.DynamicTableSinkFactory;
import org.apache.flink.table.factories.FactoryUtil;
import org.apache.flink.table.types.DataType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OutTableSinkFactory implements DynamicTableSinkFactory {

    public static final String IDENTIFIER = "out";

    private final Map<String, String> columnInfos = new HashMap<>();

    public static final ConfigOption<String> OUT_IDENTIFIER =
        ConfigOptions.key("out-identifier")
            .stringType()
            .noDefaultValue()
            .withDescription("Message that identify print and is prefixed to the output of the value.");

    @Override
    public String factoryIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        return new HashSet<>();
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        Set<ConfigOption<?>> options = new HashSet<>();
        options.add(OUT_IDENTIFIER);
        return options;
    }

    @Override
    public DynamicTableSink createDynamicTableSink(Context context) {
        FactoryUtil.TableFactoryHelper helper = FactoryUtil.createTableFactoryHelper(this, context);
        helper.validate();
        ReadableConfig options = helper.getOptions();

        TableSchema schema = context.getCatalogTable().getSchema();

        schema.getTableColumns().forEach(e -> columnInfos.put(e.getName(), e.getType().toString()));

        return new OutSink(
            schema.toPhysicalRowDataType(),
            options.get(OUT_IDENTIFIER), columnInfos);
    }

    private static class OutSink implements DynamicTableSink {

        private final DataType type;
        private final String printIdentifier;
        private final Map<String, String> columnInfos;

        private OutSink(DataType type, String printIdentifier, Map<String, String> columnInfos) {

            this.columnInfos = columnInfos;
            this.type = type;
            this.printIdentifier = printIdentifier;
        }

        @Override
        public ChangelogMode getChangelogMode(ChangelogMode requestedMode) {
            return requestedMode;
        }

        @Override
        public SinkRuntimeProvider getSinkRuntimeProvider(DynamicTableSink.Context context) {
            DataStructureConverter converter = context.createDataStructureConverter(type);
            return SinkFunctionProvider.of(
                new RowDataPrintFunction(converter, printIdentifier, false, columnInfos));
        }

        @Override
        public DynamicTableSink copy() {
            return new OutSink(type, printIdentifier, columnInfos);
        }

        @Override
        public String asSummaryString() {
            return "Print to System.out";
        }
    }

    private static class RowDataPrintFunction extends RichSinkFunction<RowData> {

        private static final long serialVersionUID = 1L;

        private final DynamicTableSink.DataStructureConverter converter;
        private final PrintSinkOutputWriter<String> writer;
        private final Map<String, String> columnInfos;

        private RowDataPrintFunction(
            DynamicTableSink.DataStructureConverter converter, String printIdentifier, boolean stdErr, Map<String, String> columnInfos) {
            this.columnInfos = columnInfos;
            this.converter = converter;
            this.writer = new PrintSinkOutputWriter<>(printIdentifier, stdErr);
        }

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            StreamingRuntimeContext context = (StreamingRuntimeContext) getRuntimeContext();
            writer.open(context.getIndexOfThisSubtask(), context.getNumberOfParallelSubtasks());
        }

        @Override
        public void invoke(RowData value, Context context) {
            List<String> colData = new ArrayList<>();
            AtomicInteger index = new AtomicInteger();
            columnInfos.forEach((k, v) -> {
                if ("int".equalsIgnoreCase(v)){
                    colData.add(String.valueOf(value.getInt(index.get())));
                }
                if ("string".equalsIgnoreCase(v)) {
                    colData.add(String.valueOf(value.getString(index.get())));
                }
                index.getAndIncrement();
            });
            System.out.println(JSON.toJSONString(colData));
        }
    }
}
