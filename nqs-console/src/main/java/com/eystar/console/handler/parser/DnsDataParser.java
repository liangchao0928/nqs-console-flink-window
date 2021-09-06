package com.eystar.console.handler.parser;


import com.eystar.gen.entity.gwdata.GwData;

public class DnsDataParser extends DetailAbstractDataParser{

	public DnsDataParser() {
		super("DNS_DETAIL");
	}

	@Override
	public void fillEachDetailRecord(GwData record) {}


}
