package com.ds.storm.sentence;

import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class SentenceSpout extends BaseRichSpout {

	private static final long serialVersionUID = -1331643911502965184L;
	
	private SpoutOutputCollector collector;

	private String[] sentences = {
		"my dog has fleas",
		"i like cold beverages",
		"the dog ate my homework",
		"i dont't think i like fleas"
	};

	private int index = 0;
	
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	public void nextTuple() {
		this.collector.emit(new Values(sentences[index]));
		index ++;
		if(index >= sentences.length){
			index = 0;
		}
		Utils.sleep(1);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}

}
