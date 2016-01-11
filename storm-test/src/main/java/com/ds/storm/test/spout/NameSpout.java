package com.ds.storm.test.spout;

import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

/**
 * 	首先，自定义的Spout需要继承Storm的相关Spout的接口，例如BaseRichSpout或者IRichSpout等。

	其次，在open函数中，实现资源的初始化等操作，这里没有特殊操作，只将流获取绑定到本身Collector上即可。

	第三，声明输出流的格式，即 declareOutputFields函数。

	最后，实现流的生成操作nextTuple函数，这里在人名中随机选择一个，并通过emit进行发送，Bolt接收到这个人名，并进行下一步的处理。

	至此，一个简单的Spout就完成了。

 * @author jackson
 *
 */
public class NameSpout extends BaseRichSpout {

	private static final long serialVersionUID = 1169235483145755473L;

	private SpoutOutputCollector collector;//喷口输出

	/** 初始化资源操作 */
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	public void nextTuple() {
		String[] names = new String[] { "nathan", "mike", "jackson", "golda", "bertels" };
		Random r = new Random();
		String name = names[r.nextInt(names.length)];

		Utils.sleep(10);
		collector.emit(new Values(name));
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("name"));
	}

}
