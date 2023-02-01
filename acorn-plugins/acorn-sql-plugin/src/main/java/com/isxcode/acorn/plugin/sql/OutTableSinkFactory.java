package com.isxcode.acorn.plugin.sql;

import org.apache.flink.api.common.functions.util.PrintSinkOutputWriter;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.operators.StreamingRuntimeContext;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.sink.DynamicTableSink;
import org.apache.flink.table.connector.sink.SinkFunctionProvider;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.factories.DynamicTableSinkFactory;
import org.apache.flink.table.factories.FactoryUtil;
import org.apache.flink.table.types.DataType;

import java.util.HashSet;
import java.util.Set;

import static org.apache.flink.configuration.ConfigOptions.key;

public class OutTableSinkFactory implements DynamicTableSinkFactory {

    public static final String IDENTIFIER = "out";

    public static final ConfigOption<String> OUT_IDENTIFIER =
        key("out-identifier")
            .stringType()
            .noDefaultValue()
            .withDescription(
                "Message that identify print and is prefixed to the output of the value.");

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
        return new OutSink(
            context.getCatalogTable().getSchema().toPhysicalRowDataType(),
            options.get(OUT_IDENTIFIER));
    }

    private static class OutSink implements DynamicTableSink {

        private final DataType type;
        private final String printIdentifier;

        private OutSink(DataType type, String printIdentifier) {
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
                new RowDataPrintFunction(converter, printIdentifier, true));
        }

        @Override
        public DynamicTableSink copy() {
            return new OutSink(type, printIdentifier);
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

        private RowDataPrintFunction(
            DynamicTableSink.DataStructureConverter converter, String printIdentifier, boolean stdErr) {
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
            String rowKind = value.getRowKind().shortString();
            Object data = converter.toExternal(value);
            writer.write(rowKind + "(" + data + ")");
        }
    }
}
