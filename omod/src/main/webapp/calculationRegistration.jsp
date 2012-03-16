<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="Manage Token Registrations" otherwise="/login.htm" redirect="/module/calculation/calculationRegistrations.list" />
<%@ include file="localHeader.jsp" %>

<c:choose>
	<c:when test="${empty calculationRegistration.id}">
		<h2><spring:message code="calculation.CalculationRegistration.create.pageTitle"/></h2><br/>
	</c:when>
	<c:otherwise>
		<h2><spring:message code="calculation.CalculationRegistration.edit.pageTitle"/></h2><br/>
		<form method="post" action="deleteCalculationRegistration.form">
			<input type="hidden" name="id" value="${ calculationRegistration.id }"/>
			<input type="submit" value="<spring:message code="calculation.CalculationRegistration.delete.title" />"/>
		</form>
	</c:otherwise>
</c:choose>

<spring:hasBindErrors name="calculationRegistration">
	<spring:message code="fix.error"/>
	<div class="error">
		<c:forEach items="${errors.allErrors}" var="error">
			<spring:message code="${error.code}" text="${error.code}"/><br/><!-- ${error} -->
		</c:forEach>
	</div>
	<br />
</spring:hasBindErrors>

<form method="post" class="box">
	<table>
		<tr>
			<td><spring:message code="calculation.CalculationRegistration.token"/></td>
			<td>
				<spring:bind path="calculationRegistration.token">
					<input type="text" name="token" size="50" value="${calculationRegistration.token}" />
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td><spring:message code="calculation.CalculationRegistration.providerClassName"/></td>
			<td>
				<spring:bind path="calculationRegistration.providerClassName">
					<select name="providerClassName">
						<option value=""></option>
						<c:forEach items="${providers}" var="provider">
							<option value="${provider['class'].name}" <c:if test="${provider['class'].name == status.value}">selected</c:if>>${provider['class'].simpleName}</option>
						</c:forEach>
					</select>
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td><spring:message code="calculation.CalculationRegistration.calculationName"/></td>
			<td>
				<spring:bind path="calculationRegistration.calculationName">
					<input type="text" name="calculationName" size="50" value="${calculationRegistration.calculationName}" />
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td><spring:message code="calculation.CalculationRegistration.configuration"/></td>
			<td>
				<spring:bind path="calculationRegistration.configuration">
					<textarea name="configuration" rows="5" cols="50">${calculationRegistration.configuration}</textarea>
					<c:if test="${status.errorMessage != ''}">
						<span class="error">${status.errorMessage}</span>
					</c:if>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="<spring:message code='general.save'/>" />
			</td>
		</tr>
	</table>
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>