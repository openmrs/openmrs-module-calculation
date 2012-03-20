<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Manage Token Registrations" otherwise="/login.htm" redirect="/module/calculation/patientCalculationTest.form" />
<%@ include file="localHeader.jsp" %>

<style>
	.calculationTable th {text-align:left;}
	.resultTable th,td {padding-left:10px; padding-right:10px; text-align:left;}
</style>

<h3><spring:message code="calculation.CalculationRegistration.test.title"/></h3>

<table width="100%">
	<tr>
		<td valign="top" style="white-space:nowrap;">
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
			<br/>
			<form>
				<input type="hidden" name="id" value="${id}"/>
				<spring:message code="calculation.CalculationRegistration.test.randomPatientIds"/>
				<input type="text" name="randomIds" size="10" value="${randomIds}"/>
				<br/>
				 - <spring:message code="general.or"/> -
				<br/>
				<spring:message code="calculation.CalculationRegistration.test.enterPatientIds"/><br/>
				<textarea name="patientIds" rows="4" cols="30">${patientIds}</textarea>
				<br/><br/>
				<c:if test="${!empty calculation.parameterDefinitionSet}">
					<spring:message code="calculation.parameters"/><br/>
					<table>
						<c:forEach items="${calculation.parameterDefinitionSet}" var="pd">
							<tr>
								<td>${pd.name}:</td>
								<td>TBD</td>
							</tr>
						</c:forEach>
					</table>
					<br/><br/>
				</c:if>
				<input type="submit" value="<spring:message code="calculation.CalculationRegistration.test"/>"/>
			</form>
		</td>
		<td valign="top" style="width:100%;">
			<c:if test="${!empty error}">
				${error}
			</c:if>
			<c:if test="${!empty result}">
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
			</c:if>
		</td>
	</tr>
</table>

<%@ include file="/WEB-INF/template/footer.jsp"%>