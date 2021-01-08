<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<petclinic:layout pageName="team">

	<h2>List of Grand Prixes</h2>
	<h3>Click on the circuit for more information</h3>
	<table id="tournamentesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Location</th>
				<th>Circuit</th>
				<th>Laps</th>
				<th>Distance</th>
				<th>Day of race</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${grandPrix}" var="grandPrix">
				<tr>
					

					<td><a
						href="<c:url value="${grandPrix.id}/details" />"><c:out
								value="${grandPrix.location}" /></a></td>
					<td><c:out value="${grandPrix.circuit}" /></td>
					<td><c:out value="${grandPrix.laps}" /></td>
					<td><c:out value="${grandPrix.distance}" /></td>
					<td><petclinic:localDate date="${grandPrix.dayOfRace}"
							pattern="yyyy-MM-dd" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


	<a class="btn btn-default"
		href='<spring:url value="/grandprix/new" htmlEscape="true"/>'>Add
		grand prix </a>


</petclinic:layout>
