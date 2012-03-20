<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Manage Token Registrations" otherwise="/login.htm" redirect="/module/calculation/calculationAutoRegistration.form" />
<%@ include file="localHeader.jsp" %>

<style>
	.calculationTable th {text-align:left;}
	.resultTable th,td {padding-left:10px; padding-right:10px; text-align:left;}
</style>

<h2><spring:message code="calculation.registerCalculationsAutomatically"/></h2>

<form method="post">
	<fieldset>
		<legend><spring:message code="calculation.whichCalculationsDoYouWishToRegister"/></legend>
		<br/>
		<c:forEach items="${suggestions}" var="suggestion">
			<c:set var="options" value="${suggestion.suggestions}"/>
			<c:if test="${!empty options}">
				<input type="checkbox" name="${suggestion['class'].name}_${suggestion.name}" value="t">
				<spring:message code="${suggestion.name}" text="${suggestion.name}"/> (${fn:length(options)})<br/>
			</c:if>
		</c:forEach>
		<br/>
	</fieldset>
	<br/>
	<fieldset>
		<br/>
		<legend><spring:message code="calculation.registrationConflictQuestion"/></legend>
		<input type="radio" name="conflictMode" value="useExisting" checked="checked">
		<spring:message code="calculation.registrationConflictUseExisting"/><br/>
		<input type="radio" name="conflictMode" value="overwrite">
		<spring:message code="calculation.registrationConflictOverride"/><br/>
		<br/>
	</fieldset>
	<br/>
	<input type="submit" value="<spring:message code="calculation.registerCalculations"/>"/>
	<input type="button" value="<spring:message code="general.cancel"/>" onclick="document.location.href='calculationRegistrations.form';"
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>