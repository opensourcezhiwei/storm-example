package com.ds.storm.test.topology;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;

import com.ds.storm.test.bolt.WordBolt;
import com.ds.storm.test.spout.NameSpout;

public class WordTopology {

	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("name", new NameSpout(), 5);
		builder.setBolt("exclaim", new WordBolt(), 5).shuffleGrouping("name");

		Config conf = new Config();
		conf.setDebug(false);
		conf.put(Config.TOPOLOGY_DEBUG, false);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(10);
			StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
		}
	}

}
