<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/style.css" />" rel="stylesheet">
<title>allReports</title>
</head>


<body>

	<div id="wrapper">
		<div id="header">
			<h2>Reports</h2>
		</div>
	</div>

	<div id="container">

		<div id="content">
			<c:if test="${not empty message}">
				<table>
					<tr>
						<td>
							<h1>${message}</h1>
						</td>
					</tr>
				</table>
			</c:if>
			<br>

			<form action="reports" method="post">
				<table>
					<tbody>
						<tr>
							<td>
								<label class="save">Start date</label>
							</td>
							<td>
								<input name="startDate" value="Sep 1,2010" class="button" />
							</td>
						</tr>
						<tr>
							<td>
								<label class="save">End date</label>
							</td>
							<td>
								<input name="endDate" value="Oct 1,2010" class="button" />
							</td>
						</tr>
						<tr>
							<td>
								<label class="save">Performer</label>
							</td>
							<td>
								<select name="performer" class="button">
									<c:forEach var="performer" items="${performers}">
										<option value="${performer.id }">${performer.name}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<label class="save">Time Period</label>
							</td>
							<td>
								<select name="timePeriod" class="button">
									<c:forEach var="timePeriod" items="${timePeriods}">
										<option value="${timePeriod}">${timePeriod}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</tbody>
				</table>

				<br>

				<br>
				<br>
				<input type="submit" value="Submit" class="button" />
			</form>


			<c:if test="${not empty reports}">
				<table>
					<tr>
						<th>Date</th>
						<th>Performer</th>
						<th>Activity</th>
					</tr>
					<c:forEach var="report" items="${reports}">
						<tr>
							<td>
								<!--  doesn't work pattern="MMM-dd-yyyy"" />  -->
								<strong>
									<fmt:formatDate type="date" pattern="MM dd,yyyy"
										value="${report.date}" />
								</strong>
							</td>
							<td>${report.performer }</td>
							<td>${report.activity }</td>
						</tr>
					</c:forEach>

				</table>
			</c:if>

		</div>

	</div>
	<p>
		<a href="index.jsp">Back to reports</a>
	</p>

</body>


</html>