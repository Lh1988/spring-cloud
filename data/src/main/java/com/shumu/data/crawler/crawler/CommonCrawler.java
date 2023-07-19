package com.shumu.data.crawler.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
/**
* @description: 
* @author: Li
* @date: 2023-07-18
*/
public class CommonCrawler extends BreadthCrawler{

    public CommonCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }
    
}
