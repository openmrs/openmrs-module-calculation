<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="View Token Registrations" otherwise="/login.htm" redirect="/module/calculation/calculationRegistrations.list" />
<%@ include file="localHeader.jsp" %>

<h2><spring:message code="calculation.CalculationRegistration.manage.pageTitle" /></h2>

<a href="calculationRegistration.form"><spring:message code="general.add"/></a>
<br/><br/>

<b class="boxHeader"><spring:message code="calculation.CalculationRegistration.listExistings.boxTitle"/></b>
<div class="box">
	<c:choose>
		<c:when test="${empty calculationRegistrations}">
			<spring:message code="general.none" />
		</c:when>
		<c:otherwise>
			<form method="post">
				<table>
					<thead>
						<th><spring:message code="calculation.CalculationRegistration.token"/></th>
						<th><spring:message code="calculation.CalculationRegistration.calculationName"/></th>
						<th><spring:message code="calculation.CalculationRegistration.providerClassName"/></th>
						<th><spring:message code="calculation.CalculationRegistration.configuration"/></th>
					</thead>
					<c:forEach var="calculationRegistration" items="${calculationRegistrations}">
						<tr>
							<td><a href="calculationRegistration.form?id=${calculationRegistration.id}">${calculationRegistration.token}</a></td>
							<td>${calculationRegistration.calculationName}</td>
							<td>${calculationRegistration.providerClassName}</td>
							<td>${calculationRegistration.configuration}</td>
						</tr>
					</c:forEach>
				</table>
			</form>		
		</c:otherwise>
	</c:choose>
</div>
<br/>

<%@ include file="/WEB-INF/template/footer.jsp"%>