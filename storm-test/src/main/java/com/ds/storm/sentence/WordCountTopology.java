package com.ds.storm.sentence;

import java.util.Map;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.Nimbus.Client;
import backtype.storm.scheduler.Cluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.NimbusClient;
import backtype.storm.utils.Utils;

public class WordCountTopology {

	private static final String SENTENCE_SPOUT_ID = "sentence-spout";
	private static final String SPLIT_BOLT_ID = "split-bolt";
	private static final String COUNT_BOLT_ID = "count-bolt";
	private static final String REPORT_BOLT_ID = "report-bolt";

	public static void main(String[] args) throws Exception {
		SentenceSpout spout = new SentenceSpout();
		SplitSentenceBolt splitBolt = new SplitSentenceBolt();
		WordCountBolt countBolt = new WordCountBolt();
		ReportBolt reportBolt = new ReportBolt();

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(SENTENCE_SPOUT_ID, spout);
		builder.setBolt(SPLIT_BOLT_ID, splitBolt).shuffleGrouping(SENTENCE_SPOUT_ID);
		builder.setBolt(COUNT_BOLT_ID, countBolt).fieldsGrouping(SPLIT_BOLT_ID, new Fields("word"));
		builder.setBolt(REPORT_BOLT_ID, reportBolt).globalGrouping(COUNT_BOLT_ID);

		Config conf = new Config();
		conf.setDebug(false);

		//读取本地storm配置文件
		//		StormSubmitter.submitJar(stormConf, localJar);
//		StormSubmitter.submitTopology("WordCountTopology", stormConf, builder.createTopology());
		LocalCluster lc = new LocalCluster();
		lc.submitTopology("WordCountTopology", conf, builder.createTopology());
		Thread.sleep(10 * 1000);
		lc.killTopology("WordCountTopology");
		lc.shutdown();
	}
}
