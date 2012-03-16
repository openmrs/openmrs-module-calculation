<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Manage Token Registrations" otherwise="/login.htm" redirect="/module/calculation/patientCalculationTest.form" />
<%@ include file="localHeader.jsp" %>

<style>
	.calculationTable th {text-align:left;}
	.resultTable th,td {padding-left:10px; padding-right:10px; text-align:left;}
</style>

<h3><spring:message code="calculation.CalculationRegistration.test.title"/></h3>

<table class="calculationTable">
	<tr>
		<th><spring:message code="calculation.CalculationRegistration.token"/></th>
		<td><a href="calculationRegistration.form?id=${calculationRegistration.id}">${calculationRegistration.token}</a></td>
	</tr>
	<tr>
		<th><spring:message code="calculation.CalculationRegistration.providerClassName"/></th>
		<td>${calculationRegistration.providerClassName}</td>
	</tr>
	<tr>
		<th><spring:message code="calculation.CalculationRegistration.calculationName"/></th>
		<td>${calculationRegistration.calculationName}</td>
	</tr>
	<tr>
		<th><spring:message code="calculation.CalculationRegistration.configuration"/></th>
		<td>${calculationRegistration.configuration}</td>
	</tr>
</table>
<br/><br/>
<b></b><spring:message code="calculation.CalculationRegistration.test.enterPatientIds"/></b>
<form>
	<input type="hidden" name="id" value="${id}"/>
	<textarea name="patientIds" rows="2", cols="100">${patientIds}</textarea>
	<input type="submit" value="<spring:message code="calculation.CalculationRegistration.test"/>"/>
</form>
<hr/>

<c:choose>
	<c:when test="${!empty error}">
		${error}
	</c:when>
	<c:when test="${!empty result}">
		<spring:message code="calculation.CalculationRegistration.test.evaluationTime"/>: ${evaluationTime} ms
		<br/>
		<table class="resultTable">
			<tr>
				<th><spring:message code="calculation.CalculationRegistration.test.patientId"/></th>
				<th><spring:message code="calculation.CalculationRegistration.test.result"/></th>
				<th><spring:message code="calculation.CalculationRegistration.test.resultType"/></th>
			</tr>
			<c:forEach items="${result}" var="entry">
				<tr>
					<td>${entry.key}</td>
					<td>${entry.value}</td>
					<td>${entry.value['class'].simpleName}</td>
				</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
	
	</c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/template/footer.jsp"%>