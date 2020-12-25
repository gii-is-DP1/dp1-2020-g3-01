<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="motorcycle">

	<h2>Motorbike Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Brand</th>
			<td><b><c:out value="${motorcycle.brand}" /></b></td>
		</tr>
		<tr>
			<th>Displacement</th>
			<td><c:out value="${motorcycle.displacement}" /></td>
		</tr>
		<tr>
			<th>Horse Power</th>
			<td><c:out value="${motorcycle.horsePower}" /></td>
		</tr>
		<tr>
			<th>Weight</th>
			<td><c:out value="${motorcycle.weight}" /></td>
		</tr>
		<tr>
			<th>Tank Capacity</th>
			<td><c:out value="${motorcycle.tankCapacity}" /></td>
		</tr>
		<tr>
			<th>Max Speed</th>
			<td><c:out value="${motorcycle.maxSpeed}" /></td>
		</tr>
	</table>

	<spring:url value="edit" var="editaMotorcycleUrl">
	</spring:url>
	<spring:url value="delete" var="eliminaMotorcycleUrl">
	</spring:url>
	<a href="${fn:escapeXml(editaMotorcycleUrl)}" class="btn btn-default" style="margin-right: 1rem;">Edit Motorcycle</a>
	<a href="${fn:escapeXml(eliminaMotorcycleUrl)}" class="btn btn-default" style="margin-right: 1rem;">Delete Motorcycle</a>
	

	<%-- <spring:url value="{managerId}/teams/details" var="viewTeamsUrl">
		<spring:param name="managerId" value="${manager.id}" />
	</spring:url> --%>

</petclinic:layout>