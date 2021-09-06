package com.eystar.console.handler.parser;


import com.eystar.gen.entity.gwdata.GwData;

public class DnsResolutionDataParser extends DetailAbstractDataParser{

	public DnsResolutionDataParser(String taskTypeName) {
		super("DNS_RESOLUTION_DETAIL");
	}

	@Override
	public void fillEachDetailRecord(GwData record) {}


}
