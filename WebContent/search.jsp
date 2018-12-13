<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		Submission DDL <input type="text" name="SubmissionDDL">
		Meeting Time <input type="text" name="MeetingTime"> 
		<input type="submit" value="搜索">
	</form>
	<div style="background-color: white; ">
		<table width="1250" style="table-layout: fixed">
			<tr style="background-color: #00CCFF; height: 30px">
				<th>Subject</th>	
				<th>Submission DDL</th>
				<th>Meeting Time</th>
				<th>Detail Message</th>
			</tr>
			<tr class="contents">
				<td>My Subject</td>
			</tr>
		</table>
	</div>
	
</body>
</html>