package minispark;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by lzb on 4/9/17.
 */
public class HdfsSplitReader {
    public static ArrayList<ArrayList<String>> HdfsGetSplitInfo(String fileName) throws IOException {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        JobConf conf = new JobConf();
        FileInputFormat.setInputPaths(conf, fileName);
        TextInputFormat format = new TextInputFormat();
        format.configure(conf);
        InputSplit[] splits = format.getSplits(conf, 0);
        for (InputSplit split : splits) {
            String[] locations = split.getLocations();
            ArrayList arrayList = new ArrayList<String>();
            Collections.addAll(arrayList, locations);
            result.add(arrayList);
        }
        System.out.println(result);
        return result;
    }

    public static ArrayList<String> HdfsSplitRead(String fileName, int index) throws IOException {
        JobConf conf = new JobConf();
        FileInputFormat.setInputPaths(conf, fileName);
        TextInputFormat format = new TextInputFormat();
        format.configure(conf);
        InputSplit[] splits = format.getSplits(conf, 0);
        LongWritable key = new LongWritable();
        Text value = new Text();
        RecordReader<LongWritable, Text> recordReader =
                format.getRecordReader(splits[index], conf, Reporter.NULL);

        ArrayList<String> result = new ArrayList<>();

        while (recordReader.next(key, value)) {
            result.add(value.toString());
        }
        return result;
    }
}
