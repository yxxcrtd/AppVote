package com.chinaedustar.app.common;

public class HtmlPager {

	public static String render(int pageCount, int offsetCount, int current) {
		String preLabel = "<";
		String nextLabel = ">";
		String h = "";
		if (pageCount <= 1)
			return h;
		int MaxStart = pageCount - offsetCount * 2;
		if (MaxStart < 1)
			MaxStart = 1;
		int MinEnd = offsetCount * 2 + 1;

		int start = current - offsetCount;
		int end = current + offsetCount;
		if (start < 1) {
			start = 1;
			end = MinEnd;
		}
		if (end >= pageCount) {
			end = pageCount - 1;
			start = MaxStart;
		}
		boolean showGoInput = false;
		h = "<a href='javascript:void(0)' class='w_pre'>" + preLabel + "</a> ";
		if (current == 1)
			h = "";
		if (start > 1)
			h += "<a href='javascript:void(0)'>1</a> ";
		if (start > 2) {
			showGoInput = true;
			h += "<span>...</span>";
		}

		for (int i = start; i <= end; i++) {
			if (i == current) {
				h += "<a href='javascript:void(0)' class='w_currPage'>" + i + "</a> ";
			} else {
				h += "<a href='javascript:void(0)'>" + i + "</a> ";
			}
		}
		if (start < MaxStart) {
			showGoInput = true;
		}

		if (end < (pageCount - 1)) {
			h += "<span>...</span>";
		}

		if (current == pageCount) {
			h += " <a href='javascript:void(0)' class='w_currPage'>" + current + "</a>";
		} else {
			h += " <a href='javascript:void(0)'>" + pageCount + "</a>";
		}
		if (current != pageCount) {
			h += "<a href='javascript:void(0)' class='w_next'>" + nextLabel + "</a> ";
		}
		if (showGoInput) {
			h += " <span class='w_quick clearfix'><em>跳转至</em><input id='goPage' minValue='1' maxValue='" + pageCount + "' value='" + current
					+ "' type='text' class='w_goPage'><button class='w_goBtn'>GO</button></span>";
		}
		return h;
	}
	
}
