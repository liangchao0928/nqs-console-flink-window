package com.eystar.console.handler.parser;


import com.eystar.gen.entity.gwdata.GwData;

public class TraceDataParser extends DetailAbstractDataParser{

	public TraceDataParser() {
		super("TRACE_DETAIL");
	}
	
	@Override
	public void fillEachDetailRecord(GwData record) {}

}
