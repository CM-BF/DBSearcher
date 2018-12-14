<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@   page   import= "web.demo.* "%>
<%@		page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
td {text-align:center; word-wrap:break-word; word-break:break-all}
tr.contents{background-color: #CCFFFF}
</style>
<title>Citrine DBWorld Searcher</title>
</head>
<body style="background-image: url(background.jpg)">
	<h1 style="color:rgba(255,255,255, 0.5);">
		Citrine   DBWorld Searcher
	</h1>
	<form action="search.jsp" method="get">
		Subject <input type="text" name="subject"> 
		Topic <input type="text" name="Topic">
		Submission DDL <input type="text" name="SubmissionDDL"><br/>
		Conference Time <input type="text" name="ConferenceTime"> 
		Important Date <input type="text" name="ImportantDate">
		Site <input type="text" name="Site"> <br/>
		<input type="submit" value="搜索">
	</form>
	<form action="search.jsp" method="get">
		<input type="hidden" name="subject" value="<%=request.getParameter("subject")%>"> 
		<input type="hidden" name="Topic" value="<%=request.getParameter("Topic")%>">
		<input type="hidden" name="SubmissionDDL" value="<%=request.getParameter("SubmissionDDL")%>">
		<input type="hidden" name="ConferenceTime" value="<%=request.getParameter("ConferenceTime")%>"> 
		<input type="hidden" name="ImportantDate" value="<%=request.getParameter("ImportantDate")%>">
		<input type="hidden" name="Site" value="<%=request.getParameter("Site")%>"> 
		Page: <input type="text" name="Page">
		<input type="submit" value="跳转">
	</form>

	<div style="background-color: white; ">
<%

	int hitsPerPage = 10;
	Search searcher = new Search();	
	List<Map<String, String>> resultdocs = searcher.search(request.getParameter("subject"),
			request.getParameter("SubmissionDDL"), request.getParameter("ConferenceTime"),
			request.getParameter("Topic"), request.getParameter("Site"), request.getParameter("ImportantDate"));
	int Page=1;
	if(request.getParameter("Page")!=null&&searcher.isInteger(request.getParameter("Page"))){
		Page = Integer.parseInt(request.getParameter("Page"));
	}
%>
<p><%=resultdocs.size() %> mails are found. Totally, <%=resultdocs.size()/10 +1 %> Pages</p>
		<table style="table-layout: fixed; width: 100%">
			<tr style="background-color: #00CCFF; height: 30px">
				<th>Subject</th>	
				<th>Submission DDL</th>
				<th>Conference Time</th>
				<th>Important Date</th>
				<th>Detail Message</th>
				<th>Site</th>
			</tr>
			<%for(int i=(Page-1)*10;i<Page*10 && i<resultdocs.size();i++){ %>
			<tr class="contents">
				<% Map<String, String> resultdoc = resultdocs.get(i); %>
				<td><%=resultdoc.get("Subject") %></td>
				<td><%=resultdoc.get("SubmissionDDL").replaceAll("-", " ") %></td>
				<td><%=resultdoc.get("ConferenceTime") %></td>
				<td><%=resultdoc.get("ImportantDate").replaceAll("\n", "<br/>") %></td>
				<td><a href="<%=resultdoc.get("DetailMessage") %>"><%=resultdoc.get("DetailMessage") %></a></td>
				<td><%=resultdoc.get("Site") %></td>
			</tr>
			<tr>
				<td colspan="5" style="text-align: left">Topic:<br/><%=resultdoc.get("Topic").replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp") %></td>
			</tr>
			<%} %>
		</table>
	</div>
	
</body>
</html>