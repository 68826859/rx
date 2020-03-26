package com.rx.pub.file.base;

public class RxFilePathParam {

	public final static String WIDTH = "width";
	public final static String HEIGHT = "height";
	public final static String QUERY = "query";
	
	
	private Integer width;
	
	private Integer height;
	
	private String query;
	
	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}
	/*
	public static void main(String[] args) throws URISyntaxException, MalformedURLException {
		
		URL url = new URL("a=2&d=2");
		
		Uri
		
		System.out.println(url.getQuery());
		
		URIBuilder bd = new URIBuilder("www.baidu.com");
		bd.addParameter("a","2222");
		bd.addParameter("b","33333");
		bd.setCustomQuery("a=2&d=2");
		
		System.out.println(bd.build().toString());
		List<NameValuePair> lt = bd.getQueryParams();
		for(NameValuePair p: lt) {
			System.out.println(p.getName()+"::"+p.getValue());
		}
	}
	*/

	public String getQuery() {
		return query;
	}

	public RxFilePathParam setQuery(String query) {
		this.query = query;
		return this;
	}
}
