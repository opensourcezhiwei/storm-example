package com.ds.storm.test.bolt;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

/**
 * 	关于初始化的prepare函数和声明输出流的函数declareOutputFields不在重新说明，和Spout的相关函数类似。

	这里定义了一个map，用来统计名字出现的次数，另外名字修改后会打印到控制台信息中。

	统计计算部分都在execute接口中实现，较复杂的情况下，可以拆分为多个Bolt来分别执行不同的计算部分。

 * @author jackson
 *
 */
public class WordBolt extends BaseRichBolt {

	private static final long serialVersionUID = -6193642025927521424L;

	private OutputCollector collector;

	private Map<String, Integer> nameCountMap = new HashMap<String, Integer>();

	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	public void execute(Tuple input) {
		//第一步:统计计算
		Integer value = 0;
		if (nameCountMap.containsKey(input.getValue(0))) {
			value = nameCountMap.get(input.getString(0));
		}
		nameCountMap.put(input.getString(0), ++value);

		//第二步:输出
		System.out.println("key = " + input.getString(0) + ", value = " + value);

		collector.ack(input);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("name"));
	}

}
