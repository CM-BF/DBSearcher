<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DBWorld</title>
</head>
<body style="background-image: url(background.jpg)">
	<h1 style="color:rgba(255,255,255, 0.5);">
		Citrine   DBWorld Searcher
	</h1>
	<form action="search.jsp" method="get">
		Subject <br/> <input type="text" name="subject"> <br/>
		Topic <br/> <input type="text" name="Topic">	<br />
		Submission DDL <br/><input type="text" name="SubmissionDDL"> <br/>
		Conference Time <br/> <input type="text" name="ConferenceTime"> <br />
		Important Date <br/> <input type="text" name="ImportantDate"> <br/>
		Site <br/> <input type="text" name="Site"> <br />
		<input type="submit" value="搜索">
	</form>
	
</body>
</html>